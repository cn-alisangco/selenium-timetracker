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
import pageObjects.timetracker.v2.NavigationBar;
import utilities.ExcelReader;
import utilities.UserHelper;

public class TC0017_TimeTracker_FileALeavePopUp_SubmitButton_SelectedLeaveDoesNotHaveBalance extends BaseClass {

	LoginPage loginPage;
	NavigationBar navigationBar;
	MyTimeLogsPage myTimeLogsPage;
	FileALeave_Modal fileALeaveModal;
	LeaveApplicationsPage leaveApplicationsPage;

	// Initialize web element variables--------------------
	// links
	List<WebElement> fileALeaveLinks;
	List<String> fileALeaveLinkDates;

	// field elements
	WebElement leaveTypeDropDown;
	WebElement leaveReasonDropDown;
	WebElement commentField;
	WebElement contactNumberField;

	// Login Test Data---------------------
	String sheetName = "Login";
	String recordID = "valid_credentials";

	// test data---------------------------
	List<String> leaveTypes = Arrays.asList("Call Back Vacation Leave", "Excess Earned Leaves", "Excess General Leaves",
			"On-call Vacation Leave", "Overtime Vacation Leave");
	String warningMessage = "You do not have enough available balance at this time to cover this leave. Your leave application is being allowed to go through on the assumption that you will be earning enough credits to cover the leave.";

	private void initialize() {
		loginPage = PageFactory.initElements(getDriver(), LoginPage.class);
		navigationBar = PageFactory.initElements(getDriver(), NavigationBar.class);
		myTimeLogsPage = PageFactory.initElements(getDriver(), MyTimeLogsPage.class);
		fileALeaveModal = PageFactory.initElements(getDriver(), FileALeave_Modal.class);
		leaveApplicationsPage = PageFactory.initElements(getDriver(), LeaveApplicationsPage.class);

		// set File A Leave links
		fileALeaveLinks = myTimeLogsPage.getAllFileALeaveButtons();

		// Set field elements
		leaveTypeDropDown = fileALeaveModal.getLeaveTypeDropdown();
		leaveReasonDropDown = fileALeaveModal.getLeaveReasonDropdown();
		commentField = fileALeaveModal.getCommentsField();
		contactNumberField = fileALeaveModal.getContactNumberField();

	}

	@Test
	public void TC0017_TimeTracker_FileALeavePopUp_SubmitButton_SelectedLeaveDoesNotHaveBalance() {

		// Initialize
		initialize();

		// Log in to Timetracker with valid credentials
		HashMap<String, String> loginCredentials = loginPage.getLoginCredentialsTestData(testDataLoc, sheetName,
				recordID);
		loginPage.login(loginCredentials.get("username"), loginCredentials.get("password"));

		// get dates with file a leave link
		fileALeaveLinkDates = myTimeLogsPage.getFileALeaveLinkDates("M/d/yyyy");

		for (int i = 0; i < fileALeaveLinks.size(); i++) {

			// get file a leave link date with the index i
			String fileALeavelinkDate = fileALeaveLinkDates.get(i);

			// Select a Leave Type with a running balance equal to zero
			for (String leaveType : leaveTypes) {
				// click File A Leave link for the corresponding date
				myTimeLogsPage.clickFileALeaveButton(fileALeavelinkDate);

				// verify File A Leave modal is displayed
				fileALeaveModal.verifyFileALeaveModalIsDisplayed();

				// check running balance
				String runningBalance = fileALeaveModal.getAvailableLeaveBalance(leaveType);
			

				switch (runningBalance) {
				case "0":
					// verify leave type was selected
					fileALeaveModal.verifydropDownOptionIsSelected(leaveTypeDropDown, leaveType);

					// Select a random Leave Reason---------------------------------
					// get a random leave reason
					String randomLeaveReason = fileALeaveModal.getRandomLeaveReason();

					// select the random leave reason
					fileALeaveModal.selectDropDownOption(leaveReasonDropDown, randomLeaveReason);

					// verify random leave reason was indeed selected
					fileALeaveModal.verifydropDownOptionIsSelected(leaveReasonDropDown, randomLeaveReason);

					// Fill out Remarks/Comment field---------------------------------
					// enter text in comments text box
					String comment = "TEST COMMENT/REMARK";
					fileALeaveModal.enterTextInRemarksTextBox(comment);

					// verify entered text is displayed
					fileALeaveModal.verifyRemarksTextBoxValue(comment);

					// Fill out Contact Number field
					// Verify other required fields are blank/has no value
					String contactNumber = "0123456789";
					fileALeaveModal.enterContactNumber(contactNumber);

					// verify entered text is displayed
					fileALeaveModal.verifyContactNumberTextBoxValue(contactNumber);

					// Click Submit button
					fileALeaveModal.clickSubmitButton();

					// Verify error/required messages are displayed
					// verify error messages for the fields WITH input are NOT displayed
					fileALeaveModal.verifyErrorMessageForFieldIsNotDisplayed(leaveTypeDropDown);
					fileALeaveModal.verifyErrorMessageForFieldIsNotDisplayed(leaveReasonDropDown);
					fileALeaveModal.verifyErrorMessageForFieldIsNotDisplayed(commentField);
					fileALeaveModal.verifyErrorMessageForFieldIsNotDisplayed(contactNumberField);

					// Verify Success Toast
					// Verify warning popup is displayed
					fileALeaveModal.verifyWarningPopupIsDisplayed();

					// verify warning popup message
					fileALeaveModal.verifyWarningPopupMessage(warningMessage);

					// click OK
					fileALeaveModal.clickOkButtonInWarningPopup();

					// verify success toast is not displayed
					fileALeaveModal.verifyWarningPopupIsNotDisplayed();

					// Verify Success Toast
					// Verify toast is displayed
					fileALeaveModal.verifyToastIsDisplayed();

					// verify toast message
					fileALeaveModal.verifyToastMessage("Leave Application Saved!");

					// wait for file a leave modal to disappear
					fileALeaveModal.waitForFileALeaveModalToDisappear();

					// Verify submitted leave is in the remarks column of corresponding timelog date
					myTimeLogsPage.verifySubmittedLeaveIsInRemarksColumn(fileALeavelinkDate, leaveType);

					// Recall filed leave //go to leave applications page
					navigationBar.navigateToPage("Leaves", "Applications");
					leaveApplicationsPage.verifySubmittedLeaveIsDisplayedInLeaveAppTable(fileALeavelinkDate, leaveType);
					leaveApplicationsPage.recallAllLeaveApplications();
					leaveApplicationsPage.verifyLeaveApplicationsTableIsNotDisplayed();

					// navigate back to timelogs
					navigationBar.navigateToPage("Time Logs", "My Time Logs");

					break;

				default:
					// Click cancel button
					fileALeaveModal.clickCancelButton();

					// verify File A Leave modal is NOT displayed
					fileALeaveModal.verifyFileALeaveModalIsNotDislayed();

				}

			}

		}

	}

}
