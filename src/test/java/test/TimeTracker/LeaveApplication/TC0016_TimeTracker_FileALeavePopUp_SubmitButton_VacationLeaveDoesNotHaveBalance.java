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

public class TC0016_TimeTracker_FileALeavePopUp_SubmitButton_VacationLeaveDoesNotHaveBalance extends BaseClass {

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
	String recordID = "accountWithZeroVacationLeaveBalance";

	// test data---------------------------
	String errorMessage = "You don't have enough balance (0 day/s) to cover your leave (1 day/s).";

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
	public void TC0016_TimeTracker_FileALeavePopUp_SubmitButton_VacationLeaveDoesNotHaveBalance() {

		// Initialize
		initialize();

		// Log in to Timetracker with valid credentials of account whose Vacation Leave
		// balance is zero
		HashMap<String, String> loginCredentials = loginPage.getLoginCredentialsTestData(testDataLoc, sheetName,
				recordID);
		loginPage.login(loginCredentials.get("username"), loginCredentials.get("password"),
				"Vacation Leave balance = 0");

		for (int i = 0; i < fileALeaveLinks.size(); i++) {

			// click File A Leave link with index i
			myTimeLogsPage.clickFileALeaveButton(i);

			// verify File A Leave modal is displayed
			fileALeaveModal.verifyFileALeaveModalIsDisplayed();

			// Select 'Vacation Leave' with zero balance---------------------------------
			fileALeaveModal.selectDropDownOption(leaveTypeDropDown, "Vacation Leave");

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

			// verify error messages for the fields WITH input are NOT displayed
			fileALeaveModal.verifyErrorMessageForFieldIsNotDisplayed(leaveTypeDropDown);
			fileALeaveModal.verifyErrorMessageForFieldIsNotDisplayed(leaveReasonDropDown);
			fileALeaveModal.verifyErrorMessageForFieldIsNotDisplayed(commentField);
			fileALeaveModal.verifyErrorMessageForFieldIsNotDisplayed(contactNumberField);

			// Verify Warning Popup
			// Verify toast is displayed
			fileALeaveModal.verifyToastIsDisplayed();

			// verify toast message
			fileALeaveModal.verifyToastMessage(errorMessage);

			// Click OK in the Warning popup
			fileALeaveModal.clickOkButtonInPopup();

			// Verify toast is NOT displayed
			fileALeaveModal.verifyToastIsNotDisplayed();

			// Click cancel button
			fileALeaveModal.clickCancelButton();

			// verify File A Leave modal is NOT displayed
			fileALeaveModal.verifyFileALeaveModalIsNotDislayed();
		}

	}

}
