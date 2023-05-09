package pageObjects.timetracker.v2;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.By;

import utilities.UserHelper;

public class FileALeavePage extends UserHelper {
	
	 WebDriver driver;
	 
	  @FindBy(xpath="//*[@id='LeaveType']")
	  WebElement leaveTypes;
	  
	  @FindBy(xpath="//*[@id='CancelLeaveApplication']")
	  WebElement cancel;

	    public String methodName;
	    public String desc;
	    public String leaveType;
	    public String reason;
	    public String comment;
	    public String contactNumberStr;

	    By dateApplied = By.xpath("//*[@id='dialog-modal-leave']/div[1]/div[2]");
	    //By leaveTypes = By.xpath("//*[@id='LeaveType']");
	    By runningBalance = By.xpath("//*[@id='running']");
	    By projectedBalance = By.xpath("//*[@id='available']");
	    By fromDate = By.xpath("//*[@id='LeaveFrom']");
	    By toDate = By.xpath("//*[@id='LeaveTo']");
	    By halfDayCheckbox = By.xpath("//*[@id='IsHalfday']");
	    By reasons = By.xpath("//*[@id='reasonDDL']");
	    By commentBox = By.xpath("//*[@id='LeaveReason']");
	    By contactNumber = By.xpath("//*[@id='ContactNumber']");
	    By submit = By.xpath("//*[@id='SubmitLeaveApplication']");
	   // By cancel = By.xpath("//*[@id='CancelLeaveApplication']");
	    By leaveTypeError = By.xpath("//*[@id='errorLeaveType']");
	    By reasonError = By.xpath("//*[@id='errorReasonDDL']");
	    By commentBoxError = By.xpath("//*[@id='errorReason1']");
	    By contactNumberError = By.xpath("//*[@id='errorContact']");
	    By closeBtn = By.xpath("//div[@class='ui-dialog ui-widget ui-widget-content ui-corner-all ui-draggable customUIDialog'][3]/div[1]/a/span");

	    public FileALeavePage(WebDriver driver){
	        this.driver = driver;
	    }
	    
	    public void selectLeaveType(String value){
	        try{
	        	waitForElement(leaveTypes);
	            moveAndHighlightElement(leaveTypes);
	            leaveTypes.click();
	            selectByVisibleText(leaveTypes, value);
	            desc = String.format("User selected %s from the Leave Type dropdown", value);
	            reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(),desc );
	            
	        } catch (Exception e){
	            e.printStackTrace();
	        }
	    }
	    
	    public void clickCancelBtn(){
	        waitForElement(cancel);
	        moveAndHighlightElement(cancel);
	        cancel.click();
	        reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(),"User clicked the Cancel button" );
	    }

}
