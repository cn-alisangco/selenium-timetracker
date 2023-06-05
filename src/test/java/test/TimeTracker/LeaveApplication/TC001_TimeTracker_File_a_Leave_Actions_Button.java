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

public class TC001_TimeTracker_File_a_Leave_Actions_Button extends BaseClass {

	LoginPage loginPage;
	MyTimeLogsPage myTimeLogsPage;
	FileALeave_Modal fileALeaveModal;
	List<WebElement> fileLeaveLinks;
	
	//Login Test Data
	String sheetName = "Login";
	String recordID = "valid_credentials";
	
	
	private void initialize() {
		loginPage = PageFactory.initElements(getDriver(), LoginPage.class);
		myTimeLogsPage = PageFactory.initElements(getDriver(), MyTimeLogsPage.class);
		fileALeaveModal = PageFactory.initElements(getDriver(), FileALeave_Modal.class);
		
		fileLeaveLinks = myTimeLogsPage.getAllFileALeaveButtons();
	}
	
	@Test
	 public void TC001_TimeTracker_File_a_Leave_Actions_Button(){
		
	        initialize();
	        
	        //Log in to Timetracker with valid credentials
	        HashMap<String, String> loginCredentials = loginPage.getLoginCredentialsTestData(testDataLoc, sheetName, recordID);
	        loginPage.login(loginCredentials.get("username"), loginCredentials.get("password"));
	     
	        //verify "File a Leave" button is visible in the list of Actions
	        myTimeLogsPage.verifyFileALeaveButtonsExist();
	    	
	        //Click File a Leave button
	        int randomIndex = UserHelper.generateRandomNumber(0, fileLeaveLinks.size() - 1);
	        myTimeLogsPage.clickFileALeaveButton(randomIndex);
	        
	        //Verify File a Leave modal is displayed
	        fileALeaveModal.verifyFileALeaveModalIsDisplayed();
	        
	        //Close File a Leave modal
	        fileALeaveModal.clickCloseButton();    

	    }
	
}


