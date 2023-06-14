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

public class TC006_TimeTracker_TimeLogs_LogValidTimeInTimeOut extends BaseClass {
	
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
	        
	    	String id = "TC006_TimeTracker_TimeLogs_LogValidTimeInTimeOut";
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
	    	/*----------Getting date and time values----------*/
	    	//TIME IN
	    	TimeParser timeparserIn = new TimeParser("9:00 AM"); /*Enter the chosen time here*/
	    	String hourIn = timeparserIn.getHour();
	    	String minuteIn = timeparserIn.getMinute();
	    	String periodIn = timeparserIn.getPeriod();
	    	//TIME OUT
	    	TimeParser timeparserOut = new TimeParser("6:00 PM"); /*Enter the chosen time here*/
	    	String hourOut = timeparserOut.getHour();
	    	String minuteOut = timeparserOut.getMinute();
	    	String periodOut = timeparserOut.getPeriod();
	    	int dayOfWeek = EditTimeLogs.setTimeLogIndex();
	    	int dayOfMonth = LocalDateTime.now().getDayOfMonth();
	    	boolean isCanceled = false;

	    	/*----------Add Logs----------*/
	    	homePage.selectCurrentTimesheetPeriod(tsFormat.format(now));
	    	homePage.clickDate(dtf.format(now));
	    	//TIME LOGS
	    	editTimeLogs.fillManualTimeIn(dayOfWeek,hourIn,minuteIn,periodIn);
	    	editTimeLogs.fillManualTimeOut(dayOfWeek,hourOut,minuteOut,periodOut);
	    	editTimeLogs.enterReasonOverride(dayOfWeek, reason);
	    	editTimeLogs.saveLogs();
	    	//Verify error messages appear
	    	homePage.verifyTimeInFill(dayOfMonth,hourIn,minuteIn,periodIn,isCanceled);
	    	homePage.verifyTimeOutFill(dayOfMonth,hourOut,minuteOut,periodOut,isCanceled);
	    	
	}
}
