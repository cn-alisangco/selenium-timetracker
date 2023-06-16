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

public class TC004_TimeTracker_LeaveApplication_VerifyLeaveTypesInTheModal extends BaseClass {

	String testDescription = "Verify that there are leave types listed and can be selected in Leave Type dropdown";

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
	List<String> expectedLeaveTypes = Arrays.asList("Banked General Leave", "Call Back Vacation Leave",
			"Excess Earned Leaves", "Excess General Leaves", "General Leave", "Leave Without Pay",
			"On-call Vacation Leave", "Overtime Vacation Leave", "Service Incentive Leave", "Solo Parent Leave",
			"Unauthorized LWOP", "Vacation Leave");

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
	public void TC004_TimeTracker_LeaveApplication_VerifyLeaveTypesInTheModal() {

		initialize();

		// Log in to Timetracker with valid credentials
		loginPage.login(loginCredentials.get("username"), loginCredentials.get("password"), accountPrecondition);

		// Get timelog date strings with 'File A Leave' button/link
		List<String> fileALeaveLinkDateStrings = myTimeLogsPage.getFileALeaveLinkDateStrings(fileALeaveLinkDateFormat);

		// Iterate over each timelog date with a 'File A Leave' button/link
		for (String fileALeaveLinkDate : fileALeaveLinkDateStrings) {

			// log loop iteration
			String iterationLog = "Iteration for timelog date: " + fileALeaveLinkDate
					+ " ---------------------------------------------------------------------------";
			UserHelper.customReportLog(iterationLog);

			// verify "File a Leave" button is visible in the list of Actions
			myTimeLogsPage.verifyFileALeaveLinkForDateIsDisplayed(fileALeaveLinkDate);

			// Click File a Leave button
			myTimeLogsPage.clickFileALeaveButton(fileALeaveLinkDate);

			// Verify File a Leave modal is displayed
			fileALeaveModal.verifyFileALeaveModalForDateIsDisplayed(fileALeaveLinkDate);

			// Verify leavetypes
			fileALeaveModal.verifyDropDownOptions(fileALeaveModal.getLeaveTypeDropdown(),
					fileALeaveModal.getLeaveTypeOptions(), expectedLeaveTypes);

			// Select the leave types
			for (String expectedleaveType : expectedLeaveTypes) {

				// log loop iteration
				String iterationLog1 = "Iteration for leave type: " + expectedleaveType
						+ " ---------------------------------------------------------------------------";
				UserHelper.customReportLog(iterationLog1);

				// Select a leave type
				fileALeaveModal.selectDropDownOption(fileALeaveModal.getLeaveTypeDropdown(), expectedleaveType);

				// Verify leave type was indeed selected
				fileALeaveModal.verifydropDownOptionIsSelected(fileALeaveModal.getLeaveTypeDropdown(),
						expectedleaveType);
			}

			// Close File a Leave modal
			fileALeaveModal.clickCloseButton();

			// Verify File A Leave modal is NOT displayed
			fileALeaveModal.verifyFileALeaveModalIsNotDisplayed();
		}

	}

}
