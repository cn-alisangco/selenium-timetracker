package pageObjects.timetracker.v2;

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
	
    public void fillManualTimeIn(int dayOfTheWeek, String hour, String minute, String time12Hour) {
    	dayOfTheWeek = dayOfTheWeek-1;
    	JavascriptExecutor javascript = (JavascriptExecutor) driver;
    	//getting locators for current date
    	waitForElement(editTimeLogs);
    	//getting locators for manual fields
    	WebElement manualTimeInCheckbox = driver.findElement(By.id("manualTimeInCheck"+dayOfTheWeek));
    	WebElement reasonTextBox = driver.findElement(By.id("txtReason"+dayOfTheWeek));
    	if(!manualTimeInCheckbox.isSelected()) { //if manual override not yet ticked, tick to release manual time fields
    		manualTimeInCheckbox.click();
    	}
    	WebElement timeInHour = driver.findElement(By.xpath("//*[@id=\"divTimeInManual"+dayOfTheWeek+"\"]/input[1]"));
    	WebElement timeInMinute = driver.findElement(By.xpath("//*[@id=\"divTimeInManual"+dayOfTheWeek+"\"]/input[2]"));
    	WebElement timeInZone = driver.findElement(By.xpath("//*[@id=\"divTimeInManual"+dayOfTheWeek+"\"]/select[1]"));
    	WebElement timeInArea = driver.findElement(By.xpath("//*[@id=\"divTimeInManual"+dayOfTheWeek+"\"]/select[2]"));
    	//enter reason text field
    	reasonTextBox.clear();
    	reasonTextBox.sendKeys("automated fill up by Selenium");
    	//enter time in hours, using JSE since field does not work properly with regular WebElement functions
    	javascript.executeScript("arguments[0].value = '';", timeInHour);
    	javascript.executeScript("arguments[0].value='"+hour+"';", timeInHour);
    	javascript.executeScript("arguments[0].value = '';", timeInMinute);
    	javascript.executeScript("arguments[0].value='"+minute+"';", timeInMinute);
    	//fill up dropdown menus
    	selectByVisibleText(timeInZone, time12Hour);
    	selectByVisibleText(timeInArea,"UP");
    	//save entered data
    	editSave.click();
    	reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Successfully filled up manual time in");
    }
    
    public void verifyTimeInFill(int dayOfMonth, String hour, String minute, String time12Hour) {
    	//compare entered time with displayed 
    	WebElement timeInEntered = driver.findElement(By.xpath("//*[@id=\"0"+dayOfMonth+"\"]/td[4]"));
    	String displayedTimeIn = timeInEntered.getText();
    	System.out.println(displayedTimeIn);
    	boolean isSame;
    	
    	isSame = displayedTimeIn.equals(hour+":"+minute+" "+time12Hour+" "+"UP");
    	//System.out.println(hour+":"+minute+" "+time12Hour+" "+"UP");
    	if(!isSame) {
    		throw new Error("Entered time not the same!");
    	}
    	reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Verified same time in");
    }

    public void fillManualTimeOut(int dayOfTheWeek, String hour, String minute, String time12Hour) {
    	dayOfTheWeek = dayOfTheWeek-1;
    	JavascriptExecutor javascript = (JavascriptExecutor) driver;
    	//getting locators for current date
    	waitForElement(editTimeLogs);
    	WebElement manualTimeOutCheckbox = driver.findElement(By.id("manualTimeOutCheck"+dayOfTheWeek));
    	WebElement reasonTextBox = driver.findElement(By.id("txtReason"+dayOfTheWeek));
    	//getting locators for manual fields
    	if(!manualTimeOutCheckbox.isSelected()) {	//if manual override not yet ticked, tick to release manual time fields
    		manualTimeOutCheckbox.click();
    	}
    	WebElement timeOutHour = driver.findElement(By.xpath("//*[@id=\"divTimeOutManual"+dayOfTheWeek+"\"]/input[1]"));
    	WebElement timeOutMinute = driver.findElement(By.xpath("//*[@id=\"divTimeOutManual"+dayOfTheWeek+"\"]/input[2]"));
    	WebElement timeOutZone = driver.findElement(By.xpath("//*[@id=\"divTimeOutManual"+dayOfTheWeek+"\"]/select[1]"));
    	WebElement timeOutArea = driver.findElement(By.xpath("//*[@id=\"divTimeOutManual"+dayOfTheWeek+"\"]/select[2]"));
    	//enter time
    	reasonTextBox.clear();
    	reasonTextBox.sendKeys("automated fill up by Selenium");
    	javascript.executeScript("arguments[0].value = '';", timeOutHour);
    	javascript.executeScript("arguments[0].value='"+hour+"';", timeOutHour);
    	javascript.executeScript("arguments[0].setAttribute('value', '');", timeOutMinute);
    	javascript.executeScript("arguments[0].value='"+minute+"';", timeOutMinute);
    	//fill up dropdown menus
    	selectByVisibleText(timeOutZone, time12Hour);
    	selectByVisibleText(timeOutArea,"UP");
    	//save entered data
    	editSave.click();
    	reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Successfully filled up manual time out");
    }

    public void verifyTimeOutFill(int dayOfMonth, String hour, String minute, String time12Hour) {	
    	//compare entered time with displayed 
    	WebElement timeOutEntered = driver.findElement(By.xpath("//*[@id=\"0"+dayOfMonth+"\"]/td[5]"));
    	String displayedTimeOut = timeOutEntered.getText();
    	System.out.println(displayedTimeOut);
    	boolean isSame;
    		
    	isSame = displayedTimeOut.equals(hour+":"+minute+" "+time12Hour+" "+"UP");
    	//System.out.println(hour+":"+minute+" "+time12Hour+" "+"UP");
    	if(!isSame) {
    		throw new Error("Entered time not the same!");
    	}
    	reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Successfully verified manual time out");
    }
}
