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
	/* --------------------------------------------VARIABLES--------------------------------------- */
	String initialSubmitToastMessage = "Please Wait...";
	
	/* --------------------------------------------LOCATORS --------------------------------------- */
	// fields
	@FindBy(id = "LeaveFrom") WebElement leaveFromField;
	@FindBy(id = "LeaveTo") WebElement leaveToField;
	@FindBy(id = "IsHalfday") WebElement halfDayCheckbox;
	@FindBy(xpath = "//div[contains(text(),'Date Applied')]/following-sibling::div") WebElement dateApplied;
	@FindBy(id = "available") WebElement availableLeaveBalance;

	// buttons
	@FindBy(xpath = "//span[@id=\"ui-dialog-title-dialog-modal-leave\"]/following-sibling::a") WebElement fileALeaveModalCloseButton;
	@FindBy(id = "CancelLeaveApplication") WebElement cancelButton;
	@FindBy(id = "SubmitLeaveApplication") WebElement submitButton;
	@FindBy(id = "OkBtn") WebElement popupOkButton;
	@FindBy(xpath = "//span[@class='ui-button-text']") WebElement warningPopupOkButton;

	// textbox
	@FindBy(id = "LeaveReason") WebElement remarksTextBox;
	@FindBy(id = "ContactNumber") WebElement contactNumberTextBox;

	// dropdowns
	@FindBy(id = "LeaveType") WebElement leaveTypeDropdown;
	@FindBy(id = "reasonDDL") WebElement leaveReasonDropdown;

	//messages(errors, toasts)
	@FindBy(id = "errorLeaveType") WebElement leaveTypeErrorMessage;
	@FindBy(id = "errorReasonDDL") WebElement leaveReasonErrorMessage;
	@FindBy(id = "errorReason1") WebElement commentErrorMessage;
	@FindBy(id = "errorContact") WebElement contactNumberErrorMessage;
	@FindBy(xpath = "//div[@id='dialog-modal']//div[@class='loaderMessage']") WebElement toastMessage;
	@FindBy(xpath = "//div[@id='dialog-modal-confirm']/p") WebElement warningPopupMessage;

	//toasts
	@FindBy(xpath = "//div[@id='dialog-modal']/div[@class='loader']") WebElement toast;
	@FindBy(xpath = "//div[@id='dialog-modal-confirm']") WebElement warningPopUp;
	
	// others (e.g. containers, lists)
	@FindBy(id = "dialog-modal-leave") WebElement fileALeaveModalBody;
	@FindBy(xpath = "//select[@id='LeaveType']/option") List<WebElement> leaveTypeOptions;
	@FindBy(xpath = "//select[@id='reasonDDL']/option[not(@id = '') and contains(@style, 'display: block')]") List<WebElement> leaveReasonOptions;

	
	
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
		return leaveReasonOptions;
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
	
	public String getAvailableLeaveBalance(String leaveType) {
		//Select leaveType
		waitForElement(leaveTypeDropdown);
		selectDropDownOption(leaveTypeDropdown, leaveType);
		
		//get running balance
		waitForElement(availableLeaveBalance);
		String runningBalanceString = availableLeaveBalance.getAttribute("innerText");
		
		return runningBalanceString;
	}
	public String getAvailableLeaveBalance() {
		
		//get running balance
		waitForElement(availableLeaveBalance);
		String runningBalanceString = availableLeaveBalance.getAttribute("innerText");
		
		return runningBalanceString;
	}
	
	public String getRandomLeaveType() {
		int randomIndex = generateRandomNumber(0, leaveTypeOptions.size()-1);
		String randomLeaveType = leaveTypeOptions.get(randomIndex).getText();
		
		return randomLeaveType;
	}
	
	public String getRandomLeaveReason() {
		int randomIndex = generateRandomNumber(0, leaveReasonOptions.size()-1);
		String randomLeaveReason = leaveReasonOptions.get(randomIndex).getText();
		
		return randomLeaveReason;
	}
	
	//ACTIONS--------------------------------------------------------------------------------------------
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
		String methodName = "Select the option '" + leaveType + "' from the " + dropDownName + " dropdown";
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

	public void clickOkButtonInPopup(){
		waitForElement(popupOkButton);
		popupOkButton.click();
		
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Click OK button in the pop-up displayed");
	}
	
	public void clickOkButtonInWarningPopup(){
		waitForElement(warningPopupOkButton);
		warningPopupOkButton.click();
		
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Click OK button in the warning pop-up displayed");
	}
	
	// VERIFICATIONS---------------------------------------------------------------------------------------
	// Verifications and assertions
	public void verifyFileALeaveModalIsDisplayed() {

		// verify "File a Leave" modal exists
		waitForElement(fileALeaveModalCloseButton);
		waitForElement(fileALeaveModalBody);
		validateElementIsDisplayed(fileALeaveModalCloseButton);
		validateElementIsDisplayed(fileALeaveModalBody);
		
		String timeLogDate = leaveFromField.getAttribute("value");
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(),
				"Verify 'File a Leave' modal is displayed for the " + timeLogDate + " timelog");

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

		// verify "File a Leave" modal does not exist
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
		String methodName = "Verify that the option '" + selectedOption + "' is selected in the " + dropDownName
				+ " dropdown";
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), methodName);
	}

	public void isInCorrectDateFormat(String dateString, String format) {

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

		
		String methodName = "Verify the date : " + dateString + " is in the format: " + format;
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
		String methodName = "Verify that the value of comments/remarks textarea is equal to '" + remarks + "'";
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(),methodName);
	}

	public void verifyContactNumberTextBoxValue(String contactNumber) {

		waitForElement(contactNumberTextBox);
		String textBoxValue = contactNumberTextBox.getAttribute("value");

		Assert.assertEquals(textBoxValue, contactNumber);
		String methodName = "Verify that the value of contact number textbox is equal to '" + contactNumber + "'";
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), methodName);
	}

	public void verifyFieldIsEmpty(WebElement field) {
		
		//verify field value is equal to ""
		waitForElement(field);
		String fieldValue = field.getAttribute("value");
		//System.out.println("Field value is equal to " + fieldValue);
		
		boolean isEmpty = (fieldValue == null || fieldValue.isEmpty());
		Assert.assertTrue(isEmpty);
		
		//reporting
		String fieldName = field.getAttribute("name");
		String methodName = "Verify " + fieldName + " field is empty";
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), methodName);
	}
	
	public void verifyErrorMessageForFieldIsNotDisplayed (WebElement field) {

		//get error message of given field parameter
		String fieldID =  field.getAttribute("id");
		String errorMessageXpath = "//*[@id='" + fieldID + "']/following-sibling::*[starts-with(@id,'error')]";
		WebElement errorMessageElement = driver.findElement(By.xpath(errorMessageXpath));
		
		//verify error message is not displayed
		validateElementIsNotDisplayed(errorMessageElement);
		
		
		String fieldName = field.getAttribute("name");
		String methodName = "Verify error message for " + fieldName + " field is NOT displayed";
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), methodName);
		
	}
	
	public void verifyErrorMessageForFieldIsDisplayed (WebElement field) {

		//get error message of given field parameter
		String fieldID =  field.getAttribute("id");
		String errorMessageXpath = "//*[@id='" + fieldID + "']/following-sibling::*[starts-with(@id,'error')]";
		WebElement errorMessageElement = driver.findElement(By.xpath(errorMessageXpath));
		
		//verify error message is displayed and is not blank
		String errorText = errorMessageElement.getAttribute("innerText");
		boolean isNotBlank = !errorText.isEmpty();
		Assert.assertTrue(isNotBlank);
		
		String fieldName = field.getAttribute("name");
		String methodName = "Verify error message for " + fieldName + " field is displayed";
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), methodName);
		
	}
	
	public void verifyErrorMessageTextForField (WebElement field, String expectedErrorMessage) {

		//get error message of given field parameter
		String fieldID =  field.getAttribute("id");
		String errorMessageXpath = "//*[@id='" + fieldID + "']/following-sibling::*[starts-with(@id,'error')]";
		WebElement errorMessageElement = driver.findElement(By.xpath(errorMessageXpath));
		
		//verify error message is equal to the errorMessage parameter
		String errorMessageElementText = errorMessageElement.getText();
		Assert.assertEquals(errorMessageElementText, expectedErrorMessage);
		
		String methodName = "Verify error message displayed for field " + field.getAttribute("name") + " is '" + expectedErrorMessage + "'";
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), methodName);
		
	}
	
	public void verifyToastIsDisplayed() {
		waitForElement(toast);
		validateElementIsDisplayed(toast);
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Verify submit toast/popup is displayed");
	}
	
	public void verifyWarningPopupIsDisplayed() {
		waitForElement(warningPopUp);
		validateElementIsDisplayed(warningPopUp);
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Verify Warning popup is displayed");
	}
	
	public void verifyWarningPopupIsNotDisplayed() {
		validateElementIsNotDisplayed(warningPopUp);
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Verify Warning popup is NOT displayed");
	}
	
	public void verifyToastIsNotDisplayed() {
		waitElementToBeInvinsible(toast);
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Verify submit toast/popup is not displayed");
	}
	
	public void waitForFileALeaveModalToDisappear() {
		waitElementToBeInvinsible(fileALeaveModalBody);
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Verify 'File A Leave' modal has disappeared");
	}
	
	public void verifyToastMessage(String expectedMessage) {
		waitForElementTextToChange(toastMessage, initialSubmitToastMessage);
		String toastMessageString = toastMessage.getText();
		Assert.assertEquals(toastMessageString, expectedMessage);
		
		String methodName = "Verify toast/popup message displayed is '" + expectedMessage + "'";
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), methodName);
	}
	
	public void verifyWarningPopupMessage(String expectedMessage) {
		String warningMessageString = warningPopupMessage.getText();
		Assert.assertEquals(warningMessageString, expectedMessage);
		
		String methodName = "Verify warning message displayed is '" + expectedMessage + "'";
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), methodName);
	}
	
	
	
	
	
	
	
	//-------------------------------------------------------------------
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
