package test.TimeTracker.LeaveApplication;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import base.BaseClass;
import pageObjects.timetracker.v2.FileALeavePage;
import pageObjects.timetracker.HomePage;
import pageObjects.timetracker.v2.LoginPage;
import utilities.ExcelReader;

public class TC004_Timetracker_LeaveApplication_LeaveTypes extends BaseClass {
	
	FileALeavePage fileALeavePage;
	HomePage homePage;
	
	public static int counter;
    public static int innerCounter;
    public static String stringCounter;
    public static String remarksColPathStr = "//*[@id='counter']/td[10]";
    public static String fileALeaveXPathStr = "//*[@id='counter']/td[8]/div/a[1]";
	
	private void initialize() {
		fileALeavePage = PageFactory.initElements(getDriver(), FileALeavePage.class);
		homePage = PageFactory.initElements(getDriver(), HomePage.class);
		counter = 1;
	}
	
	@Test
	 public void leaveTypesSelectable(){
		
	        initialize();
	        
	        ExcelReader creds = new ExcelReader(System.getProperty("user.dir") + testDataLoc, "LeaveApplication");
	    	// user.dir + td from testng file + testsheet name
	        
	        String id = "valid_leaveType";
	    	String leaveType = creds.testData(id, "leave type");
	    	
	    	while(counter < homePage.getTotalDates() + 1){
	            stringCounter = String.format("0%s",String.valueOf(counter));
	            By fileALeaveXPath = By.xpath(fileALeaveXPathStr.replace("counter", stringCounter));
	            By remarksColPath = By.xpath(remarksColPathStr.replace("counter", stringCounter));
	            if(homePage.verifyRemarksCol(remarksColPath) && homePage.verifyFileALeaveIsAvailable(fileALeaveXPath)){
	                homePage.clickFileALeave(fileALeaveXPath);
	                innerCounter = 1;
	                while(innerCounter < 14){
	                    fileALeavePage.selectLeaveType(leaveType);
	                    innerCounter++;
	                }
	                fileALeavePage.clickCancelBtn();
	            }
	            counter++;
	        }
	        
	}
}