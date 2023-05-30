package pageObjects.timetracker.v2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import utilities.UserHelper;

public class FileALeave_Modal extends UserHelper {
	WebDriver driver;

	// locators
	@FindBy(xpath = "//span[@id=\"ui-dialog-title-dialog-modal-leave\"]/following-sibling::a")
	WebElement fileALeaveModalCloseButton;
	@FindBy(id = "dialog-modal-leave")
	WebElement fileALeaveModalBody;
	@FindBy(id = "LeaveReason")
	WebElement leaveReasonField;
	@FindBy(xpath = "//div[contains(text(),'Date Applied')]/following-sibling::div")
	WebElement dateApplied;
	@FindBy(id = "LeaveType")
	WebElement leaveTypeDropdown;
	@FindBy(xpath = "//select[@id='LeaveType']/option")
	List<WebElement> leaveTypeOptions;
	@FindBy(id = "LeaveFrom")
	WebElement leaveFromField;
	@FindBy(id = "LeaveTo")
	WebElement leaveToField;

	public FileALeave_Modal(WebDriver driver) {
		this.driver = driver;
	}

	public void verifyFileALeaveModalIsDisplayed() {

		// verify "File a Leave" modal exists
		waitForElement(fileALeaveModalCloseButton);
		waitForElement(fileALeaveModalBody);
		validateElementIsDisplayed(fileALeaveModalCloseButton);
		validateElementIsDisplayed(fileALeaveModalBody);
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(),
				"Verify 'File a Leave' modal is displayed");

	}

	public void clickCloseButton() {

		// close "File a Leave" modal
		waitForElement(fileALeaveModalCloseButton);
		fileALeaveModalCloseButton.click();
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(),
				"Click close button in the 'File a leave' modal");

	}

	public void enterLeaveReason() {

		waitForElement(leaveReasonField);
		leaveReasonField.sendKeys("TEST");
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Enter leave reason");

	}

	public void verifyDateAppliedIsCurrentDate() {
		waitForElement(dateApplied);

		// get applied date text
		String dateAppliedText = dateApplied.getText();

		// get date today in the M/d/yyyy format
		String dateToday = getTodayDate("M/d/yyyy");

		// verify date applied and date today are equal
		Assert.assertEquals(dateAppliedText, dateToday);

		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(),
				"Verify Date Applied in the popup is the date today");
	}

	public void verifyFileALeaveModalIsNotDislayed() {

		// verify "File a Leave" modal exists
		validateElementIsNotDisplayed(fileALeaveModalCloseButton);
		validateElementIsNotDisplayed(fileALeaveModalBody);
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(),
				"Verify 'File a Leave' modal is NOT dislayed");

	}

	public void verifyLeaveTypeOptions(List<String> leaveTypeOptions) {
		waitForElement(leaveTypeDropdown);

		// get a list of all leave type options displayed in the app
		List<String> leaveTypeOptionsText = getLeaveTypeOptionsText();

		// click dropdown
		leaveTypeDropdown.click();

		// verify that the leaveTypeOptionsText arraylist contains each string in the
		// leaveTypeOptions parameter
		for (String leaveTypeOption : leaveTypeOptions) {

			boolean leaveTypeExists = leaveTypeOptionsText.contains(leaveTypeOption);
			System.out.println(leaveTypeOption);
			Assert.assertTrue(leaveTypeExists);

			String methodName = "Verify the ff. leave type is available: " + leaveTypeOption;
			reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), methodName);
		}

	}

	public void selectLeaveType(String leaveType) {

		// declare leavetype dropdown as an instance of the Select class
		Select leaveTypeDrp = new Select(leaveTypeDropdown);

		// select leave type
		leaveTypeDrp.selectByVisibleText(leaveType);

		String methodName = "Select the ff. leave type: " + leaveType;
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), methodName);
	}

	public void verifyLeaveTypeIsSelected(String leaveType) {
		// declare leavetype dropdown as an instance of the Select class
		Select leaveTypeDrp = new Select(leaveTypeDropdown);
		
		String selectedLeaveType = leaveTypeDrp.getFirstSelectedOption().getText();
		Assert.assertEquals(selectedLeaveType, leaveType);

		String methodName = "Verify the ff. leave type is selected: " + leaveType;
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), methodName);
	}

	public void isInCorrectDateFormat(String FromOrToDateType, String dateString, String format) {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        boolean dateParseable;
		try {
			dateFormat.parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Unable to parse date to the format: " + format);
			dateParseable = false;
		}
		dateParseable = true;
		
		Assert.assertTrue(dateParseable);
		
		String dateTypeString = (FromOrToDateType == "From") ? "From" : "To";
		String methodName = "Verify the " + dateTypeString + " date : " + dateString + " is in the format: " + format;
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), methodName);
	}
	
	public String getFromDate() {
		waitForElement(leaveFromField);
		return leaveFromField.getAttribute("value");
	}
	
	public String getToDate() {
		waitForElement(leaveToField);
		return leaveToField.getAttribute("value");
	}
	
	public void verifyFromDateIsEqualTimeLogDate(String fromDate, String timeLogDate) {
		Assert.assertEquals(fromDate, timeLogDate);
		String methodName = "Verify the From date: " + fromDate + " is equal to the timelog date: " + timeLogDate;
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), methodName);
	}
	
	public void verifyToDateIsEqualTimeLogDate(String toDate, String timeLogDate) {
		Assert.assertEquals(toDate, timeLogDate);
		String methodName = "Verify the To date: " + toDate + " is equal to the timelog date: " + timeLogDate;
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), methodName);
	}
	
	
	
	
	
	//private methods------------------------------------
	private List<String> getLeaveTypeOptionsText() {

		// returns a list of all leave type options represented as string
		List<String> leavyTypeOptionsText = new ArrayList<String>();

		for (WebElement leaveTypeOption : leaveTypeOptions) {
			waitForElement(leaveTypeOption);
			leavyTypeOptionsText.add(leaveTypeOption.getText());
		}

		return leavyTypeOptionsText;
	}
}
