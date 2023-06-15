package test.TimeTracker.LeaveApplication;

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

public class TC003_TimeTracker_File_A_Leave_PopUp_DateApplied extends BaseClass {
	
	String testDescription = "Verify that Date applied in 'File a leave' pop-up displays Date today";
	
	//Pages and elements
	LoginPage loginPage;
	MyTimeLogsPage myTimeLogsPage;
	FileALeave_Modal fileALeaveModal;
	List<WebElement> fileALeaveLinkDates;

	// Login data
	String sheetName = "Login";
	String recordID = "valid_credentials";
	String accountPrecondition = "Has valid credentials";
	HashMap<String, String> loginCredentials;

	//Test date
	String fileALeaveLinkDateFormat = "M/d/yyyy";
	
	private void initialize() {
		//initialize page elements
		loginPage = PageFactory.initElements(getDriver(), LoginPage.class);
		myTimeLogsPage = PageFactory.initElements(getDriver(), MyTimeLogsPage.class);
		fileALeaveModal = PageFactory.initElements(getDriver(), FileALeave_Modal.class);

		// get login test data
		loginCredentials = loginPage.getLoginCredentialsTestData(testDataLoc, sheetName, recordID);

		//print test description
		UserHelper.customReportLog("TEST DESCRIPTION: " + testDescription);
	}
	
	@Test
	 public void TC003_TimeTracker_File_A_Leave_PopUp_DateApplied(){
		
	        initialize();

			// Log in to Timetracker with valid credentials
			loginPage.login(loginCredentials.get("username"), loginCredentials.get("password"), accountPrecondition);

			// Get timelog date strings with 'File A Leave' button/link
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
				
				//Verify Date Applied in the popup is equal to the current date
	        	fileALeaveModal.verifyDateAppliedIsCurrentDate();

				// Close File a Leave modal
				fileALeaveModal.clickCloseButton();
				
	        	//2. verify File A Leave modal is NOT displayed
	        	fileALeaveModal.verifyFileALeaveModalIsNotDisplayed();
			}
	    }
	
}


