package test.TimeTracker.LeaveApplication;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import base.BaseClass;
import pageObjects.timetracker.HomePage;
import pageObjects.timetracker.v2.FileALeave_Modal;
import pageObjects.timetracker.v2.LeaveApplicationsPage;
import pageObjects.timetracker.v2.LoginPage;
import pageObjects.timetracker.v2.MyTimeLogsPage;
import pageObjects.timetracker.v2.TopNavigationBar;
import utilities.ExcelReader;
import utilities.UserHelper;

public class TC0017_TimeTracker_FileALeavePopUp_SubmitButton_SelectedLeaveDoesNotHaveBalance extends BaseClass {

	String testDescription = "Verify that there will be a Warning when a Leave selected does not have a balance and Submit button is clicked";

	// Pages and elements
	LoginPage loginPage;
	MyTimeLogsPage myTimeLogsPage;
	FileALeave_Modal fileALeaveModal;
	TopNavigationBar topNavigationBar;
	LeaveApplicationsPage leaveApplicationsPage;
	List<WebElement> fileALeaveLinkDates;
	WebElement leaveTypeDropDown;
	WebElement leaveReasonDropDown;
	WebElement commentField;
	WebElement contactNumberField;

	// Login data
	String sheetName = "Login";
	String recordID = "valid_credentials";
	String accountPrecondition = "Has valid credentials";
	HashMap<String, String> loginCredentials;

	// test data---------------------------
	String fileALeaveLinkDateFormat = "M/d/yyyy";
	List<String> leaveTypes = Arrays.asList("Call Back Vacation Leave", "Excess Earned Leaves", "Excess General Leaves",
			"On-call Vacation Leave", "Overtime Vacation Leave");
	String randomLeaveReason;
	String testComment = "TEST COMMENT/REMARK";
	String testContactNumber = "0123456789";
	String warningMessage = "You do not have enough available balance at this time to cover this leave. Your leave application is being allowed to go through on the assumption that you will be earning enough credits to cover the leave.";

	private void initialize() {
		loginPage = PageFactory.initElements(getDriver(), LoginPage.class);
		topNavigationBar = PageFactory.initElements(getDriver(), TopNavigationBar.class);
		myTimeLogsPage = PageFactory.initElements(getDriver(), MyTimeLogsPage.class);
		fileALeaveModal = PageFactory.initElements(getDriver(), FileALeave_Modal.class);
		leaveApplicationsPage = PageFactory.initElements(getDriver(), LeaveApplicationsPage.class);

		// Set field elements
		leaveTypeDropDown = fileALeaveModal.getLeaveTypeDropdown();
		leaveReasonDropDown = fileALeaveModal.getLeaveReasonDropdown();
		commentField = fileALeaveModal.getCommentsField();
		contactNumberField = fileALeaveModal.getContactNumberField();

		// get login test data
		loginCredentials = loginPage.getLoginCredentialsTestData(testDataLoc, sheetName, recordID);

		// print test description
		UserHelper.customReportLog("TEST DESCRIPTION: " + testDescription);
	}

	@Test
	public void TC0017_TimeTracker_FileALeavePopUp_SubmitButton_SelectedLeaveDoesNotHaveBalance() {

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

			for (String leaveType : leaveTypes) {
				// log loop iteration
				String iterationLog1 = "Iteration for leave type: " + leaveType + " ---------------------------";
				UserHelper.customReportLog(iterationLog1);

				/*-----------------------TIME LOGS PAGE---------------------------------------------------------------*/

				// click File A Leave link for the corresponding date
				myTimeLogsPage.clickFileALeaveButton(fileALeaveLinkDate);

				/*-----------------------FILE A LEAVE MODAL-----------------------------------------------------------*/

				// verify File A Leave modal is displayed
				fileALeaveModal.verifyFileALeaveModalIsDisplayed();

				// check if leave type has enough running balance
				boolean leaveTypeHasEnoughRunningBalance = fileALeaveModal.hasEnoughRunningBalance(leaveType);

				if (leaveTypeHasEnoughRunningBalance == false) {
					// verify leave type was selected
					fileALeaveModal.verifydropDownOptionIsSelected(leaveTypeDropDown, leaveType);

					// Select a random Leave Reason
					randomLeaveReason = fileALeaveModal.getRandomLeaveReason();
					fileALeaveModal.selectDropDownOption(leaveReasonDropDown, randomLeaveReason);

					// verify random leave reason was indeed selected
					fileALeaveModal.verifydropDownOptionIsSelected(leaveReasonDropDown, randomLeaveReason);

					// Fill out Remarks/Comment field
					fileALeaveModal.enterTextInRemarksTextBox(testComment);

					// verify entered text is displayed
					fileALeaveModal.verifyRemarksTextBoxValue(testComment);

					// Fill out Contact Number field
					fileALeaveModal.enterContactNumber(testContactNumber);

					// verify entered text is displayed
					fileALeaveModal.verifyContactNumberTextBoxValue(testContactNumber);

					// Click Submit button
					fileALeaveModal.clickSubmitButton();

					// verify error messages for the fields WITH input are NOT displayed
					fileALeaveModal.verifyErrorMessageForFieldIsNotDisplayed(leaveTypeDropDown);
					fileALeaveModal.verifyErrorMessageForFieldIsNotDisplayed(leaveReasonDropDown);
					fileALeaveModal.verifyErrorMessageForFieldIsNotDisplayed(commentField);
					fileALeaveModal.verifyErrorMessageForFieldIsNotDisplayed(contactNumberField);

					// Verify warning popup is displayed
					fileALeaveModal.verifyWarningPopupIsDisplayed();

					// verify warning popup message
					fileALeaveModal.verifyWarningPopupMessage(warningMessage);

					// click OK in the warning popup
					fileALeaveModal.clickOkButtonInWarningPopup();

					// verify warning popup is not displayed
					fileALeaveModal.verifyWarningPopupIsNotDisplayed();

					// Verify success toast is displayed
					fileALeaveModal.verifyToastIsDisplayed();

					// verify toast message
					fileALeaveModal.verifyToastMessage("Leave Application Saved!");

					// wait for file a leave modal to disappear
					fileALeaveModal.waitForFileALeaveModalToDisappear();

					/*-----------------------TIME LOGS PAGE------------------------------------------------------------------------*/
					
					// Verify submitted leave is in the remarks column of corresponding timelog date
					myTimeLogsPage.verifySubmittedLeaveIsInRemarksColumn(fileALeaveLinkDate, leaveType);

					// Recall filed leave //go to leave applications page
					topNavigationBar.navigateToPage("Leaves", "Applications");

					/*-----------------------LEAVE APPLICATIONS PAGE---------------------------------------------------------------*/

					// Verify submitted leave is in the leave applications table
					leaveApplicationsPage.verifySubmittedLeaveIsDisplayedInLeaveAppTable(fileALeaveLinkDate, leaveType);

					// Recall all leave applications
					leaveApplicationsPage.recallAllLeaveApplications();

					// Verify Leave Applications table is not displayed
					leaveApplicationsPage.verifyLeaveApplicationsTableIsNotDisplayed();

					// navigate back to timelogs
					topNavigationBar.navigateToPage("Time Logs", "My Time Logs");

				} else {
					
					/*-----------------------FILE A LEAVE MODAL-----------------------------------------------------------*/
					// Click cancel button
					fileALeaveModal.clickCancelButton();

					// verify File A Leave modal is NOT displayed
					fileALeaveModal.verifyFileALeaveModalIsNotDislayed();
				}

			}

		}

	}

}
