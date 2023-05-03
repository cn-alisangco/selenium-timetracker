package test.Arch;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import base.BaseClass;
import pageObjects.timetracker.EditTimeLogsPage;
import pageObjects.timetracker.HomePage;
import pageObjects.timetracker.LoginPage;
import utilities.ReadExcelData;

@Listeners(utilities.TestNGListeners.class) // need to uncomment this when running this TC only
public class TestTimeLogs_TC10_Enter_Time_Logs_TimeIn_and_TimeOut_Week3 extends BaseClass {

	LoginPage loginPage;
	HomePage homePage;
	EditTimeLogsPage editTimeLogsPage;
	Test_TC1_Login_To_Application tc1;

	public static String sheetName = "TimeTracker_Weekly"; // sheet name of the test data to be used
	public static String tcName = "TC10_TimeTracker_"; // test case name

	// TestNG Annotation
	// This is the Actual Test

	@Test(priority = 1)
	public void Enter_Time_Logs_TimeIn_and_TimeOut_Week3() {
		loginPage = new LoginPage(getDriver());
		homePage = new HomePage(getDriver());
		editTimeLogsPage = new EditTimeLogsPage(getDriver());

		// test data counter
		int tdCounter = 0;
		
		// Log in to Time Tracker Application
		loginPage.proceedToLoginPage();
		loginPage.setUsername(sheetName, tcName + tdCounter, "Username");
		loginPage.setPassword(sheetName, tcName + tdCounter, "Password");
		loginPage.clickLogin();
		homePage.verifySuccessfulLogin();

		// Selects Time Sheet Period
		homePage.selectTimeSheetPeriod(sheetName, tcName + tdCounter, "TimeSheetPeriod");
		
		// Gets the Date from external source file
		String date = homePage.getDate(sheetName, tcName + tdCounter, "Date");

		// Checks if Date is in the selected Time Sheet Period
		if (homePage.checkIfDateExists(date).equalsIgnoreCase("Yes")) {
			
			// Performs click command on the date
			homePage.clickDate(date);
			
			// Returns the total number of dates in Edit Time Logs (etl) page
			// also stores the text values of the dates and store in an array list
			int etlTotalDaysCount = editTimeLogsPage.getNumberOfDays();

			int etlDaysCounter = 0;

			// iterates n number of times and populate the time logs if date exists in the
			// array list
			while (etlDaysCounter < etlTotalDaysCount) {
				
				// Gets the Date from external source file
				date = homePage.getDate(sheetName, tcName + tdCounter, "Date");
				
				// Returns "Yes" if date from external source file exists in the array list and
				// "No" if it does not exist
				String dateExists = editTimeLogsPage.checkIfDateExists(date);
				
				// If the date exists, will set the timelogs, otherwise skip.
				if (dateExists.equalsIgnoreCase("Yes")) {
					editTimeLogsPage.setTimeIn(sheetName, tcName + tdCounter, "TimeInHR", "TimeInMin", "TimeInDP",
							"TimeInLoc", date);
					editTimeLogsPage.setTimeOut(sheetName, tcName + tdCounter, "TimeOutHR", "TimeOutMin", "TimeOutDP",
							"TimeOutLoc", date);
					editTimeLogsPage.setReasonOverride(sheetName, tcName + tdCounter, "Reason", date);
					tdCounter++;
				}
				// Increment etlDaysCounter
				etlDaysCounter++;
			}
			editTimeLogsPage.saveTimeLogs();
			
			homePage.verifyTimeLogs();
			homePage.clickLogout();
			loginPage.verifyUserIsLoggedOut();
			// homePage.verifyTimeInDetails();
			// homePage.verifyTimeOutDetails();
			// homePage.verifyReasonTITOOverride();
		} else {
			homePage.dateNotFound();
		}
	}
}