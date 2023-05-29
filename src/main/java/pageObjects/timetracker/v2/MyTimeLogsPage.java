package pageObjects.timetracker.v2;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import utilities.UserHelper;


public class MyTimeLogsPage extends UserHelper{
	WebDriver driver;
	
   
  //locators	
	@FindBy(id="TimelogsTable")
	    WebElement timeLogsTable;
	@FindBy(xpath="//tbody/tr[contains (@id, '0')]")
		List<WebElement> timeLogsTableRows;
	@FindBy(xpath="//td[contains(text(),'Reg')]/parent::tr//a[@class='fileLeaveLink']")
		List<WebElement> fileALeaveLinks;
	

    

    
    public MyTimeLogsPage(WebDriver driver) {
		this.driver = driver;
	}
    
    public void verifyFileALeaveButtonsExist() {

    	//verify file a leave link exists
    	for (WebElement fileALeaveLink : fileALeaveLinks) {
    		validateElementIsDisplayed(fileALeaveLink);
    		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Verify 'File a Leave' button exists");
    	}
    	
    }
    
    
    public void clickRandomFileALeaveButton() {

    	//generate random index
    	int randomIndex = generateRandomNumber(0, fileALeaveLinks.size() - 1);
		
    	//click "File A Leave" Link
		WebElement fileLeaveLink = fileALeaveLinks.get(randomIndex);
		fileLeaveLink.click();
		
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Click random 'File A Leave' link");
    }

    public void clickFileALeaveButton(int index) {
    	//click "File A Leave" Link
    	WebElement fileLeaveLink = fileALeaveLinks.get(index);
		fileLeaveLink.click();
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Click random 'File A Leave' link");
    }

    
	private int generateRandomNumber(int min, int max) {
		// TODO Auto-generated method stub
		Random random = new Random();
		return random.nextInt(max - min + 1) + min;
	}

	
	private List<Integer> getRowsWithRegularShift() {
		//gets the indices of rows with "Reg" shift and stores it in a list
		String shiftType = "Reg";
		List<Integer> regularShiftRowIndices = new ArrayList<Integer>();
		
		
		waitForElement(timeLogsTable);
		for (int i = 0 ; i < timeLogsTableRows.size() ; i++) {
    		String rowText = timeLogsTableRows.get(i).getText();
    		if (rowText.contains(shiftType)) {
    			regularShiftRowIndices.add(i);
    		}
    	}
		System.out.println(regularShiftRowIndices);
		return regularShiftRowIndices;
	}
	
	
}
