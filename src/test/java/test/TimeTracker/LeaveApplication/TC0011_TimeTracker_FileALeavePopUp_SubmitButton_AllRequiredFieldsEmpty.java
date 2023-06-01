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

public class TC0011_TimeTracker_FileALeavePopUp_SubmitButton_AllRequiredFieldsEmpty extends BaseClass {

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
	 public void TC0011_TimeTracker_FileALeavePopUp_SubmitButton_AllRequiredFieldsEmpty(){
		
	        initialize();
	        
	        //Log in to Timetracker with valid credentials
	        HashMap<String, String> loginCredentials = loginPage.getLoginCredentialsTestData(testDataLoc, sheetName, recordID);
	        loginPage.login(loginCredentials.get("username"), loginCredentials.get("password"));
	     
	        //get all "File a Leave" links/button and store in a variable
	        List<WebElement> fileALeaveLinks =  myTimeLogsPage.getAllFileALeaveButtons();
	    	
	        //Iterate over each fileALeaveLink
	        for (int i = 0; i < fileALeaveLinks.size(); i++) {
	        	
	        	//click the link
	        	myTimeLogsPage.clickFileALeaveButton(i);
	        	
	        	//verify File A Leave modal is displayed
	        	fileALeaveModal.verifyFileALeaveModalIsDisplayed();
	        	
	        	//Verify required fields are blank/has no value
	        		//get field elements
	        		WebElement leaveTypeDropDown = fileALeaveModal.getLeaveTypeDropdown();
	        		WebElement leaveReasonDropDown = fileALeaveModal.getLeaveTypeDropdown();
	        		WebElement commentField = fileALeaveModal.getLeaveTypeDropdown();
	        		WebElement contactNumberField = fileALeaveModal.getLeaveTypeDropdown();
	        		
	        		//Verify fields are empty
		        	fileALeaveModal.verifyFieldIsEmpty(leaveTypeDropDown);
		        	fileALeaveModal.verifyFieldIsEmpty(leaveReasonDropDown);
		        	fileALeaveModal.verifyFieldIsEmpty(commentField);
		        	fileALeaveModal.verifyFieldIsEmpty(contactNumberField);
	        	
	        	//Click Submit button
	        	fileALeaveModal.clickSubmitButton();
	        	
	        	//Verify error/required messages are displayed
	        		//get error message elements
	        		WebElement leaveTypeErrorMessage = fileALeaveModal.getLeaveTypeErrorMessage();
	        		WebElement leaveReasonErrorMessage = fileALeaveModal.getLeaveReasonErrorMessage();
	        		WebElement commentErrorMessage = fileALeaveModal.getCommentErrorMessage();
	        		WebElement contactNumberErrorMessage = fileALeaveModal.getContactNumberErrorMessage();
	        		
	        		//verify error messages for the fields are displayed
	        		fileALeaveModal.verifyErrorMessageForFieldIsDisplayed(leaveTypeDropDown, leaveTypeErrorMessage);
	        		fileALeaveModal.verifyErrorMessageForFieldIsDisplayed(leaveReasonDropDown, leaveReasonErrorMessage);
	        		fileALeaveModal.verifyErrorMessageForFieldIsDisplayed(commentField, commentErrorMessage);
	        		fileALeaveModal.verifyErrorMessageForFieldIsDisplayed(contactNumberField, contactNumberErrorMessage);
	        		
	        		//verify error message text are correct
	        		fileALeaveModal.verifyErrorMessageText(leaveTypeErrorMessage, "Choose a Leave Type");
	        		fileALeaveModal.verifyErrorMessageText(leaveReasonErrorMessage, "Choose a Reason");
	        		fileALeaveModal.verifyErrorMessageText(commentErrorMessage, "Required");
	        		fileALeaveModal.verifyErrorMessageText(contactNumberErrorMessage, "Required");
	        	
	        		
	        	//Click cancel button
	        	fileALeaveModal.clickCancelButton();
	        	
	        	
	        	//verify File A Leave modal is NOT displayed
	        	fileALeaveModal.verifyFileALeaveModalIsNotDislayed();
	        }
	       
	    }
	
}


