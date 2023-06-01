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

public class DailyWorkHours extends UserHelper {
	WebDriver driver;
    
    /*---------Daily Work Hours----------*/
    @FindBy(id="ui-dialog-title-taskLog-modal")
    WebElement dailyWorkHours;
    @FindBy(xpath="/html/body/div[9]/div[1]/a/span")
    WebElement closeDailyWorkHours;
    @FindBy(id="prevDay")
    WebElement prevDayArrow;
    @FindBy(id="nextDay")
    WebElement nextDayArrow;
    @FindBy(xpath="//*[@id=\"summaryHours\"]/tbody/tr[1]/td[2]")
    WebElement currentMonthYear;
    @FindBy(xpath="//*[@id=\"summaryHours\"]/tbody/tr[2]/td[2]")
    WebElement currentDay;
    @FindBy(id="todayTask")
    WebElement tasksWithLogs;
    @FindBy(id="active")
    WebElement activeTasks;
    @FindBy(id="all")
    WebElement allTasks;
    @FindBy(id="general")
    WebElement generalTasks;
    
	public DailyWorkHours(WebDriver driver) {
		this.driver = driver;
	}
    
	public void verifyInputWhizExists() {
		waitForElement(dailyWorkHours);
        boolean popupSuccess = dailyWorkHours!=null;
        if(!popupSuccess) {
    		throw new Error("Pop-up does not appear!");
    	}
        //moveAndHighlightElement(dailyWorkHours);
        reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Verified Enter Hours pop-up exists");
	}
	
	public void closeInputWhizHours() {
		//waitForElement(closeDailyWorkHours);
		//page.setBrowserZoom(80);
	    WebElement e = driver.findElement(By.xpath("/html/body/div[9]/div[1]/a/span"));
	    moveAndHighlightElement(e);
	    e.click();
		//moveAndHighlightElement(closeDailyWorkHours);
		//closeDailyWorkHours.click();
		//actionsClick(By.xpath("/html/body/div[9]/div[1]/a/span"));
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Closed Enter Hours pop-up");
	}
	
	public void verifyProperDay(String monthYear,int dayOfMonth) {
		waitForElement(currentMonthYear);
		waitForElement(currentDay);
		String dwhMonthYear = currentMonthYear.getText();
		String dwhDay = currentDay.getText();
		boolean isSameMonthYear = dwhMonthYear.equals(dwhMonthYear);
		boolean isSameDay = dwhDay.equals(Integer.toString(dayOfMonth));
		if(!isSameMonthYear) {
			throw new Error("Not same month and/or year!");
		}
		if(!isSameDay) {
			throw new Error("Not same day!");
		}
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Verified same day as clicked");
	}
}
