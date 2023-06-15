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
	
	/* --------------------------------------------LOCATORS --------------------------------------- */
    @FindBy(id="Username") WebElement txtUsername;
    @FindBy(id="Password") WebElement txtPassword;
    @FindBy(id="LoginSubmit") WebElement btnLogin;
    
    /* --------------------------------------------CONSTRUCTOR --------------------------------------- */
    
	public LoginPage(WebDriver driver) {
		this.driver = driver;
	}
	
	
	/* -----------------------------------------PUBLIC METHODS --------------------------------------- */
	
	//ACTIONS--------------------------------------------------------------------------------------------
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
    
    public void login(String username, String password) {
    	enterUsername(username);
    	enterPassword(password);
    	clickLogin();
    	
    	String methodName = "Log in to time tracker";
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), methodName);
    }
    
    public void login(String username, String password, String accountPrecondition) {

    	enterUsername(username);
    	enterPassword(password);
    	clickLogin();
    	
    	String methodName = "Logged in to time tracker using an account with the following precondition/s: " + accountPrecondition;
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), methodName);
    }
    
    
    
 
}
