package test;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import base.BaseClass;
import pageObjects.timetracker.DailyWorkHoursPage;
import pageObjects.timetracker.EditTimeLogsPage;
import pageObjects.timetracker.HomePage;
import pageObjects.timetracker.LoginPage;
import utilities.ReadExcelData;

@Listeners(utilities.TestNGListeners.class) // need to uncomment this when running this TC only
public class TC3_Enter_Work_Hours extends BaseClass {

	LoginPage loginPage;
	HomePage homePage;
	DailyWorkHoursPage dailyWorkHoursPage;

	public static String sheetName = "Timetracker_Dec"; // sheet name of the test data to be used
	public static String tcName = "TCs_TimeTracker_"; // test case name

	// TestNG Annotation
	// This is the Actual Test
	@Test
	public void Enter_Work_Hours() {
		loginPage = new LoginPage(getDriver());
		homePage = new HomePage(getDriver());
		dailyWorkHoursPage = new DailyWorkHoursPage(getDriver());

		// test data counter
		int tdCounter = 0;

		// ---Login to Time Tracker---
		loginPage.proceedToLoginPage();
		loginPage.setUsername(sheetName, tcName + tdCounter, "Username");
		loginPage.setPassword(sheetName, tcName + tdCounter, "Password");
		loginPage.clickLogin();
		homePage.verifySuccessfulLogin();

		homePage.selectTimeSheetPeriod(sheetName, tcName + tdCounter, "TimeSheetPeriod");

		// total number of test data to be used
		int tdTotalCount = ReadExcelData.getTdCount();

		// ---Enter Daily Work Hours---

		while (tdCounter < tdTotalCount) {
			homePage.clickWhizHours(sheetName, tcName + tdCounter, "Date");
			dailyWorkHoursPage.verifyDateMonthYearIsDisplayed();
			dailyWorkHoursPage.selectProject(sheetName, tcName + tdCounter, "Project");
			dailyWorkHoursPage.clickActiveTasks();
			dailyWorkHoursPage.clickTask(sheetName, tcName + tdCounter, "Task");
			dailyWorkHoursPage.enterWorkHours(sheetName, tcName + tdCounter, "WorkHours");
			dailyWorkHoursPage.selectWorkType(sheetName, tcName + tdCounter, "WorkType");
			dailyWorkHoursPage.enterTaskDescription(sheetName, tcName + tdCounter, "TaskDesc");
			dailyWorkHoursPage.saveWorkHours();
			dailyWorkHoursPage.closeDailyWorkHours();
			homePage.waitUntilTaskLogIsDisplayed();
			homePage.clickShowTaskLog();
			homePage.verifyTaskLogs();
			homePage.clickHideTaskLog();
			tdCounter++;
		}
	}
}