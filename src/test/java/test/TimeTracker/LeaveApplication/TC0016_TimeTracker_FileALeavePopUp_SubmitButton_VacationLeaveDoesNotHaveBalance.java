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
	String recordID = "valid_credentials";

	// test data---------------------------
	List<String> leaveTypes = Arrays.asList("Banked General Leave", "Call Back Vacation Leave", "Excess Earned Leaves",
			"Excess General Leaves", "General Leave", "Leave Without Pay", "On-call Vacation Leave",
			"Overtime Vacation Leave", "Service Incentive Leave", "Solo Parent Leave", "Unauthorized LWOP",
			"Vacation Leave");
	List<String> leaveReasons = Arrays.asList("Emergency Leave", "Vacation Leave", "Sick Leave", "Others");

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

		// Log in to Timetracker with valid credentials of account whose Vacation Leave balance is zero
		HashMap<String, String> loginCredentials = loginPage.getLoginCredentialsTestData(testDataLoc, sheetName,
				recordID);
		loginPage.login(loginCredentials.get("username"), loginCredentials.get("password"), "");

		// click a file a random leave link and get the corresponding date
		
			// generate random index
			int randomFileALeaveLinkIndex = UserHelper.generateRandomNumber(0, fileALeaveLinks.size() - 1);
	
			// get file a leave link date with the random index
			fileALeaveLinkDates = myTimeLogsPage.getFileALeaveLinkDates("M/d/yyyy");
			String fileALeavelinkDate = fileALeaveLinkDates.get(randomFileALeaveLinkIndex);
			System.out.println(fileALeavelinkDate);
	
			// click File A Leave link with random index
			myTimeLogsPage.clickFileALeaveButton(randomFileALeaveLinkIndex);
	
			// verify File A Leave modal is displayed
			fileALeaveModal.verifyFileALeaveModalIsDisplayed();

		// Select 'Vacation Leave' with zero balance---------------------------------
			boolean leaveTypeFound = false;
			String leaveType = "";
			for (int i = 0; i < leaveTypes.size(); i++) {
	
				// get leave type with index i
				String currentLeaveType = leaveTypes.get(i);
	
				// select leave type and check running balance
				String runningBalance = fileALeaveModal.getAvailableLeaveBalance(currentLeaveType);
				if (runningBalance != "0") {
	
					leaveTypeFound = true;
					leaveType = currentLeaveType;
					System.out.println(leaveType);
					
					// verify random leave reason was indeed selected
					fileALeaveModal.verifydropDownOptionIsSelected(leaveTypeDropDown, leaveType);
	
					break;
				}
			}

		// Select a random Leave Reason---------------------------------
			//get a random leave reason
    		String randomLeaveReason = fileALeaveModal.getRandomLeaveReason();
    	
    		//select the random leave reason
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

		// Verify error/required messages are displayed
			// verify error messages for the fields WITH input are NOT displayed
			fileALeaveModal.verifyErrorMessageForFieldIsNotDisplayed(leaveTypeDropDown);
			fileALeaveModal.verifyErrorMessageForFieldIsNotDisplayed(leaveReasonDropDown);
			fileALeaveModal.verifyErrorMessageForFieldIsNotDisplayed(commentField);
			fileALeaveModal.verifyErrorMessageForFieldIsNotDisplayed(contactNumberField);
			
		//Verify Success Toast
			//Verify toast is displayed
			fileALeaveModal.verifyToastIsDisplayed();
			
			//verify toast message
			fileALeaveModal.verifyToastMessage("Leave Application Saved!");
			
			//wait for file a leave modal to disappear
			fileALeaveModal.waitForFileALeaveModalToDisappear();
			
			
			//Verify submitted leave is in the remarks column  of corresponding timelog date
			myTimeLogsPage.verifySubmittedLeaveIsInRemarksColumn(fileALeavelinkDate, leaveType);
			  
			//Recall filed leave //go to leave applications page
			 navigationBar.navigateToPage("Leaves", "Applications");
			  
			//
			 leaveApplicationsPage.verifySubmittedLeaveIsDisplayedInLeaveAppTable(fileALeavelinkDate, leaveType);
			 leaveApplicationsPage.recallAllLeaveApplications();
			 leaveApplicationsPage.verifyLeaveApplicationsTableIsNotDisplayed();
	}

}
