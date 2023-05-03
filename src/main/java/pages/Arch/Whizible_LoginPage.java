package pages.Arch;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Whizible_LoginPage extends UserHelper {
    WebDriver driver;
    public String desc;
    public String methodName;
    private static String username;
    By usernameField = By.xpath("//*[@id='txtLogin']");
    By passwordField = By.xpath("//*[@id='txtPassword']");
    By logInBtn = By.xpath("//*[@id='loginformboxformD']/div[2]/div[4]/button");
    By emptyUsernameFieldMessage = By.xpath("//*[@id='SpanEmailID']");
    By emptyPasswordFieldMessage = By.xpath("//*[@id='SpanPassword']");
    By logInErrorMessage = By.xpath("//*[@id='lblLoginError']");

    public Whizible_LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void setUsername(String sheetName, String tcName, String columnName) {
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        desc = "Entered the username";
        waitElementToLoad(usernameField);
        moveAndHighlightElement(usernameField);
        setUserName(sendKeys(usernameField, sheetName, tcName, columnName));
        reportPass(methodName, desc);
    }

    public void setPassword(String sheetName, String tcName, String columnName) {
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        desc = "Entered the password";
        waitElementToLoad(passwordField);
        moveAndHighlightElement(passwordField);
        sendKeys(passwordField,sheetName, tcName, columnName);
        reportPass(methodName, desc);
    }

    public void clickLogin() {
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        desc = "Clicked the Sign In button";
        waitElementToLoad(logInBtn);
        moveAndHighlightElement(logInBtn);
        click(logInBtn);
        reportPass(methodName, desc);
    }

    public void verifyLoginErrorMessage(){
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        desc = "Successfully verified that the invalid login error message is displayed";
        try {
			waitElementToLoad(logInErrorMessage);
	        moveAndHighlightElement(logInErrorMessage);
		} catch (org.openqa.selenium.StaleElementReferenceException ex) {
			waitElementToLoad(logInErrorMessage);
	        moveAndHighlightElement(logInErrorMessage);
		}
        reportPass(methodName, desc);
    }

    public void verifyEmptyUsernameField(){
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        desc = "Successfully verified that the empty username field message is displayed";
        waitElementToLoad(emptyUsernameFieldMessage);
        moveAndHighlightElement(emptyUsernameFieldMessage);
        reportPass(methodName, desc);
    }

    public void verifyEmptyPasswordField(){
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        desc = "Successfully verified that the empty password field message is displayed";
        waitElementToLoad(emptyPasswordFieldMessage);
        moveAndHighlightElement(emptyPasswordFieldMessage);
        reportPass(methodName, desc);
    }

    public void verifyUserIsLoggedOut() {
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        desc = "Successfully verified that the User is logged out";
        waitElementToLoad(usernameField);
        waitElementToLoad(usernameField);
        waitElementToLoad(passwordField);
        waitElementToLoad(passwordField);
        reportPass(methodName, desc);
    }

    public static String getUserName() {
        return username;
    }

    public void setUserName(String username) {
        this.username = username;
    }
}
