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

public class TC022_TimeTracker_ChargeHours_DailyWorkHours_ActualHoursAndLoggedHours extends BaseClass {
	
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
	        
	    	String id = "TC022_TimeTracker_ChargeHours_DailyWorkHours_ActualHoursAndLoggedHours";
	    	String user = creds.testData(id, "username");
	    	String pass = creds.testData(id, "password");
	    	String project = creds.testData(id, "project");
	    	String actualHours = creds.testData(id, "actualHours");
	    	String tab = creds.testData(id, "tab");
	    	String reason = creds.testData(id, "remarks");
	    	int dayOfMonth = LocalDateTime.now().getDayOfMonth();
	    	
	    	loginPage.login(user, pass);
	    	
	    	homePage.verifyInHomePage();
	    	homePage.clickInputWhizHours(dayOfMonth);
	    	//verify successful login
	    	dailyWorkHours.verifyInputWhizExists();
	    	//choose and then verify chosen project
	    	dailyWorkHours.chooseProjectFromDropdown(project);
	    	dailyWorkHours.verifyChosenProject(project);
	    	//click and verify in General Tasks
	    	dailyWorkHours.clickTab(tab);
	    	dailyWorkHours.verifySelectedTasks();
	    	String chosenTask = dailyWorkHours.chooseRandomTask(2);
	    	dailyWorkHours.verifyTaskChosen();
	    	//extract values for later comparison
	    	int actual = dailyWorkHours.getActualHours(chosenTask);
	    	int logged = dailyWorkHours.getLoggedHours(chosenTask);
	    	//enter your Work Hours
	    	dailyWorkHours.enterActualHours(actualHours);
	    	dailyWorkHours.enterTaskDescription(reason);
	    	dailyWorkHours.clickSave();
	    	dailyWorkHours.handleWarningPopup();
	    	//verify successful return to homepage and correct entered hours
	    	dailyWorkHours.verifyAddedHours(actual, logged, Integer.parseInt(actualHours), chosenTask);
	}
}
