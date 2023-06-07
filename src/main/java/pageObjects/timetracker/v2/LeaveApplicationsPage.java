package pageObjects.timetracker.v2;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import utilities.UserHelper;

public class LeaveApplicationsPage extends UserHelper{

	WebDriver driver;
	
	//Locators -------------------------------------------------------
	
	//Containers(tables, rows)
	@FindBy(id = "LeaveApplicationsTable") WebElement leaveApplicationsTable;
	@FindBy(xpath = "//tbody/tr") List<WebElement> leaveApplicationsTableRows;
	
	//checkboxes
	@FindBy(id = "deleteAll") WebElement deleteAllCheckBox;
	
	//links
	@FindBy(id = "RecallLeaveApplication") WebElement recallLink;
	
	//Buttons
	@FindBy(xpath = "//button/*[text()='Ok']") WebElement confirmRecallButton;
	
	//Text
	@FindBy(xpath = "//div[contains(text(), 'No applications on record.')]") WebElement noLeaveApplicationsMessage;
	
	//Constructor
	public LeaveApplicationsPage(WebDriver driver) {
		this.driver = driver;
	}
	
	//Methods
	
	//ACTIONS---------------------------------------------------
	public void recallAllLeaveApplications() {

		//tick delete all checkbox in the leave applications table
		clickDeleteAllCheckBox();
		
		//click 'Recall' link
		clickRecallLink();
		
		//confirm leave recall
		clickConfirmRecallButton();
		
		
		String methodName = "Recall all leave applications";
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), methodName);
		
	}
	
	private void clickConfirmRecallButton() {

		waitForClickable(confirmRecallButton);
		confirmRecallButton.click();
		
		String methodName = "Click 'Ok' button in the confirm recall pop-up";
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), methodName);
	}

	private void clickRecallLink() {

		waitForClickable(recallLink);
		recallLink.click();
		
		String methodName = "Click 'Recall' link";
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), methodName);
		
	}

	private void clickDeleteAllCheckBox() {

		waitForClickable(deleteAllCheckBox);
		deleteAllCheckBox.click();
		
		String methodName = "Click delete all checkbox in the leave applications table";
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), methodName);
		
	}

	
	//VERIFICATIONS---------------------------------------------------
	public void verifySubmittedLeaveIsDisplayedInLeaveAppTable(String date, String leaveType) {
		waitForElement(leaveApplicationsTable);
		
		boolean rowFound = false;
		for (int i=0 ; i < leaveApplicationsTableRows.size() ; i++) {
			
			String elementText = leaveApplicationsTableRows.get(i).getText();
			
			if (elementText.contains(date) && elementText.contains(leaveType) && elementText.contains("Submitted")) {
				rowFound = true;
			}
		}
				
		Assert.assertTrue(rowFound);
		
		String methodName = "Verify that submitted leave with date '" + date + "' and type '" + leaveType + "' is in the Leave Applications table";
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), methodName);
	}

	public void verifyLeaveApplicationsTableIsNotDisplayed() {
		waitForElement(noLeaveApplicationsMessage);
		validateElementIsNotDisplayed(leaveApplicationsTable);
		
		String methodName = "Verify that the Leave Applications Table is not displayed";
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), methodName);
	}
}
