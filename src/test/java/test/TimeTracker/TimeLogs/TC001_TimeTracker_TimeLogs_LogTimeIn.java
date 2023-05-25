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
	    	
	    	//loginPage.login(user, pass);
	    	//verify if successful login
	    	//if(!homePage.verifySuccessfulLogin()) {
	    	//	throw new Exception("Login error!");
	    	//}
	    	
	    	//setting date today
	    	
	    	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy"); //setting date format
	    	LocalDateTime now = LocalDateTime.now(); //getting current time
	    	TimeParser timeparser = new TimeParser("11:00 PM");
	    	System.out.println(timeparser.getHour());
	    	System.out.println(timeparser.getMinute());
	    	System.out.println(timeparser.getPeriod());
	    	//homePage.clickDate(dtf.format(now)); 
	    	editTimeLogs.fillManualTimeIn(now.getDayOfWeek().getValue(),timeparser.getHour(),timeparser.getMinute(),timeparser.getPeriod());
	    	//editTimeLogs.verifyTimeInFill(timeparser.getPeriod());
	    }
}
