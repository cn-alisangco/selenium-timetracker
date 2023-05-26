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
	    	String timeIn = creds.testData(id, "timein");
	    	System.out.println(timeIn);
	    	
	    	loginPage.login(user, pass);
	    	homePage.verifySuccessfulLogin();
	    	//setting date today
	    	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("M/dd/yyyy");
	    	LocalDateTime now = LocalDateTime.now(); //getting current time
	    	//System.out.println(dtf.format(timeIn));
	    	TimeParser timeparser = new TimeParser("11:00 AM");
	    	//getting date and time values
	    	int dayOfWeek = now.getDayOfWeek().getValue();
	    	String hour = timeparser.getHour();
	    	String minute = timeparser.getMinute();
	    	String period = timeparser.getPeriod();
	    	int dayOfMonth = LocalDateTime.now().getDayOfMonth();
	    	
	    	homePage.clickDate(dtf.format(now)); 
	    	editTimeLogs.fillManualTimeIn(dayOfWeek,hour,minute,period);
	    	editTimeLogs.verifyTimeInFill(dayOfMonth,hour,minute,period);
	}
}
