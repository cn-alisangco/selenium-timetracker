package test.TimeTracker.ChargeHours;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import base.BaseClass;
import pageObjects.timetracker.v2.DailyWorkHours;
import pageObjects.timetracker.v2.EditTimeLogs;
import pageObjects.timetracker.v2.HomePage;
import pageObjects.timetracker.v2.LoginPage;
import utilities.ExcelReader;
import utilities.TimeParser;

public class TC003_TimeTracker_ChargeHours_DailyWorkHoursDateDisplayed extends BaseClass {
	
	LoginPage loginPage;
	HomePage homePage;
	DailyWorkHours dailyWorkHours;
	
	private void initialize() {
		loginPage = PageFactory.initElements(getDriver(), LoginPage.class);
		homePage = PageFactory.initElements(getDriver(), HomePage.class);
		dailyWorkHours = PageFactory.initElements(getDriver(), DailyWorkHours.class);
	}
	
	@Test
	 public void loginToTimeTracker() throws Exception{
		
	        initialize();
	        
	    	ExcelReader creds = new ExcelReader(System.getProperty("user.dir") + testDataLoc, "Charge Hours");
	    	// user.dir + td from testng file + testsheet name
	        
	    	String id = "TC003_TimeTracker_ChargeHours_DailyWorkHoursDateDisplayed";
	    	String user = creds.testData(id, "username");
	    	String pass = creds.testData(id, "password");
	    	
	    	loginPage.login(user, pass);
	    	homePage.verifyInHomePage();
	    	
	    	int dayOfMonth = LocalDateTime.now().getDayOfMonth();
	    	homePage.clickInputWhizHours(dayOfMonth);
	    	dailyWorkHours.verifyInputWhizExists();
	    	
	    	DateTimeFormatter tsFormat = DateTimeFormatter.ofPattern("MMMM yyyy");
	    	LocalDateTime now = LocalDateTime.now(); //getting current time
	    	dailyWorkHours.verifyProperDay(tsFormat.format(now),dayOfMonth);
	    	
	}
}