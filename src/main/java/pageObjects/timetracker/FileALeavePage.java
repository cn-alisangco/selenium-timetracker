package pageObjects.timetracker;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import userHelper.UserHelper;

import java.time.LocalDate;

public class FileALeavePage extends UserHelper {
    WebDriver driver;

    public String methodName;
    public String desc;
    public String leaveType;
    public String reason;
    public String comment;
    public String contactNumberStr;

    By dateApplied = By.xpath("//*[@id='dialog-modal-leave']/div[1]/div[2]");
    By leaveTypes = By.xpath("//*[@id='LeaveType']");
    By runningBalance = By.xpath("//*[@id='running']");
    By projectedBalance = By.xpath("//*[@id='available']");
    By fromDate = By.xpath("//*[@id='LeaveFrom']");
    By toDate = By.xpath("//*[@id='LeaveTo']");
    By halfDayCheckbox = By.xpath("//*[@id='IsHalfday']");
    By reasons = By.xpath("//*[@id='reasonDDL']");
    By commentBox = By.xpath("//*[@id='LeaveReason']");
    By contactNumber = By.xpath("//*[@id='ContactNumber']");
    By submit = By.xpath("//*[@id='SubmitLeaveApplication']");
    By cancel = By.xpath("//*[@id='CancelLeaveApplication']");
    By leaveTypeError = By.xpath("//*[@id='errorLeaveType']");
    By reasonError = By.xpath("//*[@id='errorReasonDDL']");
    By commentBoxError = By.xpath("//*[@id='errorReason1']");
    By contactNumberError = By.xpath("//*[@id='errorContact']");
    By closeBtn = By.xpath("//div[@class='ui-dialog ui-widget ui-widget-content ui-corner-all ui-draggable customUIDialog'][3]/div[1]/a/span");

    public FileALeavePage(WebDriver driver){
        this.driver = driver;
    }

    public void verifyDateApplied(){
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        desc = "Successfully verified Date Applied";
        waitElementToLoad(dateApplied);
        moveAndHighlightElement(dateApplied);
        LocalDate date = LocalDate.now();
        String day = String.valueOf(date).split("-")[2];
        validateText("contains", getText(dateApplied), day);
        reportPass(methodName, desc);
    }

    public String selectLeaveType(String sheetName,String tcName,String columnName){
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        try{
            waitElementToLoad(leaveTypes);
            moveAndHighlightElement(leaveTypes);
            click(leaveTypes);
            leaveType = selectByVisibleText(leaveTypes,sheetName, tcName, columnName);
            desc = String.format("User selected %s from the Leave Type dropdown", leaveType);
            reportPass(methodName, desc);
            
        } catch (Exception e){
            e.printStackTrace();
        }
        return leaveType;
    }

    public void verifyRunningLeaveBalance(){
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        desc = "Successfully verified Running Leave Balance is visible";
        waitElementToLoad(runningBalance);
        moveAndHighlightElement(runningBalance);
        reportPass(methodName, desc);
    }

    public void verifyProjectedLeaveBalance(){
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        desc = "Successfully verified Projected Leave Balance is visible";
        waitElementToLoad(projectedBalance);
        moveAndHighlightElement(projectedBalance);
        reportPass(methodName, desc);
    }

    public void verifyFromDate(){
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        desc = "Successfully verified leave start date is visible";
        waitElementToLoad(fromDate);
        moveAndHighlightElement(fromDate);
        reportPass(methodName, desc);
    }

    public void verifyToDate(){
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        desc = "Successfully verified leave end date is visible";
        waitElementToLoad(toDate);
        moveAndHighlightElement(toDate);
        reportPass(methodName, desc);
    }

    public void markHalfDayCheckbox(){
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        desc = "User marked the Half Day checkbox";
        waitElementToLoad(halfDayCheckbox);
        moveAndHighlightElement(halfDayCheckbox);
        click(halfDayCheckbox);
        reportPass(methodName, desc);
    }

    public void unmarkedHalfDayCheckbox(){
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        desc = "User unmarked the Half Day checkbox";
        waitElementToLoad(halfDayCheckbox);
        moveAndHighlightElement(halfDayCheckbox);
        click(halfDayCheckbox);
        reportPass(methodName, desc);
    }

    public void selectReason(String sheetName,String tcName,String columnName){
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        try{
            waitElementToLoad(reasons);
            moveAndHighlightElement(reasons);
            click(reasons);
            reason = selectByVisibleText(reasons,sheetName, tcName, columnName);
            desc = String.format("User selected %s from the Reasons dropdown", reason);
            reportPass(methodName, desc);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void populateCommentTextBox(String sheetName, String tcName, String columnName){
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        waitElementToLoad(commentBox);
        moveAndHighlightElement(commentBox);
        comment = sendKeys(commentBox, sheetName, tcName, columnName);
        desc = String.format("User entered %s in the Comment text box", comment);
        reportPass(methodName, desc);
    }

    public void populateContactNumberField(String sheetName, String tcName, String columnName){
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        waitElementToLoad(contactNumber);
        moveAndHighlightElement(contactNumber);
        contactNumberStr = sendKeys(contactNumber, sheetName, tcName, columnName);
        desc = String.format("User entered %s in the Contact Number field", contactNumberStr);
        reportPass(methodName, desc);
    }

    public void clickSubmitBtn(){
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        desc = "User clicked the Submit button";
        waitElementToLoad(submit);
        moveAndHighlightElement(submit);
        hover(submit);
        click(submit);
        reportPass(methodName, desc);
    }

    public void clickSubmitBtnAndWait(){
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        desc = "User clicked the Submit button";
        waitElementToLoad(submit);
        moveAndHighlightElement(submit);
        hover(submit);
        click(submit);
        reportPass(methodName, desc);
        //Wait(10000);
    }

    public boolean verifyCancelBtn(){
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        desc = "Successfully verified the existence of the Cancel button";
        reportPass(methodName, desc);
        return elementExistenceFlag(cancel);
    }
    public void clickCancelBtn(){
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        desc = "User clicked the Cancel button";
        waitElementToLoad(cancel);
        moveAndHighlightElement(cancel);
        hover(cancel);
        click(cancel);
        reportPass(methodName, desc);
    }
    
    public void clickCloseBtn() {
    	 methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
         desc = "User clicked the Close button";
         waitElementToLoad(closeBtn);
         moveAndHighlightElement(closeBtn);
         hover(closeBtn);
         click(closeBtn);
         reportPass(methodName, desc);
    }

    public void verifyMissingLeaveTypeError(){
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        desc = "Successfully verified that the Leave Type has not been selected";
        waitElementToLoad(leaveTypeError);
        moveAndHighlightElement(leaveTypeError);
        reportPass(methodName, desc);
    }

    public void verifyMissingReasonError(){
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        desc = "Successfully verified that the Reason has not been selected";
        waitElementToLoad(reasonError);
        moveAndHighlightElement(reasonError);
        reportPass(methodName, desc);
    }

    public void verifyMissingCommentError(){
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        desc = "Successfully verified that the Comment has not been entered";
        waitElementToLoad(commentBoxError);
        moveAndHighlightElement(commentBoxError);
        reportPass(methodName, desc);
    }

    public void verifyMissingContactNumberError(){
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        desc = "Successfully verified that the Contact Number has not been entered";
        waitElementToLoad(contactNumberError);
        moveAndHighlightElement(contactNumberError);
        reportPass(methodName, desc);
    }

    public void verifyNotEnoughBalance(By locator){
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        desc = "Successfully verified that the Leave balance is not enough";
        waitElementToLoad(locator);
        moveAndHighlightElement(locator);
        reportPass(methodName, desc);
    }

    public void clickOkInNotEnoughBalanceErrorMessage(By locator){
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        desc = "User clicked the OK button";
        waitElementToLoad(locator);
        moveAndHighlightElement(locator);
        hover(locator);
        click(locator);
        reportPass(methodName, desc);
    }
}
