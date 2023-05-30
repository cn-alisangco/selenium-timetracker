package test.TimeTracker.LeaveApplication;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import base.BaseClass;
import junit.framework.Assert;
import pageObjects.timetracker.HomePage;
import pageObjects.timetracker.v2.FileALeave_Modal;
import pageObjects.timetracker.v2.LoginPage;
import pageObjects.timetracker.v2.MyTimeLogsPage;
import utilities.ExcelReader;
import utilities.UserHelper;

public class TC006_TimeTracker_LeaveApplication_VerifyHalfDayCheckboxInTheModal extends BaseClass {

	LoginPage loginPage;
	MyTimeLogsPage myTimeLogsPage;
	FileALeave_Modal fileALeaveModal;

	// Test Data
	String sheetName = "Login";
	String recordID = "valid_credentials";
	String dateFormat = "MM/dd/yyyy";

	private void initialize() {
		loginPage = PageFactory.initElements(getDriver(), LoginPage.class);
		myTimeLogsPage = PageFactory.initElements(getDriver(), MyTimeLogsPage.class);
		fileALeaveModal = PageFactory.initElements(getDriver(), FileALeave_Modal.class);
	}

	@Test
	public void TC006_TimeTracker_LeaveApplication_VerifyHalfDayCheckboxInTheModal() {

		initialize();

		// Log in to Timetracker with valid credentials
		HashMap<String, String> loginCredentials = loginPage.getLoginCredentialsTestData(testDataLoc, sheetName,
				recordID);
		loginPage.login(loginCredentials.get("username"), loginCredentials.get("password"));

		// get all "File a Leave" links/button for regular shift dates and store in a variable
		List<WebElement> fileALeaveLinks = myTimeLogsPage.getAllFileALeaveButtons();

		// Iterate over each fileALeaveLink
		for (int i = 0; i < fileALeaveLinks.size(); i++) {

			// click the file a leave link with the same index as the regular shift date
			myTimeLogsPage.clickFileALeaveButton(i);

			// Verify File A Leave modal is displayed
			fileALeaveModal.verifyFileALeaveModalIsDisplayed();

			// Verify default state of checkbox is UNCHECKED
			fileALeaveModal.verifyHalfDayCheckIsUnchecked();

			// Verify Checkbox is CHECKED -------------------------
			// Click half day checkbox
			fileALeaveModal.clickHalfDayCheckbox();
			// Verify checkbox is CHECKED
			fileALeaveModal.verifyHalfDayCheckIsChecked();

			// Verify Checkbox is UNCHECKED --------------------------
			// Click half day checkbox
			fileALeaveModal.clickHalfDayCheckbox();
			// Verify checkbox is UNCHECKED
			fileALeaveModal.verifyHalfDayCheckIsUnchecked();

			// Close modal
			fileALeaveModal.clickCloseButton();

			// verify File A Leave modal is NOT displayed
			fileALeaveModal.verifyFileALeaveModalIsNotDislayed();
		}

	}

}
