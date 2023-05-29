package test.TimeTracker.LeaveApplication;

import java.util.HashMap;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import base.BaseClass;
import pageObjects.timetracker.HomePage;
import pageObjects.timetracker.v2.FileALeave_Modal;
import pageObjects.timetracker.v2.LoginPage;
import pageObjects.timetracker.v2.MyTimeLogsPage;
import utilities.ExcelReader;

public class TC002_TimeTracker_FileALeavePopUp_CloseButton extends BaseClass {

	LoginPage loginPage;
	MyTimeLogsPage myTimeLogsPage;
	FileALeave_Modal fileALeaveModal;
	
	//Login Test Data
	String sheetName = "Login";
	String recordID = "valid_credentials";
	
	
	private void initialize() {
		loginPage = PageFactory.initElements(getDriver(), LoginPage.class);
		myTimeLogsPage = PageFactory.initElements(getDriver(), MyTimeLogsPage.class);
		fileALeaveModal = PageFactory.initElements(getDriver(), FileALeave_Modal.class);
	}
	
	@Test
	 public void TC002_TimeTracker_FileALeavePopUp_CloseButton(){
		
	        initialize();
	        
	        //Log in to Timetracker with valid credentials
	        HashMap<String, String> loginCredentials = loginPage.getLoginCredentialsTestData(testDataLoc, sheetName, recordID);
	        loginPage.login(loginCredentials.get("username"), loginCredentials.get("password"));
	     
	        //verify "File a Leave" button is visible in the list of Actions
	        myTimeLogsPage.verifyFileALeaveButtonsExist();
	    	
	        //Click File a Leave button
	        myTimeLogsPage.clickRandomFileALeaveButton();
	        
	        //Verify File a Leave modal is displayed
	        fileALeaveModal.verifyFileALeaveModalIsDisplayed();
	       	         

	    }
	
}


