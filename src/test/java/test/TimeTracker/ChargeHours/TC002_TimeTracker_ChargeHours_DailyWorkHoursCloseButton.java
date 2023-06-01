package test.TimeTracker.ChargeHours;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import base.BaseClass;
import pageObjects.timetracker.v2.DailyWorkHours;
import pageObjects.timetracker.v2.EditTimeLogs;
import pageObjects.timetracker.v2.HomePage;
import pageObjects.timetracker.v2.LoginPage;
import utilities.ExcelReader;
import utilities.TimeParser;

public class TC002_TimeTracker_ChargeHours_DailyWorkHoursCloseButton extends BaseClass {
	
	LoginPage loginPage;
	HomePage homePage;
	DailyWorkHours dailyWorkHours;
	
	private void initialize() {
		loginPage = PageFactory.initElements(getDriver(), LoginPage.class);
		homePage = PageFactory.initElements(getDriver(), HomePage.class);
		dailyWorkHours = PageFactory.initElements(getDriver(), DailyWorkHours.class);
	}
	
	@BeforeClass
	public void disablePopup() {
		ChromeOptions options = new ChromeOptions(); 
		//options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
		options.addArguments("disable-infobars");
	}
	
	@Test
	 public void loginToTimeTracker() throws Exception{
		
	        initialize();
	        dailyWorkHours.setBrowserZoom(80);
	        //disablePopup();
	        
	    	ExcelReader creds = new ExcelReader(System.getProperty("user.dir") + testDataLoc, "Time Logs");
	    	// user.dir + td from testng file + testsheet name
	        
	    	String id = "TC001_TimeTracker_TimeLogs_LogTimeIn";
	    	String user = creds.testData(id, "username");
	    	String pass = creds.testData(id, "password");
	    	
	    	loginPage.login(user, pass);
	    	homePage.verifySuccessfulLogin();
	    	
	    	int dayOfMonth = LocalDateTime.now().getDayOfMonth();
	    	
	    	homePage.clickInputWhizHours(dayOfMonth);
	    	dailyWorkHours.verifyInputWhizExists();
	    	
	    	
	    	//initialize();
	        //WebElement e = driver.findElement(By.xpath("/html/body/div[9]/div[1]/a/span"));
	        //e.click();
	    	
	    	dailyWorkHours.closeInputWhizHours();
	    	//homePage.verifySuccessfulLogin();
	}
}
