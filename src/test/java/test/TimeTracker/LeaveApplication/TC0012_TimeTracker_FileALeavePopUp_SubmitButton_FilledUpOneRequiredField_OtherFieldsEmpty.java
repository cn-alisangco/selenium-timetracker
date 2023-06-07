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
import utilities.ExcelReader;
import utilities.UserHelper;

public class TC0012_TimeTracker_FileALeavePopUp_SubmitButton_FilledUpOneRequiredField_OtherFieldsEmpty extends BaseClass {

	LoginPage loginPage;
	MyTimeLogsPage myTimeLogsPage;
	FileALeave_Modal fileALeaveModal;
	
	//Initialize web element variables--------------------
	//field elements
	WebElement leaveTypeDropDown;
	WebElement leaveReasonDropDown;
	WebElement commentField;
	WebElement contactNumberField;
	
	//Login Test Data---------------------
	String sheetName = "Login";
	String recordID = "valid_credentials";
	
	//test data---------------------------
	List<String> leaveTypes = Arrays.asList("Banked General Leave", "Call Back Vacation Leave", "Excess Earned Leaves",
			"Excess General Leaves", "General Leave", "Leave Without Pay", "On-call Vacation Leave",
			"Overtime Vacation Leave", "Service Incentive Leave", "Solo Parent Leave", "Unauthorized LWOP",
			"Vacation Leave");
	
	
	
	private void initialize() {
		loginPage = PageFactory.initElements(getDriver(), LoginPage.class);
		myTimeLogsPage = PageFactory.initElements(getDriver(), MyTimeLogsPage.class);
		fileALeaveModal = PageFactory.initElements(getDriver(), FileALeave_Modal.class);
		
		//Set field elements
		leaveTypeDropDown = fileALeaveModal.getLeaveTypeDropdown();
		leaveReasonDropDown = fileALeaveModal.getLeaveReasonDropdown();
		commentField = fileALeaveModal.getCommentsField();
		contactNumberField = fileALeaveModal.getContactNumberField();
	}
	
	
	
	@Test
	 public void TC0012_TimeTracker_FileALeavePopUp_SubmitButton_FilledUpOneRequiredField_OtherFieldsEmpty(){
		
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
	        	
	        	//Select a random Leave Type
	        		//get a random leave type
	        		int index = UserHelper.generateRandomNumber(0, leaveTypes.size() - 1);
	        		String randomLeaveType = leaveTypes.get(index);
	        	
	        		//select the random leave type
	        		fileALeaveModal.selectDropDownOption(leaveTypeDropDown, randomLeaveType);
	        		
	        		//verify random leave reason was indeed selected
	        		fileALeaveModal.verifydropDownOptionIsSelected(leaveTypeDropDown, randomLeaveType);
	        	
	        	//Verify required fields are blank/has no value
		        fileALeaveModal.verifyFieldIsEmpty(leaveReasonDropDown);
		        fileALeaveModal.verifyFieldIsEmpty(commentField);
		        fileALeaveModal.verifyFieldIsEmpty(contactNumberField);
	        	
	        	//Click Submit button
	        	fileALeaveModal.clickSubmitButton();
	        	
	        	//Verify error/required messages are displayed
	        		
		        	//verify error messages for the fields WITH input are NOT displayed
	        		fileALeaveModal.verifyErrorMessageForFieldIsNotDisplayed(leaveTypeDropDown);
	        		
	        		//verify error messages for the fields WITHOUT input are displayed
	        		fileALeaveModal.verifyErrorMessageForFieldIsDisplayed(leaveReasonDropDown);
	        		fileALeaveModal.verifyErrorMessageForFieldIsDisplayed(commentField);
	        		fileALeaveModal.verifyErrorMessageForFieldIsDisplayed(contactNumberField);
	        		
	        		//verify error message text are correct
	        		fileALeaveModal.verifyErrorMessageTextForField(leaveReasonDropDown, "Choose a Reason");
	        		fileALeaveModal.verifyErrorMessageTextForField(commentField, "Required");
	        		fileALeaveModal.verifyErrorMessageTextForField(contactNumberField, "Required");
	        	
	        		
	        	//Click cancel button
	        	fileALeaveModal.clickCancelButton();
	        	
	        	
	        	//verify File A Leave modal is NOT displayed
	        	fileALeaveModal.verifyFileALeaveModalIsNotDislayed();
	        }
	       
	    }
	
}


