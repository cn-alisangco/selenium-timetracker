package pageObjects.timetracker.v2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import utilities.ExcelReader;
import utilities.UserHelper;

public class LoginPage extends UserHelper {
	WebDriver driver;
	
    @FindBy(id="Username")
    WebElement txtUsername;
	
    @FindBy(id="Password")
    WebElement txtPassword;
    
    @FindBy(id="LoginSubmit")
    WebElement btnLogin;
    
	public LoginPage(WebDriver driver) {
		this.driver = driver;
	}
	
    public void enterUsername(String value) {
        waitForElement(txtUsername);
        moveAndHighlightElement(txtUsername);
        txtUsername.sendKeys(value);
        reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Entered the username");
    }	
    
    public void enterPassword(String value) {
    	waitForElement(txtPassword);
        moveAndHighlightElement(txtPassword);
        txtPassword.sendKeys(value);
        reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Entered the password");
    }
    
    public void clickLogin() {
    	waitForElement(btnLogin);
        moveAndHighlightElement(btnLogin);
        btnLogin.click();
        reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Clicked the Log In button");
    }
    
    //newly added 5/26/2023
    public HashMap<String, String> getLoginCredentialsTestData(String testDataLoc, String sheetname, String recordID) {
    	
    	HashMap<String, String> loginCredentials = new HashMap<String, String>();
    	ExcelReader creds = new ExcelReader(System.getProperty("user.dir") + testDataLoc, sheetname);
    	// user.dir + td from testng file + testsheet name
        
	    String id = recordID;
	    String user = creds.testData(id, "username");
	    String pass = creds.testData(id, "password");
	    
	    loginCredentials.put("username", user);
	    loginCredentials.put("password", pass);
	    
		return loginCredentials;
    }
    
    public void login(String username, String password) {
    	enterUsername(username);
    	enterPassword(password);
    	clickLogin();
    }
    
 
}
