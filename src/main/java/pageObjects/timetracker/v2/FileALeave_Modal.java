package pageObjects.timetracker.v2;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import utilities.UserHelper;



public class FileALeave_Modal extends UserHelper{
	WebDriver driver;
	
   
  //locators	
	@FindBy(xpath="//span[@id=\"ui-dialog-title-dialog-modal-leave\"]/following-sibling::a")
	    WebElement fileALeaveModalCloseButton;
	@FindBy(id="dialog-modal-leave")
    	WebElement fileALeaveModalBody;
	@FindBy(id="LeaveReason")
		WebElement leaveReasonField;
	@FindBy(xpath="//div[contains(text(),'Date Applied')]/following-sibling::div")
	WebElement dateApplied;
	

    
    
    public FileALeave_Modal(WebDriver driver) {
		this.driver = driver;
	}
    
    public void verifyFileALeaveModalIsDisplayed() {
    			
    	//verify "File a Leave" modal exists
    	waitForElement(fileALeaveModalCloseButton);
    	waitForElement(fileALeaveModalBody);
    	validateElementIsDisplayed(fileALeaveModalCloseButton);
    	validateElementIsDisplayed(fileALeaveModalBody);
    	reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Verify 'File a Leave' modal is displayed");
    	
    }
    
    
    public void clickCloseButton() {
		
    	//close "File a Leave" modal
    	waitForElement(fileALeaveModalCloseButton);
    	fileALeaveModalCloseButton.click();	
    	reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Click close button in the 'File a leave' modal");
    	
    }
    
    public void enterLeaveReason() {
		
    	waitForElement(leaveReasonField);
    	leaveReasonField.sendKeys("TEST");
    	reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Enter leave reason");
    	
    }
    
    public void verifyDateAppliedIsCurrentDate() {
    	waitForElement(dateApplied);
    	
    	//get applied date text
    	String dateAppliedText = dateApplied.getText();
    	
    	//get date today in the M/d/yyyy format
    	String dateToday = getTodayDate("M/d/yyyy");
    	
    	//verify date applied and date today are equal
    	Assert.assertEquals(dateAppliedText, dateToday);
    	
    	reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Verify Date Applied in the popup is the date today");
    }
    
    
    public void verifyFileALeaveModalIsNotDislayed() {
		
    	//verify "File a Leave" modal exists
    	validateElementIsNotDisplayed(fileALeaveModalCloseButton);
    	validateElementIsNotDisplayed(fileALeaveModalBody);
    	reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Verify 'File a Leave' modal is NOT dislayed");
    	
    }
    
    

	
}
