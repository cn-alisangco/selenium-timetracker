package test.TimeTracker.Login;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import base.BaseClass;
import pageObjects.timetracker.HomePage;
import pageObjects.timetracker.v2.LoginPage;
import utilities.ExcelReader;

public class TC005_TimeTracker_Login_NullCredentials extends BaseClass {
	
	LoginPage loginPage;
	HomePage homePage;
	
	private void initialize() {
		loginPage = PageFactory.initElements(getDriver(), LoginPage.class);
		homePage = PageFactory.initElements(getDriver(), HomePage.class);
	}
	
	@Test
	 public void loginToTimeTracker() throws Exception{
		
	        initialize();
	        
	    	ExcelReader creds = new ExcelReader(System.getProperty("user.dir") + testDataLoc, "Login");
	    	// user.dir + td from testng file + testsheet name
	        
	    	String id = "TC005_TimeTracker_Login_NullCredentials";
	    	String username = creds.testData(id, "username");
	    	String password = creds.testData(id, "password");
	    	
	    	//leave login empty
	    	loginPage.login(username, password);
	    	//verify error message for invalid credentials appear
	    	loginPage.verifyNull();
	    }
}
