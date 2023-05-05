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
    
    @FindBy(xpath="//*[@id=\"Login\"]/p")
    WebElement msgErrorLogin;
    
    @FindBy(xpath="//span[@data-valmsg-for='Username']")
    WebElement msgErrorNullUsername;
    
    @FindBy(xpath="//span[@data-valmsg-for='Password']")
    WebElement msgErrorNullPassword;
    
    private static String methodName = "Thread.currentThread().getStackTrace()[1].getMethodName()";
    
	public LoginPage(WebDriver driver) {
		this.driver = driver;
	}
	
    public void enterUsername(String value) {
        waitForElement(txtUsername);
        moveAndHighlightElement(txtUsername);
        txtUsername.sendKeys(value);
        reportPass(methodName, "Entered the username");
    }	
    
    public void enterPassword(String value) {
    	waitForElement(txtPassword);
        moveAndHighlightElement(txtPassword);
        txtPassword.sendKeys(value);
        reportPass(methodName, "Entered the password");
    }
    
    public void clickLogin() {
    	waitForElement(btnLogin);
        moveAndHighlightElement(btnLogin);
        btnLogin.click();
        reportPass(methodName, "Clicked the Log In button");
    }
    
    public void login(String username, String password) {
    	enterUsername(username);
    	enterPassword(password);
    	clickLogin();
    }
    
    // Added verifications for error messages during failed login attempts
    
    public void verifyLoginErrorMessage(){
    	waitForElement(msgErrorLogin);
		moveAndHighlightElement(msgErrorLogin);
        reportPass(methodName, "Incorrect username or password error message is displayed.");
	}

	public void verifyNullFields(){
		waitForElement(msgErrorNullUsername);
		waitForElement(msgErrorNullPassword);
		moveAndHighlightElement(msgErrorNullUsername);
		moveAndHighlightElement(msgErrorNullPassword);
		reportPass(methodName, "Empty username and password field validators are displayed.");
	}
}
