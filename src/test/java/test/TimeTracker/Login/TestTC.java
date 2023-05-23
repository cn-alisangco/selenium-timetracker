package test.TimeTracker.Login;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import base.BaseClass;
import pageObjects.timetracker.HomePage;
import pageObjects.timetracker.v2.LoginPage;
import utilities.ExcelReader;

public class TestTC extends BaseClass {
	
	LoginPage loginPage;
	HomePage homePage;
	
	private void initialize() {
		loginPage = PageFactory.initElements(getDriver(), LoginPage.class);
		homePage = PageFactory.initElements(getDriver(), HomePage.class);
	}
	
	@Test
	 public void loginToTimeTracker(){
		
	        initialize();
	        
	    	ExcelReader creds = new ExcelReader(System.getProperty("user.dir") + testDataLoc, "Login");
	    	// user.dir + td from testng file + testsheet name
	        
	    	String id = "valid_credentials";
	    	System.out.println(System.getProperty("user.dir") + testDataLoc);
	    	System.out.println(creds);
	    	//String user = creds.testData(id, "username");
	    	//String pass = creds.testData(id, "password");
	    	
	    	//loginPage.login(user, pass);
	    	

	    }
}
