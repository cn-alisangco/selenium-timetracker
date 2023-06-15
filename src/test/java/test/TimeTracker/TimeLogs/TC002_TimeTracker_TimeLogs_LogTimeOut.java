package test.TimeTracker.TimeLogs;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import base.BaseClass;
import pageObjects.timetracker.v2.EditTimeLogs;
import pageObjects.timetracker.v2.HomePage;
import pageObjects.timetracker.v2.LoginPage;
import utilities.DateParser;
import utilities.ExcelReader;
import utilities.TimeParser;

public class TC002_TimeTracker_TimeLogs_LogTimeOut extends BaseClass {
	
	LoginPage loginPage;
	HomePage homePage;
	EditTimeLogs editTimeLogs;
	
	private void initialize() {
		loginPage = PageFactory.initElements(getDriver(), LoginPage.class);
		homePage = PageFactory.initElements(getDriver(), HomePage.class);
		editTimeLogs = PageFactory.initElements(getDriver(), EditTimeLogs.class);
	}
	
	@Test
	 public void loginToTimeTracker() throws Exception{
		
	        initialize();
	        
	    	ExcelReader creds = new ExcelReader(System.getProperty("user.dir") + testDataLoc, "Time Logs");
	    	// user.dir + td from testng file + testsheet name
	        
	    	String id = "TC002_TimeTracker_TimeLogs_LogTimeOut";
	    	String username = creds.testData(id, "username");
	    	String password = creds.testData(id, "password");
	    	String reason = creds.testData(id, "remarks");
	    	
	    	loginPage.login(username, password);
	    	homePage.verifyInHomePage();
	    	
	    	DateParser datetime = new DateParser();
	    	TimeParser timeparser = new TimeParser("7:00 PM");/*Enter the chosen time here*/
	    	
	    	//getting date and time values
	    	int dayOfWeek = EditTimeLogs.setTimeLogIndex(); //set index for choosing date
	    	String hour = timeparser.getHour();
	    	String minute = timeparser.getMinute();
	    	String period = timeparser.getPeriod();
	    	int dayOfMonth = datetime.getDay();
	    	//click the date to add logs, fill up the fields, and verify tracker registered data successfully
	    	homePage.selectCurrentTimesheetPeriod(datetime.period);
	    	homePage.clickDate(datetime.fulldate); 
	    	
	    	editTimeLogs.fillManualTimeOut(dayOfWeek,hour,minute,period);
	    	editTimeLogs.enterReasonOverride(dayOfWeek, reason);
	    	editTimeLogs.saveLogs();
	    	//verify entered time in is reflected on page
	    	homePage.verifyTimeOutFill(dayOfMonth,hour,minute,period,false);
	}
}
