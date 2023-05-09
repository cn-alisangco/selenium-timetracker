package test.TimeTracker.LeaveApplication;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import base.BaseClass;
import pageObjects.timetracker.FileALeavePage;
import pageObjects.timetracker.HomePage;


public class TC001_TimeTracker_FileLeave_ActionsButton extends BaseClass {
	
	FileALeavePage fileALeavePage;
	HomePage homePage;
	
	public static int counter;
    public static String stringCounter;
	public static String remarksColPathStr = "//*[@id='counter']/td[10]";
	public static String fileALeaveXPathStr = "//*[@id='counter']/td[8]/div/a[1]";
	
	private void initialize() {
		fileALeavePage = PageFactory.initElements(getDriver(), FileALeavePage.class);
		homePage = PageFactory.initElements(getDriver(), HomePage.class);
		counter = 1;
	}
	
	@Test
	 public void leaveButtonClickable(){
		
	        initialize();
	        while(counter < homePage.getTotalDates() + 1){
	            stringCounter = String.format("0%s",String.valueOf(counter));
	            By fileALeaveXPath = By.xpath(fileALeaveXPathStr.replace("counter", stringCounter));
	            By remarksColPath = By.xpath(remarksColPathStr.replace("counter", stringCounter));
	            if(homePage.verifyRemarksCol(remarksColPath) && homePage.verifyFileALeaveIsAvailable(fileALeaveXPath)){
	                homePage.clickFileALeave(fileALeaveXPath);
	                fileALeavePage.clickCancelBtn();
	            }
	            counter++;
	        }
	        
	        
	}


	        

}
