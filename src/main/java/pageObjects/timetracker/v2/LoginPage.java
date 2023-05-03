package pageObjects.timetracker.v2;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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
    
    public void login(String username, String password) {
    	enterUsername(username);
    	enterPassword(password);
    	clickLogin();
    }
}
