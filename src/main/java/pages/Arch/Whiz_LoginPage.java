package pages.Arch;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import utilities.UserHelper;

public class Whiz_LoginPage extends UserHelper {
    WebDriver driver;
    
    @FindBy(css="input#txtLogin")
    WebElement txtUsername;
    
    @FindBy(css="input#txtPassword")
    WebElement txtPassword;
    
    @FindBy(xpath="//*[@id='loginformboxformD']//*[text()='Sign In']")
    WebElement btnSignIn;
    
    @FindBy(xpath="//*[@id='lblLoginError']")
    WebElement lblErrMsgLogin;
    
    @FindBy(xpath="//*[@id='SpanEmailID']")
    WebElement lblerrMsgEmptyTxtUsername;
    
    @FindBy(xpath="//*[@id='SpanPassword']")
    WebElement lblerrMsgEmptyTxtPassword;
  
    public Whiz_LoginPage(WebDriver driver) {
        this.driver = driver;
    }
    
    public void vfyMsgErrLogin(){
    	waitForElement(lblErrMsgLogin);
    	moveAndHighlightElement(lblErrMsgLogin);
    	reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(),
    			"Successfully verified that the invalid login error message is displayed");
    }

    public void vfyMsgErrEmptyTxtUsername(){
        waitForElement(lblerrMsgEmptyTxtUsername);
        moveAndHighlightElement(lblerrMsgEmptyTxtUsername);
        reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), 
        		"Successfully verified that the empty username field message is displayed");
    }

    public void vfyMsgErrEmptyTxtPassword(){
        waitForElement(lblerrMsgEmptyTxtPassword);
        moveAndHighlightElement(lblerrMsgEmptyTxtPassword);
        reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), 
        		"Successfully verified that the empty password field message is displayed");
    }

    public void verifyUserIsLoggedOut() {
        waitForElement(txtUsername);
        waitForElement(txtPassword);
        reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), 
        		"Successfully verified that the User is logged out");
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
    
    public void clickSignIn() {
    	waitForElement(btnSignIn);
        moveAndHighlightElement(btnSignIn);
        btnSignIn.click();
        reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Clicked the Sign In button");
    }
}
