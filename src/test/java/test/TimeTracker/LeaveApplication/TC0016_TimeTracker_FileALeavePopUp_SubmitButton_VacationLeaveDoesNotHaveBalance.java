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

public class TC0016_TimeTracker_FileALeavePopUp_SubmitButton_VacationLeaveDoesNotHaveBalance extends BaseClass {

	String testDescription = "Verify that there will be a Warning when 'Vacation Leave' type is selected and Submit button is clicked";

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
	String recordID = "accountWithZeroVacationLeaveBalance";
	String accountPrecondition = "Account with 0 Balance for leave type: Vacation Leave";
	HashMap<String, String> loginCredentials;

	// test data---------------------------
	String fileALeaveLinkDateFormat = "M/d/yyyy";
	String leaveType = "Vacation Leave";
	String randomLeaveReason;
	String testComment = "TEST COMMENT/REMARK";
	String testContactNumber = "0123456789";
	String errorMessage = "You don't have enough balance (0 day/s) to cover your leave (1 day/s).";


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
	public void TC0016_TimeTracker_FileALeavePopUp_SubmitButton_VacationLeaveDoesNotHaveBalance() {

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
			
			// click File A Leave link with index i
			myTimeLogsPage.clickFileALeaveButton(fileALeaveLinkDate);

			
			/*-----------------------FILE A LEAVE MODAL-----------------------------------------------------------*/
			
			// verify File A Leave modal is displayed
			fileALeaveModal.verifyFileALeaveModalIsDisplayed();

			// Select 'Vacation Leave' with zero balance---------------------------------
			fileALeaveModal.selectDropDownOption(leaveTypeDropDown, leaveType);

			// Select a random Leave Reason---------------------------------
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

			/*-----------------------WARNING POPUP---------------------------------------------------------------*/
			
			// Verify Warning Popup is displayed
			fileALeaveModal.verifyToastIsDisplayed();

			// verify error message in popup
			fileALeaveModal.verifyToastMessage(errorMessage);

			// Click OK in the Warning popup
			fileALeaveModal.clickOkButtonInPopup();

			// Verify toast is NOT displayed
			fileALeaveModal.verifyToastIsNotDisplayed();

			// Click close button
			fileALeaveModal.clickCloseButton();

			/*-----------------------TIME LOGS PAGE---------------------------------------------------------------*/
			
			// verify File A Leave modal is NOT displayed
			fileALeaveModal.verifyFileALeaveModalIsNotDisplayed();
		}

	}

}
