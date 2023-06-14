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

public class TC0014_TimeTracker_FileALeavePopUp_SubmitButton_FilledUpAllRequiredFields_OneFieldEmpty extends BaseClass {

	String testDescription = "Verify that there will be an error messages when user filled up all required field and one empty field and click Submit button";

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
	String randomLeaveReason;
	String testComment = "TEST COMMENT/REMARK";
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
	public void TC0014_TimeTracker_FileALeavePopUp_SubmitButton_FilledUpAllRequiredFields_OneFieldEmpty() {

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

			// Select a random Leave Type
			randomLeaveType = fileALeaveModal.getRandomLeaveType();
			fileALeaveModal.selectDropDownOption(leaveTypeDropDown, randomLeaveType);

			// verify random leave reason was indeed selected
			fileALeaveModal.verifydropDownOptionIsSelected(leaveTypeDropDown, randomLeaveType);

			// select a random leave reason
			randomLeaveReason = fileALeaveModal.getRandomLeaveReason();
			fileALeaveModal.selectDropDownOption(leaveReasonDropDown, randomLeaveReason);

			// verify random leave reason was indeed selected
			fileALeaveModal.verifydropDownOptionIsSelected(leaveReasonDropDown, randomLeaveReason);

			// Fill out Remarks/Comment field
			fileALeaveModal.enterTextInRemarksTextBox(testComment);

			// verify entered text is displayed
			fileALeaveModal.verifyRemarksTextBoxValue(testComment);

			// Verify other required fields are blank/has no value
			fileALeaveModal.verifyFieldIsEmpty(contactNumberField);

			// Click Submit button
			fileALeaveModal.clickSubmitButton();

			// Verify error/required messages are displayed

			// verify error messages for the fields WITH input are NOT displayed
			fileALeaveModal.verifyErrorMessageForFieldIsNotDisplayed(leaveTypeDropDown);
			fileALeaveModal.verifyErrorMessageForFieldIsNotDisplayed(leaveReasonDropDown);
			fileALeaveModal.verifyErrorMessageForFieldIsNotDisplayed(commentField);

			// verify error messages for the fields WITHOUT input are displayed
			fileALeaveModal.verifyErrorMessageForFieldIsDisplayed(contactNumberField);

			// verify error message text are correct
			fileALeaveModal.verifyErrorMessageTextForField(contactNumberField, "Required");

			// Click cancel button
			fileALeaveModal.clickCancelButton();

			/*-----------------------TIME LOGS PAGE---------------------------------------------------------------*/
			
			// verify File A Leave modal is NOT displayed
			fileALeaveModal.verifyFileALeaveModalIsNotDislayed();
		}

	}

}
