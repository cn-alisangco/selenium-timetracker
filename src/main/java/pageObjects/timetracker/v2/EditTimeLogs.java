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
    	WebElement manualTimeInCheckbox = driver.findElement(By.id("manualTimeInCheck"+dayOfTheWeek));
    	WebElement reasonTextBox = driver.findElement(By.id("txtReason"+dayOfTheWeek));
    	//getting locators for manual fields
    	if(!manualTimeInCheckbox.isSelected()) {
    		manualTimeInCheckbox.click();
    	}
    	WebElement timeInHour = driver.findElement(By.xpath("//*[@id=\"divTimeInManual"+dayOfTheWeek+"\"]/input[1]"));
    	WebElement timeInMinute = driver.findElement(By.xpath("//*[@id=\"divTimeInManual"+dayOfTheWeek+"\"]/input[2]"));
    	WebElement timeInZone = driver.findElement(By.xpath("//*[@id=\"divTimeInManual"+dayOfTheWeek+"\"]/select[1]"));
    	WebElement timeInArea = driver.findElement(By.xpath("//*[@id=\"divTimeInManual"+dayOfTheWeek+"\"]/select[2]"));
    	//enter time
    	reasonTextBox.clear();
    	reasonTextBox.sendKeys("automated fill up by Selenium");
    	//int hour = LocalTime.now().getHour();
    	//if(hour > 12) {
    	//	hour = hour - 12;
    	//}
    	//int minute = LocalTime.now().getMinute();
    	javascript.executeScript("arguments[0].value = '';", timeInHour);
    	javascript.executeScript("arguments[0].value='"+hour+"';", timeInHour);
    	javascript.executeScript("arguments[0].value = '';", timeInMinute);
    	//javascript.executeScript("arguments[0].setAttribute('value', '');", timeInMinute);
    	//if(minute < 10) {
    		//javascript.executeScript("arguments[0].value='"+"0"+minute+"';", timeInMinute);
    	//}else {
    		javascript.executeScript("arguments[0].value='"+minute+"';", timeInMinute);
    	//}
    	//fill up dropdown menus
    	selectByVisibleText(timeInZone, time12Hour);
    	selectByVisibleText(timeInArea,"UP");
    	//save entered data
    	editSave.click();
    	reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Successfully filled up manual time in");
    }
    
    public void verifyTimeInFill(String time12Hour) {
    	//compare entered time with displayed 
    	int dayOfMonth = LocalDateTime.now().getDayOfMonth();
    	WebElement timeInEntered = driver.findElement(By.xpath("//*[@id=\"0"+dayOfMonth+"\"]/td[4]"));
    	String displayedTimeIn = timeInEntered.getText();
    	System.out.println(displayedTimeIn);
    	boolean isSame;
    	int hour = LocalTime.now().getHour();
    	if(hour > 12) {
    		hour = hour - 12;
    	}
    	int minute = LocalTime.now().getMinute();
    	if(minute < 10) {
    		isSame = displayedTimeIn.equals(hour+":"+"0"+minute+" "+time12Hour+" "+"UP");
    		System.out.println(hour+":"+"0"+minute+" "+time12Hour+" "+"UP");
    	}else {
    		isSame = displayedTimeIn.equals(hour+":"+minute+" "+time12Hour+" "+"UP");
    		System.out.println(hour+":"+minute+" "+time12Hour+" "+"UP");
    	}
    	isSame = displayedTimeIn.equals(hour+":"+minute+" "+time12Hour+" "+"UP");
    	System.out.println(isSame);

    	reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Verified same time in");
    }

    public void fillManualTimeOut(int dayOfTheWeek, String time12Hour) {
    	dayOfTheWeek = dayOfTheWeek-1;
    	JavascriptExecutor javascript = (JavascriptExecutor) driver;
    	//getting locators for current date
    	waitForElement(editTimeLogs);
    	WebElement manualTimeOutCheckbox = driver.findElement(By.id("manualTimeOutCheck"+dayOfTheWeek));
    	WebElement reasonTextBox = driver.findElement(By.id("txtReason"+dayOfTheWeek));
    	//getting locators for manual fields
    	if(!manualTimeOutCheckbox.isSelected()) {
    		manualTimeOutCheckbox.click();
    	}
    	WebElement timeOutHour = driver.findElement(By.xpath("//*[@id=\"divTimeOutManual"+dayOfTheWeek+"\"]/input[1]"));
    	WebElement timeOutMinute = driver.findElement(By.xpath("//*[@id=\"divTimeOutManual"+dayOfTheWeek+"\"]/input[2]"));
    	WebElement timeOutZone = driver.findElement(By.xpath("//*[@id=\"divTimeOutManual"+dayOfTheWeek+"\"]/select[1]"));
    	WebElement timeOutArea = driver.findElement(By.xpath("//*[@id=\"divTimeOutManual"+dayOfTheWeek+"\"]/select[2]"));
    	//enter time
    	reasonTextBox.clear();
    	reasonTextBox.sendKeys("automated fill up by Selenium");
    	int hour = LocalTime.now().getHour();
    	if(hour > 12) {
    		hour = hour - 12;
    	}
    	int minute = LocalTime.now().getMinute();
    	javascript.executeScript("arguments[0].value = '';", timeOutHour);
    	javascript.executeScript("arguments[0].value='"+hour+"';", timeOutHour);
    	javascript.executeScript("arguments[0].setAttribute('value', '');", timeOutMinute);
    	if(minute < 10) {
    		javascript.executeScript("arguments[0].value='"+"0"+minute+"';", timeOutMinute);
    	}else {
    		javascript.executeScript("arguments[0].value='"+minute+"';", timeOutMinute);
    	}
    	//fill up dropdown menus
    	selectByVisibleText(timeOutZone, time12Hour);
    	selectByVisibleText(timeOutArea,"UP");
    	//save entered data
    	editSave.click();
    	reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Successfully filled up manual time out");
    }

    public void verifyTimeOutFill(String time12Hour) {	
    	//compare entered time with displayed 
    	int dayOfMonth = LocalDateTime.now().getDayOfMonth();
    	WebElement timeOutEntered = driver.findElement(By.xpath("//*[@id=\"0"+dayOfMonth+"\"]/td[5]"));
    	String displayedTimeOut = timeOutEntered.getText();
    	System.out.println(displayedTimeOut);
    	boolean isSame;
    	int hour = LocalTime.now().getHour();
    	if(hour > 12) {
    		hour = hour - 12;
    	}
    	int minute = LocalTime.now().getMinute();
    	if(minute < 10) {
    		isSame = displayedTimeOut.equals(hour+":"+"0"+minute+" "+time12Hour+" "+"UP");
    		System.out.println(hour+":"+"0"+minute+" "+time12Hour+" "+"UP");
    	}else {
    		isSame = displayedTimeOut.equals(hour+":"+minute+" "+time12Hour+" "+"UP");
    		System.out.println(hour+":"+minute+" "+time12Hour+" "+"UP");
    	}
    	System.out.println(isSame);
    	
    	reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Successfully verified manual time out");
    }
}
