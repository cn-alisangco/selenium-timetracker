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

public class TC0014_TimeTracker_FileALeavePopUp_SubmitButton_FilledUpAllRequiredFields_OneFieldEmpty extends BaseClass {

	LoginPage loginPage;
	MyTimeLogsPage myTimeLogsPage;
	FileALeave_Modal fileALeaveModal;
	
	//Initialize web element variables--------------------
	//field elements
	WebElement leaveTypeDropDown;
	WebElement leaveReasonDropDown;
	WebElement commentField;
	WebElement contactNumberField;
	
	//lists
	List<WebElement> fileALeaveLinks;
	
	//Login Test Data---------------------
	String sheetName = "Login";
	String recordID = "valid_credentials";
	
	
	private void initialize() {
		loginPage = PageFactory.initElements(getDriver(), LoginPage.class);
		myTimeLogsPage = PageFactory.initElements(getDriver(), MyTimeLogsPage.class);
		fileALeaveModal = PageFactory.initElements(getDriver(), FileALeave_Modal.class);
		
		//set File A Leave links
		fileALeaveLinks = myTimeLogsPage.getAllFileALeaveButtons();
		
		//Set field elements
		leaveTypeDropDown = fileALeaveModal.getLeaveTypeDropdown();
		leaveReasonDropDown = fileALeaveModal.getLeaveReasonDropdown();
		commentField = fileALeaveModal.getCommentsField();
		contactNumberField = fileALeaveModal.getContactNumberField();
	}
	
	
	
	@Test
	 public void TC0014_TimeTracker_FileALeavePopUp_SubmitButton_FilledUpAllRequiredFields_OneFieldEmpty(){
		
	        initialize();
	        
	        //Log in to Timetracker with valid credentials
	        HashMap<String, String> loginCredentials = loginPage.getLoginCredentialsTestData(testDataLoc, sheetName, recordID);
	        loginPage.login(loginCredentials.get("username"), loginCredentials.get("password"));
	     
	        //Iterate over each fileALeaveLink
	        for (int i = 0; i < fileALeaveLinks.size(); i++) {
	        	
	        	//click the link
	        	myTimeLogsPage.clickFileALeaveButton(i);
	        	
	        	//verify File A Leave modal is displayed
	        	fileALeaveModal.verifyFileALeaveModalIsDisplayed();
	        	
	        	//Select a random Leave Type---------------------------------
	        		//get a random leave type
	        		String randomLeaveType = fileALeaveModal.getRandomLeaveType();
	        	
	        		//select the random leave type
	        		fileALeaveModal.selectDropDownOption(leaveTypeDropDown, randomLeaveType);
	        		
	        		//verify random leave reason was indeed selected
	        		fileALeaveModal.verifydropDownOptionIsSelected(leaveTypeDropDown, randomLeaveType);
	        		
	        	//Select a random Leave Reason---------------------------------
	        		//get a random leave reason
	        		String randomLeaveReason = fileALeaveModal.getRandomLeaveReason();
	        	
	        		//select the random leave reason
	        		fileALeaveModal.selectDropDownOption(leaveReasonDropDown, randomLeaveReason);
	        		
	        		//verify random leave reason was indeed selected
	        		fileALeaveModal.verifydropDownOptionIsSelected(leaveReasonDropDown, randomLeaveReason);
	        	
	        		
	        	//Fill out Remarks/Comment field---------------------------------
	        		//enter text in comments text box
	        		String comment = "TEST COMMENT/REMARK";
	        		fileALeaveModal.enterTextInRemarksTextBox(comment);
	        		
	        		//verify entered text is displayed
	        		fileALeaveModal.verifyRemarksTextBoxValue(comment);
		        
		        
		        //Verify other required fields are blank/has no value
		        fileALeaveModal.verifyFieldIsEmpty(contactNumberField);
	        	
		        
	        	//Click Submit button
	        	fileALeaveModal.clickSubmitButton();
	        	
	        	//Verify error/required messages are displayed
	        	
		        	//verify error messages for the fields WITH input are NOT displayed
	        		fileALeaveModal.verifyErrorMessageForFieldIsNotDisplayed(leaveTypeDropDown);
	        		fileALeaveModal.verifyErrorMessageForFieldIsNotDisplayed(leaveReasonDropDown);
	        		fileALeaveModal.verifyErrorMessageForFieldIsNotDisplayed(commentField);
	        		
	        		//verify error messages for the fields WITHOUT input are displayed
	        		fileALeaveModal.verifyErrorMessageForFieldIsDisplayed(contactNumberField);
	        		
	        		//verify error message text are correct
	        		fileALeaveModal.verifyErrorMessageTextForField(contactNumberField, "Required");
	        	
	        		
	        	//Click cancel button
	        	fileALeaveModal.clickCancelButton();
	        	
	        	
	        	//verify File A Leave modal is NOT displayed
	        	fileALeaveModal.verifyFileALeaveModalIsNotDislayed();
	        }
	       
	    }
	
}


