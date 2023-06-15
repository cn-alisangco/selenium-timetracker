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
import utilities.DateParser;
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
	    	String username = creds.testData(id, "username");
	    	String password = creds.testData(id, "password");
	    	DateParser datetime = new DateParser();
	    	int dayOfMonth = datetime.getMonth();
	    	
	    	loginPage.login(username, password);
	    	homePage.verifyInHomePage();
	    	homePage.clickInputWhizHours(dayOfMonth);
	    	dailyWorkHours.verifyInputWhizExists();
	    	dailyWorkHours.verifyProperDay(datetime.period,dayOfMonth);
	    	
	}
}
