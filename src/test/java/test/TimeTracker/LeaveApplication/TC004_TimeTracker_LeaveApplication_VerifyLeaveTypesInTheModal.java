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

public class TC004_TimeTracker_LeaveApplication_VerifyLeaveTypesInTheModal extends BaseClass {
	// variables
	LoginPage loginPage;
	MyTimeLogsPage myTimeLogsPage;
	FileALeave_Modal fileALeaveModal;

	List<String> leaveTypes = Arrays.asList("Banked General Leave", "Call Back Vacation Leave", "Excess Earned Leaves",
			"Excess General Leaves", "General Leave", "Leave Without Pay", "On-call Vacation Leave",
			"Overtime Vacation Leave", "Service Incentive Leave", "Solo Parent Leave", "Unauthorized LWOP",
			"Vacation Leave");

	
	// Login Test Data
	String sheetName = "Login";
	String recordID = "valid_credentials";

	private void initialize() {
		loginPage = PageFactory.initElements(getDriver(), LoginPage.class);
		myTimeLogsPage = PageFactory.initElements(getDriver(), MyTimeLogsPage.class);
		fileALeaveModal = PageFactory.initElements(getDriver(), FileALeave_Modal.class);
	}

	@Test
	public void TC004_TimeTracker_LeaveApplication_VerifyLeaveTypesInTheModal() {

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

			// 2. Verify leavetypes
			fileALeaveModal.verifyLeaveTypeOptions(leaveTypes);
			
			
			//3. Select the leave types
			for (String leaveType : leaveTypes) {
				//Select a leave type
				fileALeaveModal.selectLeaveType(leaveType);
				
				//Verify leave type was indeed selected
				fileALeaveModal.verifyLeaveTypeIsSelected(leaveType);
			}
			
			// 4. Close modal
			fileALeaveModal.clickCloseButton();

			// 5. verify File A Leave modal is NOT displayed
			fileALeaveModal.verifyFileALeaveModalIsNotDislayed();
		}

		
	
	}

}
