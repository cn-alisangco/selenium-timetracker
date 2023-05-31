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
	// variables
	LoginPage loginPage;
	MyTimeLogsPage myTimeLogsPage;
	FileALeave_Modal fileALeaveModal;

	List<String> leaveReasonOptions = Arrays.asList("Emergency Leave", "Sick Leave", "Vacation Leave", "Others");

	// Login Test Data
	String sheetName = "Login";
	String recordID = "valid_credentials";

	private void initialize() {
		loginPage = PageFactory.initElements(getDriver(), LoginPage.class);
		myTimeLogsPage = PageFactory.initElements(getDriver(), MyTimeLogsPage.class);
		fileALeaveModal = PageFactory.initElements(getDriver(), FileALeave_Modal.class);
	}

	@Test
	public void TC007_TimeTracker_LeaveApplication_VerifyReasonsInTheModal() {

		initialize();

		// Log in to Timetracker with valid credentials
		HashMap<String, String> loginCredentials = loginPage.getLoginCredentialsTestData(testDataLoc, sheetName,
				recordID);
		loginPage.login(loginCredentials.get("username"), loginCredentials.get("password"));

		// get all "File a Leave" links/button and store in a variable
		List<WebElement> fileALeaveLinks = myTimeLogsPage.getAllFileALeaveButtons();

		// Iterate over each fileALeaveLink
		for (int i = 0; i < fileALeaveLinks.size(); i++) {

			// 1. click the link
			myTimeLogsPage.clickFileALeaveButton(i);

			// 2. Verify leave reasons
			fileALeaveModal.verifyDropDownOptions(fileALeaveModal.getLeaveReasonDropdown(), fileALeaveModal.getLeaveReasonOptions(), leaveReasonOptions);

			// 3. Select the leave reasons
			for (String leaveReasonOption : leaveReasonOptions) {
				// Select a leave reason
				fileALeaveModal.selectDropDownOption(fileALeaveModal.getLeaveReasonDropdown(), leaveReasonOption);

				// Verify leave reason was indeed selected
				fileALeaveModal.verifydropDownOptionIsSelected(fileALeaveModal.getLeaveReasonDropdown(), leaveReasonOption);
			}

			// 4. Close modal
			fileALeaveModal.clickCloseButton();

			// 5. verify File A Leave modal is NOT displayed
			fileALeaveModal.verifyFileALeaveModalIsNotDislayed();
		}

	}

}
