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
	    	if(!homePage.verifySuccessfulLogin()) {
	    		throw new Exception("Login error!");
	    	}
	    	
	    	//setting date today
	    	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("M/dd/yyyy"); //setting date format
	    	LocalDateTime now = LocalDateTime.now(); //getting current time
	    	homePage.clickDate(dtf.format(now)); 
	    	String time12Hour = (now.getHour() < 12) //String value to get whether current time is in AM or PM 
	    		? "AM"  //if less than 12, set to AM
	    		: "PM"; //more than 12, set to PM
	    	editTimeLogs.fillManualTimeOut(now.getDayOfWeek().getValue(),time12Hour);
	    	editTimeLogs.verifyTimeOutFill(time12Hour);
	    }
}
