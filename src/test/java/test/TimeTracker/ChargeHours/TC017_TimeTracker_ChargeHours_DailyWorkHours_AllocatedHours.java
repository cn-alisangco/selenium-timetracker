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

public class TC017_TimeTracker_ChargeHours_DailyWorkHours_AllocatedHours extends BaseClass {
	
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
	        
	    	String id = "TC017_TimeTracker_ChargeHours_DailyWorkHours_AllocatedHours";
	    	String user = creds.testData(id, "username");
	    	String pass = creds.testData(id, "password");
	    	String project = creds.testData(id, "project");
	    	String tab = creds.testData(id, "tab"); 
	    	DateParser datetime = new DateParser();
	    	int dayOfMonth = datetime.getMonth();
	    	
	    	loginPage.login(user, pass);
	    	homePage.verifyInHomePage();
	    	homePage.clickInputWhizHours(dayOfMonth);
	    	dailyWorkHours.verifyInputWhizExists();
	    	dailyWorkHours.chooseProjectFromDropdown(project);
	    	dailyWorkHours.verifyChosenProject(project);
	    	dailyWorkHours.clickTab(tab);
	    	dailyWorkHours.verifySelectedTasks();
	    	String randomTask = dailyWorkHours.chooseRandomTask(1);	//enter number of tasks that will be randomly chosen from list
	    	dailyWorkHours.verifyTaskChosen();
	    	dailyWorkHours.verifyAllocatedHours(randomTask);
	}
}
