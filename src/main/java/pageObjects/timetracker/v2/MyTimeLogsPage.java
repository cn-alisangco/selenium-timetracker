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

import utilities.UserHelper;

public class MyTimeLogsPage extends UserHelper {
	WebDriver driver;

	// variables
	String dateFormat = "MM/dd/yyyy";
	
	
	// locators
	@FindBy(id = "TimelogsTable")
	WebElement timeLogsTable;
	@FindBy(xpath = "//tbody/tr[contains (@id, '0')]")
	List<WebElement> timeLogsTableRows;
	//@FindBy(xpath = "//td[contains(text(),'Reg')]/parent::tr//a[@class='fileLeaveLink']")
	@FindBy(xpath = "//td[contains(text(),'Reg')]/parent::tr//a[@class='fileLeaveLink' and not(@style)]")
	List<WebElement> fileALeaveLinks;
	@FindBy(xpath = "//td[contains(text(),'Reg')]/following-sibling::td[position()=2 and not(contains(text(),'PM'))]/parent::tr/td[contains(@class, 'selectDate')]")
	List<WebElement> regularShiftDates;

	public MyTimeLogsPage(WebDriver driver) {
		this.driver = driver;
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

		fileLeaveLink.click();
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(),
				"Click 'File A Leave' link with the given index");
	}

	public List<WebElement> getAllFileALeaveButtons() {
		return fileALeaveLinks;
	}

	public List<String> getRegularShiftDates(String parseFormat) {
		
		List<String> parsedDateStrings = new ArrayList<String>();
		
		for (WebElement regularShiftDate : regularShiftDates) {
			
			String dateString = regularShiftDate.getText();
			
			String parsedDateString = null;
			
			try {
				Date date = new SimpleDateFormat("MM/dd/yyyy").parse(dateString);
				SimpleDateFormat dateFormat = new SimpleDateFormat(parseFormat);

				parsedDateString = dateFormat.format(date);
				
				parsedDateStrings.add(parsedDateString);
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Unable to parse date to the format: " + parseFormat);
			}
			
		}
		  
    	return parsedDateStrings;
    	
    }



	// private methods----------------------------------------------------------------------------


	private List<Integer> getRowsWithRegularShift() {
		// gets the indices of rows with "Reg" shift and stores it in a list
		String shiftType = "Reg";
		List<Integer> regularShiftRowIndices = new ArrayList<Integer>();
		
		

		waitForElement(timeLogsTable);
		for (int i = 0; i < timeLogsTableRows.size(); i++) {
			String rowText = timeLogsTableRows.get(i).getText();
			
			//add the Row that contains "Reg" and does NOT contain "Time Out" in its innerText and add it to the list
			if (rowText.contains(shiftType) && !(rowText.contains("Time Out"))) {
				regularShiftRowIndices.add(i);
			}
		}
		System.out.println(regularShiftRowIndices);
		return regularShiftRowIndices;
	}

	
}
