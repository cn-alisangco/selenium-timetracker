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

public class TC008_TimeTracker_FileALeavePopUp_Comment extends BaseClass {
	// variables
	LoginPage loginPage;
	MyTimeLogsPage myTimeLogsPage;
	FileALeave_Modal fileALeaveModal;


	// Login Test Data
	String sheetName = "Login";
	String recordID = "valid_credentials";

	private void initialize() {
		loginPage = PageFactory.initElements(getDriver(), LoginPage.class);
		myTimeLogsPage = PageFactory.initElements(getDriver(), MyTimeLogsPage.class);
		fileALeaveModal = PageFactory.initElements(getDriver(), FileALeave_Modal.class);
	}

	@Test
	public void TC008_TimeTracker_FileALeavePopUp_Comment() {

		initialize();

		// Log in to Timetracker with valid credentials
		HashMap<String, String> loginCredentials = loginPage.getLoginCredentialsTestData(testDataLoc, sheetName,
				recordID);
		loginPage.login(loginCredentials.get("username"), loginCredentials.get("password"));

		// get all "File a Leave" links/button and store in a variable
		List<WebElement> fileALeaveLinks = myTimeLogsPage.getAllFileALeaveButtons();

		// Iterate over each fileALeaveLink
		for (int i = 0; i < fileALeaveLinks.size(); i++) {

			//lick the link
			myTimeLogsPage.clickFileALeaveButton(i);

			//Enter text in comment/remark text area
			String testComment = "TEST COMMENT";
			fileALeaveModal.enterTextInRemarksTextBox(testComment);
			
			//Verify value of textbox is the comment entered
			fileALeaveModal.verifyRemarksTextBoxValue(testComment);

			//Close modal
			fileALeaveModal.clickCloseButton();

			//verify File A Leave modal is NOT displayed
			fileALeaveModal.verifyFileALeaveModalIsNotDislayed();
		}

	}

}
