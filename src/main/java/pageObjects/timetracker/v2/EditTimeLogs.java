package pageObjects.timetracker.v2;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import utilities.UserHelper;

public class EditTimeLogs extends UserHelper {
	WebDriver driver;
    
    /*---------Edit Timelogs----------*/
    @FindBy(id="ui-dialog-title-popupEditTimeLogs")
    WebElement editTimeLogs;
    @FindBy(xpath="/html/body/div[6]/div[1]/a")
    WebElement closeEditTimeLogs;
    @FindBy(id="editTimeSheetCancel")
    WebElement editCancel;
    @FindBy(id="editTimeSheetSave")
    WebElement editSave;
    
	public EditTimeLogs(WebDriver driver) {
		this.driver = driver;
	}
	
	public void enterReasonOverride(int dayOfTheWeek, String reason) {
		WebElement reasonTextBox = driver.findElement(By.id("txtReason"+dayOfTheWeek));
		reasonTextBox.clear();
    	reasonTextBox.sendKeys(reason);
    	reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "entered Reason Override");
	}
	
	public void saveLogs() {
    	editSave.click();
    	reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Clicked Save");
	}
	
	public boolean cancelLogs() {
		editCancel.click();
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Clicked Cancel");
		return true;
	}
	
	public void tickManual(WebElement checkbox) {
		if(!checkbox.isSelected()) { //if manual override not yet ticked, tick to release manual time fields
    		checkbox.click();
    	}
	}
	
    public void fillManualTimeIn(int dayOfTheWeek, String hour, String minute, String time12Hour) {
    	JavascriptExecutor javascript = (JavascriptExecutor) driver;
    	//getting locators for current date
    	waitForElement(editTimeLogs);
    	WebElement manualTimeInCheckbox = driver.findElement(By.id("manualTimeInCheck"+dayOfTheWeek));
    	tickManual(manualTimeInCheckbox);
    	//getting locators for manual fields
    	//fill up dropdown menus
    	WebElement timeInZone = driver.findElement(By.xpath("//*[@id=\"divTimeInManual"+dayOfTheWeek+"\"]/select[1]"));
    	WebElement timeInArea = driver.findElement(By.xpath("//*[@id=\"divTimeInManual"+dayOfTheWeek+"\"]/select[2]"));
    	selectByVisibleText(timeInZone, time12Hour);
    	selectByVisibleText(timeInArea,"UP");   	
    	WebElement timeInHour = driver.findElement(By.xpath("//*[@id=\"divTimeInManual"+dayOfTheWeek+"\"]/input[1]"));
    	WebElement timeInMinute = driver.findElement(By.xpath("//*[@id=\"divTimeInManual"+dayOfTheWeek+"\"]/input[2]"));
    	//enter time in hours, using JSE since field does not work properly with regular WebElement functions
    	timeInHour.clear();
    	javascript.executeScript("arguments[0].value='"+hour+"';", timeInHour);
    	timeInMinute.clear();
    	javascript.executeScript("arguments[0].value='"+minute+"';", timeInMinute);
    	
    	reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Successfully filled up manual time in");
    }
    
    public void fillManualTimeOut(int dayOfTheWeek, String hour, String minute, String time12Hour) {
    	JavascriptExecutor javascript = (JavascriptExecutor) driver;
    	//getting locators for current date
    	waitForElement(editTimeLogs);
    	WebElement manualTimeOutCheckbox = driver.findElement(By.id("manualTimeOutCheck"+dayOfTheWeek));
    	tickManual(manualTimeOutCheckbox);
    	//getting locators for manual fields
    	WebElement timeOutZone = driver.findElement(By.xpath("//*[@id=\"divTimeOutManual"+dayOfTheWeek+"\"]/select[1]"));
    	WebElement timeOutArea = driver.findElement(By.xpath("//*[@id=\"divTimeOutManual"+dayOfTheWeek+"\"]/select[2]"));
    	//fill up dropdown menus
    	selectByVisibleText(timeOutZone, time12Hour);
    	selectByVisibleText(timeOutArea,"UP");
    	WebElement timeOutHour = driver.findElement(By.xpath("//*[@id=\"divTimeOutManual"+dayOfTheWeek+"\"]/input[1]"));
      	WebElement timeOutMinute = driver.findElement(By.xpath("//*[@id=\"divTimeOutManual"+dayOfTheWeek+"\"]/input[2]"));
    	//enter time
    	timeOutHour.clear();
    	javascript.executeScript("arguments[0].value='"+hour+"';", timeOutHour);
    	timeOutMinute.clear();
    	javascript.executeScript("arguments[0].value='"+minute+"';", timeOutMinute);
    	
    	reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Successfully filled up manual time out");
    }
    
    public void verifyTimeInFill(int dayOfMonth, String hour, String minute, String time12Hour, boolean isCanceled) {
    	//compare entered time with displayed 
    	WebElement timeInEntered = driver.findElement(By.xpath("//*[@id=\"0"+dayOfMonth+"\"]/td[4]"));
    	moveAndHighlightElement(timeInEntered);
    	String displayedTimeIn = timeInEntered.getText();
    	boolean isSame = displayedTimeIn.equals(hour+":"+minute+" "+time12Hour+" "+"UP");
    	if(isCanceled&&!isSame) { //if edit was canceled and value is not same
    		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Successfully verified canceled manual time out");
    	}else if(!isCanceled&&isSame) { //not canceled, same value
    		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Verified same time in");
    	}else if(isCanceled&&isSame) { //canceled, but same value 
    		throw new Error("Time was changed!");
    	}else { //not canceled and not the same as input
    		throw new Error("Entered time not the same!");
    	}
    }

    public void verifyTimeOutFill(int dayOfMonth, String hour, String minute, String time12Hour, boolean isCanceled) {	
    	//compare entered time with displayed 
    	WebElement timeOutEntered = driver.findElement(By.xpath("//*[@id=\"0"+dayOfMonth+"\"]/td[5]"));
    	moveAndHighlightElement(timeOutEntered);
    	String displayedTimeOut = timeOutEntered.getText();
    	System.out.println(displayedTimeOut);
    	boolean isSame = displayedTimeOut.equals(hour+":"+minute+" "+time12Hour+" "+"UP");	
    	if(isCanceled&&!isSame) { //if edit was canceled and value is not same
    		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Successfully verified canceled manual time out");
    	}else if(!isCanceled&&isSame) { //not canceled, same value
    		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Verified same time in");
    	}else if(isCanceled&&isSame) { //canceled, but same value 
    		throw new Error("Time was changed!");
    	}else { //not canceled and not the same as input
    		throw new Error("Entered time not the same!");
    	}
    }
    
    public void verifyTimeInError(int dayOfWeek) {
    	WebElement timeInError = driver.findElement(By.xpath("//*[@id=\"timeInValidation"+dayOfWeek+"\"]"));
    	waitElementToLoad(timeInError);
		moveAndHighlightElement(timeInError);
    	String errorMessage = timeInError.getText();
    	boolean isLaterOrEqual = errorMessage.equals("Time-In cannot be later than or equal to Time-Out");
    	System.out.println(errorMessage);
    	if(!isLaterOrEqual) {
    		throw new Error("Different error message!");
    	}
    	reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Successfully verified correct TimeIn error message");
    }
    
    public void verifyTimeOutError(int dayOfWeek) {
    	WebElement timeOutError = driver.findElement(By.xpath("//*[@id=\"timeOutValidation"+dayOfWeek+"\"]"));
    	waitElementToLoad(timeOutError);
		moveAndHighlightElement(timeOutError);
    	String errorMessage = timeOutError.getText();
    	boolean isEarlierOrEqual = errorMessage.equals("Time-Out cannot be earlier than or equal to Time-In");
    	if(!isEarlierOrEqual) {
    		throw new Error("Different error message!");
    	}
    	reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Successfully verified correct TimeOut error message");
    }

    public void verifyReasonError(int dayOfWeek) {
    	dayOfWeek = dayOfWeek + 2;
    	WebElement reasonError = driver.findElement(By.xpath("//*[@id=\"tblManualEditTimeLogs\"]/tbody/tr["+dayOfWeek+"]/td[6]/div/label"));
    	waitElementToLoad(reasonError);
		moveAndHighlightElement(reasonError);
    	String errorMessage = reasonError.getText();
    	boolean noOverrideReason = errorMessage.equals("Please add reason for manual override.");
    	if(!noOverrideReason) {
    		throw new Error("Different error message!");
    	}
    	reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Successfully verified correct Reason error message");
    }
    
    public static int setTimeLogIndex() {
    	LocalDate currentDate = LocalDate.now();
        // Get the last day of the current week
        LocalDate lastDayOfWeek = currentDate.with(DayOfWeek.SATURDAY);
	    // Get the current day for the month, current day of the week in number format
	    int lastDay = lastDayOfWeek.getDayOfMonth();
        int dayOfWeek = currentDate.getDayOfWeek().getValue();
        int dayOfMonth = LocalDateTime.now().getDayOfMonth();
        //if start of the month, offset property to get proper index in timetracker
        if(dayOfMonth<7&&lastDay<7) {
        	//weekIndex is equal to <dayOfTheWeek> minus offset
        	dayOfWeek = dayOfWeek-7+lastDay;
        }
        //if not start of the month, maintain current index
        return dayOfWeek;
    }
    
}
