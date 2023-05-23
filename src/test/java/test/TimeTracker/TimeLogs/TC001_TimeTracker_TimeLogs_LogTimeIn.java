package test.TimeTracker.TimeLogs;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import base.BaseClass;
import pageObjects.timetracker.v2.HomePage;
import pageObjects.timetracker.v2.LoginPage;
import utilities.ExcelReader;

public class TC001_TimeTracker_TimeLogs_LogTimeIn extends BaseClass {
	
	LoginPage loginPage;
	HomePage homePage;
	
	private void initialize() {
		loginPage = PageFactory.initElements(getDriver(), LoginPage.class);
		homePage = PageFactory.initElements(getDriver(), HomePage.class);
	}
	
	@Test
	 public void loginToTimeTracker() throws Exception{
		
	        initialize();
	        
	    	ExcelReader creds = new ExcelReader(System.getProperty("user.dir") + testDataLoc, "Time Logs");
	    	// user.dir + td from testng file + testsheet name
	        
	    	String id = "TC001_TimeTracker_TimeLogs_LogTimeIn";
	    	String user = creds.testData(id, "username");
	    	String pass = creds.testData(id, "password");
	    	
	    	loginPage.login(user, pass);
	    	//verify if successful login
	    	if(!homePage.verifySuccessfulLogin()) {
	    		throw new Exception("Login error!");
	    	}
	    	//homePage.clickCategory();
	    	
	    	//setting date today
	    	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("M/dd/yyyy");
	    	LocalDateTime now = LocalDateTime.now();
	    	homePage.clickDate(dtf.format(now));
	    	
	    	
	    }
}
