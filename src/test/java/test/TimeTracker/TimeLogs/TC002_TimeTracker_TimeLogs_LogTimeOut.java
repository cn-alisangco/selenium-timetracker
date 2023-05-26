package test.TimeTracker.TimeLogs;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import base.BaseClass;
import pageObjects.timetracker.v2.EditTimeLogs;
import pageObjects.timetracker.v2.HomePage;
import pageObjects.timetracker.v2.LoginPage;
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
	    	String user = creds.testData(id, "username");
	    	String pass = creds.testData(id, "password");
	    	
	    	loginPage.login(user, pass);
	    	//verify if successful login
	    	homePage.verifySuccessfulLogin();
	    	//setting date today
	    	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("M/dd/yyyy"); //setting date format
	    	LocalDateTime now = LocalDateTime.now(); //getting current time
	    	TimeParser timeparser = new TimeParser("11:00 PM");
	    	//getting date and time values
	    	int dayOfWeek = now.getDayOfWeek().getValue();
	    	String hour = timeparser.getHour();
	    	String minute = timeparser.getMinute();
	    	String period = timeparser.getPeriod();
	    	int dayOfMonth = LocalDateTime.now().getDayOfMonth();
	    	
	    	homePage.clickDate(dtf.format(now)); 
	    	editTimeLogs.fillManualTimeOut(dayOfWeek,hour,minute,period);
	    	editTimeLogs.verifyTimeOutFill(dayOfMonth,hour,minute,period);
	    }
}
