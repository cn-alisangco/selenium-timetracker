package pageObjects.whizible;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import userHelper.UserHelper;

public class Whizible_HomePage extends UserHelper {
    WebDriver driver;
    public String desc;
    public String methodName;
    public static String employeeName;
    By employeeNameTag = By.xpath("//*[@id='EmployeeName']");
    By timesheetPageBtn = By.xpath("//*[@id='a_10']/span");
    By timesheetEntryPageBtn = By.xpath("//*[@id='mainmenu_10']/ul/li[4]/a/span");

    By logoutDropdown = By.xpath("//li[@class='dropdown user user-menu']/a");
    By logoutBtn = By.xpath("//ul[@id='Ulmenu']/li[2]/div/a");
    By logoutBtnInModal = By.xpath("//button[@id='btnLogout']");


    public Whizible_HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void verifySuccessfulLogin() {
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        waitElementToLoad(employeeNameTag);
        moveAndHighlightElement(employeeNameTag);
        employeeName = getText(employeeNameTag);
        desc = "Successfully logged in using " + employeeName + " credentials";
        reportPass(methodName, desc);
    }

    public void clickTimesheetPageBtn(){
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        waitElementToLoad(timesheetPageBtn);
        moveAndHighlightElement(timesheetPageBtn);
        hover(timesheetPageBtn);
        click(timesheetPageBtn);
        desc = "Clicked the Timesheet button";
        reportPass(methodName, desc);
    }

    public void clickTimesheetEntryPageBtn(){
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        waitElementToLoad(timesheetEntryPageBtn);
        moveAndHighlightElement(timesheetEntryPageBtn);
        hover(timesheetEntryPageBtn);
        click(timesheetEntryPageBtn);
        desc = "Clicked the Timesheet Entry button";
        reportPass(methodName, desc);
        //getHeaderNames();
    }

	public void clickLogoutButton() {
		switchToDefaultContent();
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		desc = "Clicked the logout button";
		waitElementToLoad(logoutDropdown);
        moveAndHighlightElement(logoutDropdown);
		click(logoutDropdown);
		waitElementToLoad(logoutBtn);
        moveAndHighlightElement(logoutBtn);
		click(logoutBtn);
		waitElementToLoad(logoutBtnInModal);
        moveAndHighlightElement(logoutBtnInModal);
		click(logoutBtnInModal);
		reportPass(methodName, desc);
	}
	

}
