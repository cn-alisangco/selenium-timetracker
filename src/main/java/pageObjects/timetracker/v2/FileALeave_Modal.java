package pageObjects.timetracker.v2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
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

import utilities.ExcelReader;
import utilities.UserHelper;

public class FileALeave_Modal extends UserHelper {
	WebDriver driver;

	/* --------------------------------------------LOCATORS --------------------------------------- */
	// fields
	@FindBy(id = "LeaveFrom")
	WebElement leaveFromField;
	@FindBy(id = "LeaveTo")
	WebElement leaveToField;
	@FindBy(id = "IsHalfday")
	WebElement halfDayCheckbox;
	@FindBy(xpath = "//div[contains(text(),'Date Applied')]/following-sibling::div")
	WebElement dateApplied;

	// buttons
	@FindBy(xpath = "//span[@id=\"ui-dialog-title-dialog-modal-leave\"]/following-sibling::a")
	WebElement fileALeaveModalCloseButton;
	@FindBy(id = "CancelLeaveApplication")
	WebElement cancelButton;
	@FindBy(id = "SubmitLeaveApplication")
	WebElement submitButton;

	// textbox
	@FindBy(id = "LeaveReason")
	WebElement remarksTextBox;
	@FindBy(id = "ContactNumber")
	WebElement contactNumberTextBox;

	// dropdowns
	@FindBy(id = "reasonDDL")
	WebElement leaveReasonDropdown;
	@FindBy(id = "LeaveType")
	WebElement leaveTypeDropdown;

	// error/required messages
	@FindBy(id = "errorLeaveType")
	WebElement leaveTypeErrorMessage;
	@FindBy(id = "errorReasonDDL")
	WebElement leaveReasonErrorMessage;
	@FindBy(id = "errorReason1")
	WebElement commentErrorMessage;
	@FindBy(id = "errorContact")
	WebElement contactNumberErrorMessage;

	// others (e.g. containers, lists)
	@FindBy(id = "dialog-modal-leave")
	WebElement fileALeaveModalBody;
	@FindBy(xpath = "//select[@id='LeaveType']/option")
	List<WebElement> leaveTypeOptions;
	@FindBy(xpath = "//select[@id='reasonDDL']/option")
	List<WebElement> leaveResonOptions;

	
	
	/* --------------------------------------------CONSTRUCTOR --------------------------------------- */
	public FileALeave_Modal(WebDriver driver) {
		this.driver = driver;
	}
	
	
	// Getters
	
	/* --------------------------------------------PUBLIC METHODS --------------------------------------- */
	
	//GETTERS--------------------------------------------------------------------------------------------
	public WebElement getLeaveTypeDropdown() {
		return leaveTypeDropdown;
	}

	public WebElement getLeaveReasonDropdown() {
		return leaveReasonDropdown;
	}

	public List<WebElement> getLeaveTypeOptions() {
		return leaveTypeOptions;
	}

	public List<WebElement> getLeaveReasonOptions() {
		return leaveResonOptions;
	}

	public String getFromDate() {
		waitForElement(leaveFromField);
		return leaveFromField.getAttribute("value");
	}

	public String getToDate() {
		waitForElement(leaveToField);
		return leaveToField.getAttribute("value");
	}

	public WebElement getCommentsField() {
		return remarksTextBox;
		
	}
	
	public WebElement getContactNumberField() {
		return contactNumberTextBox;
		
	}
	
	public WebElement getLeaveTypeErrorMessage() {
		return leaveTypeErrorMessage;
	}
	
	public WebElement getLeaveReasonErrorMessage() {
		return leaveReasonErrorMessage;
	}
	
	public WebElement getCommentErrorMessage() {
		return commentErrorMessage;
	}
	
	public WebElement getContactNumberErrorMessage() {
		return contactNumberErrorMessage;
	}
	
	//ACTIONS--------------------------------------------------------------------------------------------
	// Actions
	public void clickCloseButton() {

		// close "File a Leave" modal
		waitForElement(fileALeaveModalCloseButton);
		fileALeaveModalCloseButton.click();
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(),
				"Click close button in the 'File a leave' modal");

	}

	public void selectDropDownOption(WebElement dropDown, String leaveType) {

		// declare dropdown as an instance of the Select class
		Select dropDownElement = new Select(dropDown);

		// select leave type
		dropDownElement.selectByVisibleText(leaveType);

		String dropDownName = dropDown.getAttribute("name");
		String methodName = "Select the ff. option: " + leaveType + " from the " + dropDownName + " dropdown";
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), methodName);
	}

	public void clickHalfDayCheckbox() {
		waitForElement(halfDayCheckbox);
		halfDayCheckbox.click();
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Click Half Day checkbox");
	}

	public void enterTextInRemarksTextBox(String remark) {
		waitForElement(remarksTextBox);

		remarksTextBox.sendKeys(remark);

		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(),
				"Enter text in the comments/remarks textbox");
	}

	public void enterContactNumber(String contactNumber) {
		waitForElement(contactNumberTextBox);

		contactNumberTextBox.sendKeys(contactNumber);

		String methodName = "Enter contact number:  " + contactNumber + " in the contact number textbox";
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), methodName);
	}

	public void clickCancelButton() {
		waitForElement(cancelButton);
		cancelButton.click();
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Click the Cancel button");
	}
	
	public void clickSubmitButton() {
		waitForElement(submitButton);
		submitButton.click();
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Click the Submit button");
	}

	// VERIFICATIONS---------------------------------------------------------------------------------------
	// Verifications and assertions
	public void verifyFileALeaveModalIsDisplayed() {

		// verify "File a Leave" modal exists
		waitForElement(fileALeaveModalCloseButton);
		waitForElement(fileALeaveModalBody);
		validateElementIsDisplayed(fileALeaveModalCloseButton);
		validateElementIsDisplayed(fileALeaveModalBody);
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(),
				"Verify 'File a Leave' modal is displayed");

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

	public void verifyDropDownOptions(WebElement dropDownElement, List<WebElement> optionsList,
			List<String> optionsListTestData) {
		waitForElement(dropDownElement);

		// get a list of all leave type options displayed in the app
		List<String> optionsListStrings = getOptionElementsText(optionsList);

		// click dropdown
		dropDownElement.click();

		// verify that the optionsListStrings arraylist contains each string in the
		// optionsListTestData parameter
		for (String optionTestData : optionsListTestData) {

			boolean optionExists = optionsListStrings.contains(optionTestData);
			Assert.assertTrue(optionExists);

			String dropDownName = dropDownElement.getAttribute("name");
			String methodName = "Verify the ff. option is available: " + optionTestData + " in the " + dropDownName
					+ " dropdown";
			reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), methodName);
		}

	}

	public void verifydropDownOptionIsSelected(WebElement dropDown, String option) {
		// declare dropdown as an instance of the Select class
		Select dropDownElement = new Select(dropDown);

		// verify option is selected
		String selectedOption = dropDownElement.getFirstSelectedOption().getText();
		Assert.assertEquals(selectedOption, option);

		String dropDownName = dropDown.getAttribute("name");
		String methodName = "Verify the ff. option is selected: " + selectedOption + "  in the " + dropDownName
				+ " dropdown";
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

	public void verifyHalfDayCheckIsChecked() {
		waitForElement(halfDayCheckbox);
		boolean isChecked = halfDayCheckbox.isSelected();

		Assert.assertTrue(isChecked);
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Verify Half Day checkbox is CHECKED");

	}

	public void verifyHalfDayCheckIsUnchecked() {
		waitForElement(halfDayCheckbox);
		boolean isChecked = halfDayCheckbox.isSelected();

		Assert.assertFalse(isChecked);
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Verify Half Day checkbox is UNCHECKED");

	}

	public void verifyRemarksTextBoxValue(String remarks) {

		waitForElement(remarksTextBox);
		String text = remarksTextBox.getAttribute("value");

		Assert.assertEquals(text, remarks);
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(),
				"Verify value of comments/remarks textarea is equal to text entered");
	}

	public void verifyContactNumberTextBoxValue(String contactNumber) {

		waitForElement(contactNumberTextBox);
		String textBoxValue = contactNumberTextBox.getAttribute("value");

		Assert.assertEquals(textBoxValue, contactNumber);
		String methodName = "Verify value of contact number textbox: " + textBoxValue
				+ " is equal to contact number entered: " + contactNumber;
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), methodName);
	}

	public void verifyFieldIsEmpty(WebElement field) {
		
		//verify field value is equal to ""
		waitForElement(field);
		String fieldValue = field.getAttribute("value");
		System.out.println("Field value is equal to " + fieldValue);
		
		boolean isEmpty = (fieldValue == null || fieldValue.isEmpty());
		Assert.assertTrue(isEmpty);
		
		//reporting
		String fieldName = field.getAttribute("name");
		String methodName = "Verify " + fieldName + " field is empty";
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), methodName);
	}
	
	public void verifyErrorMessageForFieldIsDisplayed (WebElement field, WebElement errorMessage) {

		//verify error message is displayed 
		validateElementIsDisplayed(errorMessage);
	
		String fieldName = field.getAttribute("name");
		String methodName = "Verify error message for " + fieldName + " field is displayed";
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), methodName);
		
	}
	
	public void verifyErrorMessageTextForField (WebElement field, WebElement errorMessageElement, String expectedErrorMessage) {

		//verify error message is equal to the errorMessage parameter
		waitForElement(errorMessageElement);
		String errorMessageElementText = errorMessageElement.getText();
		Assert.assertEquals(errorMessageElementText, expectedErrorMessage);
		
		String methodName = "Verify error message displayed for field " + field.getAttribute("name") + " is '" + expectedErrorMessage + "'";
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), methodName);
		
	}
	
	/* --------------------------------------------PRIVATE METHODS --------------------------------------- */
	private List<String> getOptionElementsText(List<WebElement> optionsList) {

		// returns a list of all leave type options represented as string
		List<String> leavyTypeOptionsText = new ArrayList<String>();

		for (WebElement option : optionsList) {
			waitForElement(option);
			leavyTypeOptionsText.add(option.getText());
		}

		return leavyTypeOptionsText;
	}
}
