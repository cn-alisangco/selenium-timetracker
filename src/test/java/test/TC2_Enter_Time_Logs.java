package test;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import base.BaseClass;
import pageObjects.timetracker.EditTimeLogsPage;
import pageObjects.timetracker.HomePage;
import pageObjects.timetracker.LoginPage;
import utilities.ReadExcelData;

@Listeners(utilities.TestNGListeners.class) // need to uncomment this when running this TC only
public class TC2_Enter_Time_Logs extends BaseClass {
	LoginPage loginPage;
	HomePage homePage;
	EditTimeLogsPage editTimeLogsPage;
	TC1_Login_To_Application tc1;

	public static String sheetName = "Timetracker_Dec"; // sheet name of the test data to be used
	public static String tcName = "TCs_TimeTracker_"; // test case name
	

	// TestNG Annotation
	// This is the Actual Test

	@Test(priority = 1)
	public void Enter_Time_Logs() {
		loginPage = new LoginPage(getDriver());
		homePage = new HomePage(getDriver());
		editTimeLogsPage = new EditTimeLogsPage(getDriver());

		// test data counter
		int tdCounter = 0;
		
		loginPage.proceedToLoginPage();
		loginPage.setUsername(sheetName, tcName + tdCounter, "Username");
		loginPage.setPassword(sheetName, tcName + tdCounter, "Password");
		loginPage.clickLogin();
		homePage.verifySuccessfulLogin();
		
		homePage.selectTimeSheetPeriod(sheetName, tcName + tdCounter, "TimeSheetPeriod");

		int tdTotalCount = ReadExcelData.getTdCount();

		while (tdCounter < tdTotalCount) {

			// #1 fetch the date from external source file
			String date = homePage.getDate(sheetName, tcName + tdCounter, "Date");

			// #2 checks if the date is in the selected time sheet period
			if (homePage.checkIfDateExists(date).equalsIgnoreCase("Yes")) {
				
				// #2.1 perform click command on the date
				homePage.clickDate(date);

				// #2.2 returns the total number of dates in Edit Time Logs (etl) page
				// also stores the text values of the dates and store in an array list
				int etlTotalDaysCount = editTimeLogsPage.getNumberOfDays();

				int etlDaysCounter = 0;

				// iterates n number of times and populate the time logs if date exists in the
				// array list
				while (etlDaysCounter < etlTotalDaysCount && tdCounter < tdTotalCount) {

					// #2.3 fetch the date from external source file
					date = homePage.getDate(sheetName, tcName + tdCounter, "Date");
					
					// #2.4 returns "Yes" if date from external source file exists in the array list and
					// "No" if it does not exist
					String dateExists = editTimeLogsPage.checkIfDateExists(date);

					// #2.4.1 if the date exists, will set the timelogs, otherwise skip.
					if (dateExists.equalsIgnoreCase("Yes")) {
						editTimeLogsPage.setTimeIn(sheetName, tcName + tdCounter, "TimeInHR", "TimeInMin", "TimeInDP",
								"TimeInLoc", date);
						editTimeLogsPage.setTimeOut(sheetName, tcName + tdCounter, "TimeOutHR", "TimeOutMin",
								"TimeOutDP", "TimeOutLoc", date);
						//editTimeLogsPage.setRemarks(sheetName, tcName + tdCounter, "Remarks", date);
						editTimeLogsPage.setReasonOverride(sheetName, tcName + tdCounter, "Reason", date);
						tdCounter++;
					}
					// #2.5 increment etlDaysCounter
					etlDaysCounter++;
				}
				editTimeLogsPage.saveTimeLogs();
				// editTimeLogsPage.cancelTimeLogs();
				
				
				
			} else {
				// #3
				tdCounter++;
			}
		}
		homePage.verifyTimeLogs();
		homePage.clickLogout();
		loginPage.verifyUserIsLoggedOut();
		

	}
}
