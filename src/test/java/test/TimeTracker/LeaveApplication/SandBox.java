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
import pageObjects.timetracker.v2.TopNavigationBar;
import utilities.ExcelReader;
import utilities.UserHelper;

public class SandBox extends BaseClass {

	LoginPage loginPage;
	TopNavigationBar navigationBar;
	MyTimeLogsPage myTimeLogsPage;
	FileALeave_Modal fileALeaveModal;
	

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
		navigationBar = PageFactory.initElements(getDriver(), TopNavigationBar.class);
		myTimeLogsPage = PageFactory.initElements(getDriver(), MyTimeLogsPage.class);
		fileALeaveModal = PageFactory.initElements(getDriver(), FileALeave_Modal.class);

		// set File A Leave links
		fileALeaveLinks = myTimeLogsPage.getAllFileALeaveButtons();
		

		// Set field elements
		leaveTypeDropDown = fileALeaveModal.getLeaveTypeDropdown();
		leaveReasonDropDown = fileALeaveModal.getLeaveReasonDropdown();
		commentField = fileALeaveModal.getCommentsField();
		contactNumberField = fileALeaveModal.getContactNumberField();

	}

	@Test
	public void SandBox() {

		// Initialize
		initialize();

		// Log in to Timetracker with valid credentials
		HashMap<String, String> loginCredentials = loginPage.getLoginCredentialsTestData(testDataLoc, sheetName,
				recordID);
		loginPage.login(loginCredentials.get("username"), loginCredentials.get("password"));

		
			  
			//Recall filed leave //go to leave applications page
			 navigationBar.navigateToPage("Leaves", "Applications");
			  
			//
			 
	}

}
