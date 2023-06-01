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

public class TC007_Timetracker_TimeLogs_CancelTimeInTimeOut extends BaseClass {
	
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
	        
	    	String id = "TC007_Timetracker_TimeLogs_CancelTimeInTimeOut";
	    	String user = creds.testData(id, "username");
	    	String pass = creds.testData(id, "password");
	    	
	    	loginPage.login(user, pass);
	    	//verify successful login
	    	homePage.verifySuccessfulLogin();
	    	//setting date today
	    	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("M/d/yyyy");
	    	DateTimeFormatter tsFormat = DateTimeFormatter.ofPattern("MMMM yyyy");
	    	LocalDateTime now = LocalDateTime.now(); //getting current time
	    	/*----------Getting date and time values----------*/
	    	//TIME IN
	    	TimeParser timeparserIn = new TimeParser("9:01 AM");
	    	String hourIn = timeparserIn.getHour();
	    	String minuteIn = timeparserIn.getMinute();
	    	String periodIn = timeparserIn.getPeriod();
	    	//TIME OUT
	    	TimeParser timeparserOut = new TimeParser("6:24 PM");
	    	String hourOut = timeparserOut.getHour();
	    	String minuteOut = timeparserOut.getMinute();
	    	String periodOut = timeparserOut.getPeriod();
	    	int dayOfWeek = EditTimeLogs.setTimeLogIndex();
	    	int dayOfMonth = LocalDateTime.now().getDayOfMonth();
	    	
	    	/*----------Add Logs----------*/
	    	homePage.selectCurrentTimesheetPeriod(tsFormat.format(now));
	    	homePage.clickDate(dtf.format(now));
	    	//TIME LOGS
	    	editTimeLogs.fillManualTimeIn(dayOfWeek,hourIn,minuteIn,periodIn);
	    	editTimeLogs.fillManualTimeOut(dayOfWeek,hourOut,minuteOut,periodOut);
	    	editTimeLogs.enterReasonOverride(dayOfWeek, "automated fillup by Selenium");
	    	boolean isCanceled = editTimeLogs.cancelLogs();
	    	//Verify no messages occurred
	    	editTimeLogs.verifyTimeInFill(dayOfMonth,hourIn,minuteIn,periodIn,isCanceled);
	    	editTimeLogs.verifyTimeOutFill(dayOfMonth,hourOut,minuteOut,periodOut,isCanceled);
	    	
	}
}
