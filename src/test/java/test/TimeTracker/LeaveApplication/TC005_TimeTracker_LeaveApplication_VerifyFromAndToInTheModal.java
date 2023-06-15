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

public class TC005_TimeTracker_LeaveApplication_VerifyFromAndToInTheModal extends BaseClass {

	String testDescription = "Verify that From and To in 'File a leave' pop-up displays the date when the user is having his/her Leave";

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

	// Test data
	String fileALeaveLinkDateFormat = "M/d/yyyy";
	String expectedFromAndToDateFormat = "MM/dd/yyyy";

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
	public void TC005_TimeTracker_LeaveApplication_VerifyFromAndToInTheModal() {

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

			// verify "File a Leave" button is visible in the list of Actions
			myTimeLogsPage.verifyFileALeaveLinkForDateIsDisplayed(fileALeaveLinkDate);

			// Click File a Leave button
			myTimeLogsPage.clickFileALeaveButton(fileALeaveLinkDate);

			// Verify File a Leave modal is displayed
			fileALeaveModal.verifyFileALeaveModalForDateIsDisplayed(fileALeaveLinkDate);

			// get from and to date strings in the modal
			String fromDate = fileALeaveModal.getFromDate();
			String toDate = fileALeaveModal.getToDate();

			// Verify format of from and to dates in the modal is correct (i.e. MM/dd/yyyy)
			fileALeaveModal.isInCorrectDateFormat(fromDate, expectedFromAndToDateFormat);
			fileALeaveModal.isInCorrectDateFormat(toDate, expectedFromAndToDateFormat);

			// Parse the fileALeaveLinkDate to a "MM/dd/yyyy" format
			String parsedFileALeaveLinkDate = UserHelper.parseDate(fileALeaveLinkDate, "MM/dd/yyyy");

			// Verify from and to dates is equal to the parsed time log date
			fileALeaveModal.verifyFromDateValue(fromDate, parsedFileALeaveLinkDate);
			fileALeaveModal.verifyToDateValue(toDate, parsedFileALeaveLinkDate);

			// Close modal
			fileALeaveModal.clickCloseButton();

			// verify File A Leave modal is NOT displayed
			fileALeaveModal.verifyFileALeaveModalIsNotDisplayed();

		}

	}

}
