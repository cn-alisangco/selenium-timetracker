package pageObjects.timetracker.v2;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import utilities.UserHelper;

public class HomePage extends UserHelper {
	WebDriver driver;
	/*----------Header----------*/
    @FindBy(id="user")
    WebElement loggedUser;
    @FindBy(className="helpImage")
    WebElement helpImage;
    @FindBy(className="logoutImage")
    WebElement logout;
    
    /*----------Toolbar----------*/
    //Time Logs
    @FindBy(xpath="//*[@id=\"topapps\"]/li[1]/a")
    WebElement timeLogsTab;
    @FindBy(linkText="My Time Logs")
    WebElement myTimeLogs;
    //TimeSheet
    @FindBy(xpath="//*[@id=\"topapps\"]/li[2]/a")
    WebElement timesheetTab;
    @FindBy(linkText="My Timesheets")
    WebElement myTimesheets;
    @FindBy(linkText="Send Email to Approve")
    WebElement sendEmail;
    //Shifts
    @FindBy(xpath="//*[@id=\"topapps\"]/li[3]/a")
    WebElement shiftsTab;
    @FindBy(linkText="Shift Types")
    WebElement shiftTypes;
    @FindBy(linkText="Project Shifting Assignments")
    WebElement projectShifting;
    //Leaves
    @FindBy(xpath="//*[@id=\"topapps\"]/li[4]/a")
    WebElement leavesTab;
    @FindBy(linkText="Balances")
    WebElement leaveBalance;
    @FindBy(linkText="Applications")
    WebElement leaveApplication;
    @FindBy(linkText="Approvals")
    WebElement leaveApproval;
    @FindBy(linkText="Leave Inquiry")
    WebElement leaveInquiry;
    //Monthly Crediting
    @FindBy(xpath="//*[@id=\"topapps\"]/li[5]/a")
    WebElement creditingTab;
    @FindBy(linkText="My Crediting History")
    WebElement creditInquiry;
    //Reports
    @FindBy(xpath="//*[@id=\"topapps\"]/li[6]/a")
    WebElement reportsTab;
    @FindBy(linkText="Leave SOA")
    WebElement leaveSOA;
    
    /*----------Current Timesheet Options----------*/
    @FindBy(linkText="Show Exceptions")
    WebElement showExceptions;
    @FindBy(linkText="Update Remarks by Batch")
    WebElement updateRemarks;
    @FindBy(linkText="Auto Complete")
    WebElement autoComplete;
    @FindBy(linkText="Submit")
    WebElement submit;
    
    @FindBy(className="jqTransformSelectWrapper")
    WebElement timesheetPeriod;
    @FindBy(className="jqTransformSelectOpen")
    WebElement timesheetPeriodArrow;
    @FindBy(xpath="//*[@id=\"period\"]/div[3]/div[2]/div/ul/li[1]/a")
    WebElement currentMonth;
    
	public HomePage(WebDriver driver) {
		this.driver = driver;
	}
	
    public void verifyInHomePage() {
        waitForElement(loggedUser);
        boolean logSuccess = loggedUser!=null;
        if(!logSuccess) {
    		throw new Error("Not in HomePage!");
    	}
        moveAndHighlightElement(loggedUser);
        reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Verified successful login");
    }
    
    public void clickCategory() {
    	Actions action = new Actions(driver);
    	waitForElement(timeLogsTab);
    	action.moveToElement(timeLogsTab).build().perform();
    	waitForElement(myTimeLogs);
    	myTimeLogs.click();
    	reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Clicked My Time Logs");
    }
    
    public void clickDate(String date) {
    	WebElement timeDate = driver.findElement(By.linkText(date));
    	moveAndHighlightElement(timeDate);
    	timeDate.click();
    	reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Clicked Date for today");
    }
    
    public void selectCurrentTimesheetPeriod(String period) {
    	waitForElement(timesheetPeriod);
    	moveAndHighlightElement(timesheetPeriod);
    	timesheetPeriodArrow.click();
    	currentMonth.click(); 
    	reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Clicked chosen period");
    }
    
    public void clickInputWhizHours(int dayOfMonth) {
    	WebElement day = driver.findElement(By.xpath("//*[@id=\"0"+dayOfMonth+"\"]/td[8]/div/a[2]/img"));
    	day.click();
    	reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Clicked Input Whiz Hours");
    }
    
    public void verifyEnteredHours(int day) {
    	WebElement dailyHours = driver.findElement(By.xpath("//*[@id=\"0"+day+"\"]/td[7]/span"));
    	moveAndHighlightElement(dailyHours);
    	Wait(3000);
    	String enteredHours = dailyHours.getText();
    	System.out.println(enteredHours);
    	boolean isSame = enteredHours.equals("8");
    	if(!isSame) {
    		throw new Error("Different entered hours!");
    	}
    	reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Verified same entered hours");
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
}