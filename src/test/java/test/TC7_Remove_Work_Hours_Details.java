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
public class TC7_Remove_Work_Hours_Details extends BaseClass {

	LoginPage loginPage;
	HomePage homePage;
	EditTimeLogsPage editTimeLogsPage;
	DailyWorkHoursPage dailyWorkHoursPage;
	public static String sheetName = "Timetracker_Dec"; // sheet name of the test data to be used
	public static String tcName = "TCs_TimeTracker_"; // test case name

	// TestNG Annotation
	// This is the Actual Test
	@Test
	public void Remove_Work_Hours_Details() {
		loginPage = new LoginPage(getDriver());
		homePage = new HomePage(getDriver());
		editTimeLogsPage = new EditTimeLogsPage(getDriver());
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

		// ---Enter Daily Work Hours---

		// total number of test data to be used
		int tdTotalCount = ReadExcelData.getTdCount();

		while (tdCounter < tdTotalCount) {
			homePage.clickWhizHours(sheetName, tcName + tdCounter, "Date");
			dailyWorkHoursPage.clickTask(sheetName, tcName + tdCounter, "Task");
			dailyWorkHoursPage.clickRemoveWorkHours();
			dailyWorkHoursPage.okRemoveWorkHours();
			dailyWorkHoursPage.closeDailyWorkHours();
			homePage.waitUntilTaskLogIsNotDisplayed();
			tdCounter++;
		}

	}
}
