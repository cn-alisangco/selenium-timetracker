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
import pageObjects.timetracker.v2.LoginPage;
import pageObjects.timetracker.v2.MyTimeLogsPage;
import utilities.ExcelReader;
import utilities.UserHelper;

public class TC0012_TimeTracker_FileALeavePopUp_SubmitButton_FilledUpOneRequiredField_OtherFieldsEmpty
		extends BaseClass {

	String testDescription = "Verify that there will be an error messages when user filled up one required field and others as empty and click Submit button";

	// Pages and elements
	LoginPage loginPage;
	MyTimeLogsPage myTimeLogsPage;
	FileALeave_Modal fileALeaveModal;
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
	String randomLeaveType;
	String expectedLeaveReasonFieldErrorMessage = "Choose a Reason";
	String expectedCommentTextboxErrorMessage = "Required";
	String expectedContactNumberFieldErrorMessage = "Required";

	private void initialize() {
		loginPage = PageFactory.initElements(getDriver(), LoginPage.class);
		myTimeLogsPage = PageFactory.initElements(getDriver(), MyTimeLogsPage.class);
		fileALeaveModal = PageFactory.initElements(getDriver(), FileALeave_Modal.class);

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
	public void TC0012_TimeTracker_FileALeavePopUp_SubmitButton_FilledUpOneRequiredField_OtherFieldsEmpty() {

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
			
			

			/*-----------------------TIME LOGS PAGE---------------------------------------------------------------*/

			// click the link
			myTimeLogsPage.clickFileALeaveButton(fileALeaveLinkDate);

			/*-----------------------FILE A LEAVE MODAL-----------------------------------------------------------*/

			// verify File A Leave modal is displayed
			fileALeaveModal.verifyFileALeaveModalIsDisplayed();

			// select the random leave type
			randomLeaveType = fileALeaveModal.getRandomLeaveType();
			fileALeaveModal.selectDropDownOption(leaveTypeDropDown, randomLeaveType);

			// verify random leave type was indeed selected
			fileALeaveModal.verifydropDownOptionIsSelected(leaveTypeDropDown, randomLeaveType);

			// Verify required fields are blank/has no value
			fileALeaveModal.verifyFieldIsEmpty(leaveReasonDropDown);
			fileALeaveModal.verifyFieldIsEmpty(commentField);
			fileALeaveModal.verifyFieldIsEmpty(contactNumberField);

			// Click Submit button
			fileALeaveModal.clickSubmitButton();

			// verify error messages for the fields WITH input are NOT displayed
			fileALeaveModal.verifyErrorMessageForFieldIsNotDisplayed(leaveTypeDropDown);

			// verify error messages for the fields WITHOUT input are displayed
			fileALeaveModal.verifyErrorMessageForFieldIsDisplayed(leaveReasonDropDown);
			fileALeaveModal.verifyErrorMessageForFieldIsDisplayed(commentField);
			fileALeaveModal.verifyErrorMessageForFieldIsDisplayed(contactNumberField);

			// verify error message text are correct
			fileALeaveModal.verifyErrorMessageTextForField(leaveReasonDropDown, expectedLeaveReasonFieldErrorMessage);
			fileALeaveModal.verifyErrorMessageTextForField(commentField, expectedCommentTextboxErrorMessage);
			fileALeaveModal.verifyErrorMessageTextForField(contactNumberField, expectedContactNumberFieldErrorMessage);

			// Click close button
			fileALeaveModal.clickCloseButton();

			/*-----------------------TIME LOGS PAGE---------------------------------------------------------------*/

			// verify File A Leave modal is NOT displayed
			fileALeaveModal.verifyFileALeaveModalIsNotDisplayed();
		}

	}

}
