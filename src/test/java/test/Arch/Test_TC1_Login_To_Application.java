package test.Arch;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import base.BaseClass;
import pageObjects.timetracker.HomePage;
import pageObjects.timetracker.LoginPage;
import utilities.ReadExcelData;

@Listeners(utilities.TestNGListeners.class) // need to uncomment this when running this TC only
public class Test_TC1_Login_To_Application extends BaseClass {

	LoginPage loginPage;
	HomePage homePage;
	
	public static String sheetName = "AUT_TimeTracker"; // sheet name of the test data to be used
	public static String tcName = "TC1_TimeTracker"; // test case name
	// TestNG Annotation
	// This is the Actual Test

	@Test(priority = 1)

	public void Login_To_Application() {
		loginPage = new LoginPage(getDriver());
		homePage = new HomePage(getDriver());
		loginPage.proceedToLoginPage();
		loginPage.setUsername(sheetName, tcName, "Username");
		loginPage.setPassword(sheetName, tcName, "Password");
		loginPage.clickLogin();
		homePage.verifySuccessfulLogin();
		
	}
}
