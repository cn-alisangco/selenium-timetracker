package pageObjects.timetracker.v2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import utilities.UserHelper;

public class MyTimeLogsPage extends UserHelper {
	
	WebDriver driver;

	/* --------------------------------------------LOCATORS --------------------------------------- */
	//tables
	@FindBy(id = "TimelogsTable")
	WebElement timeLogsTable;
	@FindBy(xpath = "//tbody/tr[contains (@id, '0')]")
	List<WebElement> timeLogsTableRows;

	//links
	@FindBy(xpath = "//td[contains(text(),'Reg')]/parent::tr//a[@class='fileLeaveLink' and not(@style)]")
	List<WebElement> fileALeaveLinks;
	@FindBy(xpath = "//td[contains(text(),'Reg')]/following-sibling::td[position()=2 and not(contains(text(),'PM'))]/parent::tr/td[contains(@class, 'selectDate')]")
	List<WebElement> fileALeaveLinkDates;

	/* --------------------------------------------CONSTRUCTOR --------------------------------------- */
	
	public MyTimeLogsPage(WebDriver driver) {
		this.driver = driver;
	}

	/* --------------------------------------------PUBLIC METHODS --------------------------------------- */
	
	// GETTERS--------------------------------------------------------------------------------------------

	public List<WebElement> getAllFileALeaveButtons() {
		return fileALeaveLinks;
	}

	public List<WebElement> getFileALeaveLinkDates(){
		return fileALeaveLinkDates;
	}
	
	
	public List<String> getFileALeaveLinkDateStrings(List<WebElement> dateElements, String parseFormat) {

		List<String> parsedDateStrings = new ArrayList<String>();
		
		

		for (WebElement dateElement : dateElements) {

			String dateString = dateElement.getText();

			String parsedDateString = null;

			try {
				Date date = new SimpleDateFormat(parseFormat).parse(dateString);
				SimpleDateFormat dateFormat = new SimpleDateFormat(parseFormat);

				parsedDateString = dateFormat.format(date);

				parsedDateStrings.add(parsedDateString);

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Unable to parse date to the format: " + parseFormat);
			}

		}
		System.out.println("Dates:" + parsedDateStrings);
		return parsedDateStrings;

	}
	
	public List<String> getFileALeaveLinkDateStrings(String parseFormat) {

		List<WebElement> dateElements = this.getFileALeaveLinkDates();
		List<String> parsedDateStrings = new ArrayList<String>();
		
		for (WebElement dateElement : dateElements) {

			String dateString = dateElement.getText();

			String parsedDateString = null;

			try {
				Date date = new SimpleDateFormat(parseFormat).parse(dateString);
				SimpleDateFormat dateFormat = new SimpleDateFormat(parseFormat);

				parsedDateString = dateFormat.format(date);

				parsedDateStrings.add(parsedDateString);

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Unable to parse date to the format: " + parseFormat);
			}

		}
		System.out.println("Dates:" + parsedDateStrings);
		return parsedDateStrings;

	}

	public WebElement getFileALeaveLinkForDate(String date) {
		String xpath = "//a[text()='" + date + "']/parent::td/following-sibling::td//a[@class='fileLeaveLink']";
		WebElement fileALeaveLink = driver.findElement(By.xpath(xpath));

		return fileALeaveLink;
	}

	// ACTIONS--------------------------------------------------------------------------------------------

	public void clickRandomFileALeaveButton() {

		// generate random index
		int randomIndex = generateRandomNumber(0, fileALeaveLinks.size() - 1);

		// click "File A Leave" Link
		WebElement fileLeaveLink = fileALeaveLinks.get(randomIndex);
		fileLeaveLink.click();

		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Click random 'File A Leave' link");
	}

	public void clickFileALeaveButton(int index) {
		// click "File A Leave" Link
		waitForElements(fileALeaveLinks);
		WebElement fileLeaveLink = fileALeaveLinks.get(index);
		moveAndHighlightElement(fileLeaveLink);

		fileLeaveLink.click();
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(),
				"Click 'File A Leave' link with the given index");
	}

	public void clickFileALeaveButton(String timeLogDate) {
		waitForElements(fileALeaveLinks);

		String elementXpath = "//a[text()='" + timeLogDate + "']/ancestor::tr//a[@class='fileLeaveLink']";
		WebElement fileLeaveLink = driver.findElement(By.xpath(elementXpath));
		moveAndHighlightElement(fileLeaveLink);

		fileLeaveLink.click();

		String methodName = "Click 'File A Leave' link for timelog date: '" + timeLogDate + "'";
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), methodName);
	}

	public void refreshPage() {
		driver.navigate().refresh();
	}

	// VERIFICATIONS--------------------------------------------------------------------------------------------

	public void verifyFileALeaveLinkForDateIsDisplayed(String timeLogDate) {

		String elementXpath = "//a[text()='" + timeLogDate + "']/ancestor::tr//a[@class='fileLeaveLink']";
		WebElement fileLeaveLink = driver.findElement(By.xpath(elementXpath));
		validateElementIsDisplayed(fileLeaveLink);

		String methodName = "Verify 'File a Leave'button for timelog date: " + timeLogDate + " is displayed";
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), methodName);
	}

	public void verifyFileALeaveButtonsExist() {

		// verify file a leave link exists
		for (WebElement fileALeaveLink : fileALeaveLinks) {
			waitForElements(fileALeaveLinks);
			validateElementIsDisplayed(fileALeaveLink);
			reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(),
					"Verify 'File a Leave' button exists");
		}

	}

	public void verifySubmittedLeaveIsInRemarksColumn(String timeLogDate, String leaveType) {
		String xpath = "//a[contains(text(),'" + timeLogDate
				+ "')]/parent::td/following-sibling::td/p[(contains(text(), '" + leaveType + "'))]";
		WebElement element = driver.findElement(By.xpath(xpath));
		waitForElement(element);

		//System.out.println(element.getText());
		String expectedString = "Submitted " + leaveType;

		boolean expectedStringIsDisplayed = element.getText().contains(expectedString);
		Assert.assertTrue(expectedStringIsDisplayed);

		String methodName = "Verify submitted leave '" + leaveType
				+ "' is indicated in the remarks column of the corresponding timelog date '" + timeLogDate + "'";
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), methodName);
	}



}
