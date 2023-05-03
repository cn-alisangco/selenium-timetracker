package test;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import base.BaseClass;
import pageObjects.timetracker.DailyWorkHoursPage;
import pageObjects.timetracker.EditTimeLogsPage;
import pageObjects.timetracker.HomePage;
import pageObjects.timetracker.LoginPage;
import utilities.ReadExcelData;

@Listeners(utilities.TestNGListeners.class) //need to uncomment this when running this TC only
public class TC5_End_to_End extends BaseClass {
	
	LoginPage loginPage;
	HomePage homePage;
	EditTimeLogsPage editTimeLogsPage;
	DailyWorkHoursPage dailyWorkHoursPage;
	
	public static String sheetName = "Timetracker"; // sheet name of the test data to be used
	public static String tcName = "TCs_TimeTracker_"; // test case name
	
	// TestNG Annotation
	// This is the Actual Test
	@Test(priority = 1)
	public void Enter_Time_Logs_and_Work_Hours() {
		loginPage = new LoginPage(getDriver());
		homePage = new HomePage(getDriver());
		editTimeLogsPage = new EditTimeLogsPage(getDriver());
		dailyWorkHoursPage = new DailyWorkHoursPage(getDriver());
		
		// test data counter
		int tdCounter = 0;

		//---Login to Time Tracker---
		loginPage.proceedToLoginPage();
		loginPage.setUsername(sheetName, tcName + tdCounter, "Username");
		loginPage.setPassword(sheetName, tcName + tdCounter, "Password");
		loginPage.clickLogin();
		homePage.verifySuccessfulLogin();
		
		homePage.selectTimeSheetPeriod(sheetName, tcName + tdCounter, "TimeSheetPeriod");
		
		//---Edit Time Logs---
		
		// total number of test data to be used
		int tdTotalCount = ReadExcelData.getTdCount();
		
		while (tdCounter < tdTotalCount) {
			
			String date = homePage.getDate(sheetName, tcName + tdCounter, "Date");

			// gets the date from external source file then perform click command
			homePage.clickDate(date);

			// gets the total number of dates in Edit Time Logs (etl) page
			// also stores the text values of the dates and store in an array list
			int etlTotalDaysCount = editTimeLogsPage.getNumberOfDays();

			int etlDaysCounter = 0;

			// iterates n number of times and populate the time logs if data exists in the
			// array list
			while (etlDaysCounter < etlTotalDaysCount && tdCounter < tdTotalCount) {

				// gets the date from external source file
				date = homePage.getDate(sheetName, tcName + tdCounter, "Date");

				// returns "Yes" if date from external source file exists in the array list and
				// "No" if it does not exist
				String dateExists = editTimeLogsPage.checkIfDateExists(date);

				// if the date exists, will set the timelogs, otherwise skip.
				if (dateExists.equalsIgnoreCase("Yes")) {
					editTimeLogsPage.setTimeIn(sheetName, tcName + tdCounter, "TimeInHR", "TimeInMin", "TimeInDP",
							"TimeInLoc",date);
					editTimeLogsPage.setTimeOut(sheetName, tcName + tdCounter, "TimeOutHR", "TimeOutMin", "TimeOutDP",
							"TimeOutLoc",date);
					editTimeLogsPage.setRemarks(sheetName, tcName + tdCounter, "Remarks",date);
					editTimeLogsPage.setReasonOverride(sheetName, tcName + tdCounter, "Reason",date);
					tdCounter++;
				}
				etlDaysCounter++;
			}
			editTimeLogsPage.saveTimeLogs();
			// editTimeLogsPage.cancelTimeLogs();
		}
		
		//---Enter Daily Work Hours---
		
		tdCounter = 0;
		
		while (tdCounter < tdTotalCount) {
			homePage.clickWhizHours(sheetName, tcName + tdCounter, "Date");
			dailyWorkHoursPage.selectProject(sheetName, tcName + tdCounter, "Project");
			dailyWorkHoursPage.clickActiveTasks();
			dailyWorkHoursPage.clickTask(sheetName, tcName + tdCounter, "Task");
			dailyWorkHoursPage.enterWorkHours(sheetName, tcName + tdCounter, "WorkHours");
			dailyWorkHoursPage.selectWorkType(sheetName, tcName + tdCounter, "WorkType");
			dailyWorkHoursPage.enterTaskDescription(sheetName, tcName + tdCounter, "TaskDesc");
			dailyWorkHoursPage.saveWorkHours();
			dailyWorkHoursPage.closeDailyWorkHours();
			homePage.waitUntilTaskLogIsDisplayed();
			tdCounter++;
		}
		

		
	}
}