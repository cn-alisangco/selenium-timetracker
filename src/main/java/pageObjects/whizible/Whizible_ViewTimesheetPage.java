package pageObjects.whizible;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import userHelper.UserHelper;

public class Whizible_ViewTimesheetPage extends UserHelper {
    WebDriver driver;
    public String desc;
    public String methodName;
    public String actualHoursStr;
    By statusSection = By.xpath("//*[@id='DivTVStatusSection']");
    By statusTxtPath = By.xpath("//*[@id=\"DivTVStatusSection\"]/span");
    By submitBtn = By.xpath("//*[@id='btnSubmit']");
    By alertMessage = By.xpath("//*[@id=\"alertMsg\"]");
    By backBtnForNoTimesheetAvailable = By.xpath("//*[@id=\"ulBackButton\"]/li/a");
    By actualHours = By.xpath("(//span[@class='ActualHours'])[1]");

    public Whizible_ViewTimesheetPage(WebDriver driver) {
        this.driver = driver;
    }

    public void verifyViewTimesheetPage(){
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        waitElementToLoad(statusSection);
        moveAndHighlightElement(statusSection);
        desc = "Successfully verified that the View Timesheet page is displayed";
        reportPass(methodName, desc);
    }

    public boolean verifyActualHours(){
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        waitElementToLoad(actualHours);
        moveAndHighlightElement(actualHours);
        actualHoursStr = getText(actualHours);
        desc = String.format("Successfully verified that the actual hour(s) is %s", actualHoursStr);
        reportPass(methodName, desc);
        return !actualHoursStr.equals("00:00");
    }

    public void clickSubmitBtn(){
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        waitElementToLoad(submitBtn);
        moveAndHighlightElement(submitBtn);
        hover(submitBtn);
        click(submitBtn);
        desc = "Clicked the Submit button";
        reportPass(methodName, desc);
        Wait(5000);
    }

    public void clickBackBtn(){
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        waitElementToLoad(backBtnForNoTimesheetAvailable);
        moveAndHighlightElement(backBtnForNoTimesheetAvailable);
        hover(backBtnForNoTimesheetAvailable);
        click(backBtnForNoTimesheetAvailable);
        desc = "Clicked the Back button";
        reportPass(methodName, desc);
    }

    public String getTimesheetStatus(){
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        waitElementToLoad(statusTxtPath);
        moveAndHighlightElement(statusTxtPath);
        desc = String.format("Timesheet status is now %s", getText(statusTxtPath));
        reportPass(methodName, desc);
        return getText(statusTxtPath);
    }
}
