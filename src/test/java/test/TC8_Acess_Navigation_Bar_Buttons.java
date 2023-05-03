package test;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import base.BaseClass;
import pageObjects.timetracker.HomePage;

@Listeners(utilities.TestNGListeners.class) //need to uncomment this when running this TC only
public class TC8_Acess_Navigation_Bar_Buttons extends BaseClass {
	HomePage homePage;
	TC1_Login_To_Application tc1;
	public static String sheetName = "TC1_TD"; //sheet name of the test data to be used
	
	
	// TestNG Annotation
	// This is the Actual Test
	@Test
	
	public void Access_Navigation_Bar_Buttons() {
		tc1 = new TC1_Login_To_Application();
		tc1.Login_To_Application();
		
		homePage = new HomePage(getDriver());
		homePage.clickMyTimesheets();
		homePage.clickShiftTypes();
		homePage.clickLeavesBalances();
		homePage.clickMonthlyCreditingHistory();
		homePage.clickLeaveSOA();
		homePage.clickLogout();
	}
	
}
