package test.TimeTracker.LeaveApplication;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import base.BaseClass;
import pageObjects.timetracker.HomePage;
import pageObjects.timetracker.v2.FileALeave_Modal;
import pageObjects.timetracker.v2.LoginPage;
import pageObjects.timetracker.v2.MyTimeLogsPage;
import utilities.ExcelReader;
import utilities.UserHelper;

public class TC007_TimeTracker_LeaveApplication_VerifyReasonsInTheModal extends BaseClass {

	String testDescription = "Verify that user can select Reason from the Reason dropdown";

	// Pages and elements
	LoginPage loginPage;
	MyTimeLogsPage myTimeLogsPage;
	FileALeave_Modal fileALeaveModal;
	List<WebElement> fileALeaveLinkDates;

	// Login data
	String sheetName = "Login";
	String recordID = "valid_credentials";
	String accountPrecondition = "Has valid credentials";
	HashMap<String, String> loginCredentials;

	// Test Data
	String fileALeaveLinkDateFormat = "M/d/yyyy";
	List<String> expectedLeaveReasonOptions = Arrays.asList("Emergency Leave", "Sick Leave", "Vacation Leave",
			"Others");

	private void initialize() {
		// initialize page elements
		loginPage = PageFactory.initElements(getDriver(), LoginPage.class);
		myTimeLogsPage = PageFactory.initElements(getDriver(), MyTimeLogsPage.class);
		fileALeaveModal = PageFactory.initElements(getDriver(), FileALeave_Modal.class);

		// get login test data
		loginCredentials = loginPage.getLoginCredentialsTestData(testDataLoc, sheetName, recordID);

		// print test description
		UserHelper.customReportLog("TEST DESCRIPTION: " + testDescription);
	}

	@Test
	public void TC007_TimeTracker_LeaveApplication_VerifyReasonsInTheModal() {
		initialize();

		// Log in to Timetracker with valid credentials
		loginPage.login(loginCredentials.get("username"), loginCredentials.get("password"), accountPrecondition);

		// get all regular shift dates in the M/d/yyyy format
		List<String> fileALeaveLinkDateStrings = myTimeLogsPage.getFileALeaveLinkDateStrings(fileALeaveLinkDateFormat);

		// Iterate over each timelog date with a 'File A Leave' button/link
		for (String fileALeaveLinkDate : fileALeaveLinkDateStrings) {

			// log loop iteration
			String iterationLog = "Iteration for timelog date: " + fileALeaveLinkDate
					+ " ---------------------------------------------------------------------------";
			UserHelper.customReportLog(iterationLog);

			// click the file a leave link with the same index as the regular shift date
			myTimeLogsPage.clickFileALeaveButton(fileALeaveLinkDate);

			// Verify File A Leave modal is displayed
			fileALeaveModal.verifyFileALeaveModalIsDisplayed();

			// Verify leavetypes
			fileALeaveModal.verifyDropDownOptions(fileALeaveModal.getLeaveReasonDropdown(),
					fileALeaveModal.getLeaveReasonOptions(), expectedLeaveReasonOptions);

			// Select the leave reasons
			for (String leaveReasonOption : expectedLeaveReasonOptions) {

				// log loop iteration
				String iterationLog1 = "Iteration for leave reason: " + leaveReasonOption
						+ " ---------------------------------------------------------------------------";
				UserHelper.customReportLog(iterationLog1);

				// Select a leave reason
				fileALeaveModal.selectDropDownOption(fileALeaveModal.getLeaveReasonDropdown(), leaveReasonOption);

				// Verify leave reason was indeed selected
				fileALeaveModal.verifydropDownOptionIsSelected(fileALeaveModal.getLeaveReasonDropdown(),
						leaveReasonOption);
			}

			// 4. Close modal
			fileALeaveModal.clickCloseButton();

			// 5. verify File A Leave modal is NOT displayed
			fileALeaveModal.verifyFileALeaveModalIsNotDislayed();
		}

	}

}
