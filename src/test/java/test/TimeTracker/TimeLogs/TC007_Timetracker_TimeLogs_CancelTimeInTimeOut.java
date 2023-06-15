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
import utilities.DateParser;
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
	    	String reason = creds.testData(id, "remarks");
	    	
	    	loginPage.login(user, pass);
	    	homePage.verifyInHomePage();
	    	/*----------Getting date and time values----------*/
	    	DateParser datetime = new DateParser();
	    	int dayOfWeek = EditTimeLogs.setTimeLogIndex();
	    	int dayOfMonth = datetime.getDay();
	    	//TIME IN
	    	TimeParser timeparserIn = new TimeParser("9:01 AM"); /*Enter the chosen time here*/
	    	String hourIn = timeparserIn.getHour();
	    	String minuteIn = timeparserIn.getMinute();
	    	String periodIn = timeparserIn.getPeriod();
	    	//TIME OUT
	    	TimeParser timeparserOut = new TimeParser("6:24 PM"); /*Enter the chosen time here*/
	    	String hourOut = timeparserOut.getHour();
	    	String minuteOut = timeparserOut.getMinute();
	    	String periodOut = timeparserOut.getPeriod();
	    	/*----------Add Logs----------*/
	    	homePage.selectCurrentTimesheetPeriod(datetime.period);
	    	homePage.clickDate(datetime.fulldate);
	    	//TIME LOGS
	    	editTimeLogs.fillManualTimeIn(dayOfWeek,hourIn,minuteIn,periodIn);
	    	editTimeLogs.fillManualTimeOut(dayOfWeek,hourOut,minuteOut,periodOut);
	    	editTimeLogs.enterReasonOverride(dayOfWeek, reason);
	    	boolean isCanceled = editTimeLogs.cancelLogs();
	    	//Verify no messages occurred
	    	homePage.verifyTimeInFill(dayOfMonth,hourIn,minuteIn,periodIn,isCanceled);
	    	homePage.verifyTimeOutFill(dayOfMonth,hourOut,minuteOut,periodOut,isCanceled);
	    	
	}
}
