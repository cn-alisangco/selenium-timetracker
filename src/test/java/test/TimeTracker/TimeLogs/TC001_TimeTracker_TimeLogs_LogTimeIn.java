package test.TimeTracker.TimeLogs;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import base.BaseClass;
import pageObjects.timetracker.v2.EditTimeLogs;
import pageObjects.timetracker.v2.HomePage;
import pageObjects.timetracker.v2.LoginPage;
import utilities.ExcelReader;
import utilities.TimeParser;

public class TC001_TimeTracker_TimeLogs_LogTimeIn extends BaseClass {
	
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
	        
	    	String id = "TC001_TimeTracker_TimeLogs_LogTimeIn";
	    	String user = creds.testData(id, "username");
	    	String pass = creds.testData(id, "password");
	    	String reason = creds.testData(id, "remarks");
	    	
	    	loginPage.login(user, pass);
	    	homePage.verifyInHomePage();
	    	/*----------setting date today----------
	    	 * Pattern is <M/d/yyyy> in order to remove 0's from single-digit instances (1/1/2023 instead of 01/01/2023)
	    	 * Pattern <MMMM yyyy> for choosing the period*/
	    	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("M/d/yyyy"); 
	    	DateTimeFormatter tsFormat = DateTimeFormatter.ofPattern("MMMM yyyy");
	    	LocalDateTime now = LocalDateTime.now(); //getting current time
	    	
	    	TimeParser timeparser = new TimeParser("9:00 AM"); /*Enter the chosen time here*/
	    	
	    	//getting date and time values
	    	int dayOfWeek = EditTimeLogs.setTimeLogIndex(); //set index for choosing date
	    	String hour = timeparser.getHour();
	    	String minute = timeparser.getMinute();
	    	String period = timeparser.getPeriod();
	    	int dayOfMonth = LocalDateTime.now().getDayOfMonth();
	    	//click the date to add logs, fill up the fields, and verify tracker registered data successfully
	    	homePage.selectCurrentTimesheetPeriod(tsFormat.format(now));
	    	homePage.clickDate(dtf.format(now));
	    	
	    	editTimeLogs.fillManualTimeIn(dayOfWeek,hour,minute,period);
	    	editTimeLogs.enterReasonOverride(dayOfWeek, reason);
	    	editTimeLogs.saveLogs();
	    	//verify entered time in is reflected on page
	    	homePage.verifyTimeInFill(dayOfMonth,hour,minute,period,false);
	}
}
