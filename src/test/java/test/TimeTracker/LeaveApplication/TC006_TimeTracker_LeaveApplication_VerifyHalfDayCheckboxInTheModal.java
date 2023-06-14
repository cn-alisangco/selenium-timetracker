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

	String testDescription = "Verify that Half Day checkbox can be checked and uncheked";

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

	//Test data
	String fileALeaveLinkDateFormat = "M/d/yyyy";

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
	public void TC006_TimeTracker_LeaveApplication_VerifyHalfDayCheckboxInTheModal() {

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
