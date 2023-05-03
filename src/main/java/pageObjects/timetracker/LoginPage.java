package pageObjects.timetracker;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import userHelper.UserHelper;

public class LoginPage extends UserHelper {
	
	WebDriver driver;
	public String desc;
	public String methodName;
	private static String username;
	private static String expectedPageTitle = "Login";
	private static String actualPageTitle;
	By advanced = By.id("details-button");
	By proceed = By.id("proceed-link");
	By blocked = By.linkText("I understand the risks, take me there anyway");
	By userTxtbox = By.xpath("//input[@id='Username']");
	By passTxtbox = By.xpath("//input[@id='Password']");
	By loginErrorMessage = By.xpath("//*[@id='Login']/p");
	By logInBtn = By.xpath("//button[@id='LoginSubmit']");
	By usernameValidationError = By.xpath("//span[@data-valmsg-for='Username']");
	By passwordValidationError = By.xpath("//span[@data-valmsg-for='Password']");

	public LoginPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public void proceedToLoginPage() {
		click(advanced);
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		desc = "Clicked the \"I understand the risks, take me there anyway\" link.";
		//waitElementToLoad(blocked);
		//moveAndHighlightElement(blocked);
		//click(blocked);
		waitElementToLoad(proceed);
		moveAndHighlightElement(proceed);
		click(proceed);
		reportPass(methodName, desc);
	}

	public void setUsername(String sheetName, String tcName, String columnName) {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		desc = "Entered the username";
		waitElementToLoad(userTxtbox);
		moveAndHighlightElement(userTxtbox);
		//Wait(1000);
		setUserName(sendKeys(userTxtbox, sheetName, tcName, columnName));
		reportPass(methodName, desc);
	}

	public void setPassword(String sheetName, String tcName, String columnName) {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		desc = "Entered the password";
		waitElementToLoad(passTxtbox);
		moveAndHighlightElement(passTxtbox);
		//Wait(1000);
		sendKeys(passTxtbox,sheetName, tcName, columnName);
		reportPass(methodName, desc);
	}

	public void clickLogin() {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		desc = "Clicked the Login button";
		waitElementToLoad(logInBtn);
		moveAndHighlightElement(logInBtn);
		click(logInBtn);
		reportPass(methodName, desc);
	}

	public void verifyLoginErrorMessage(){
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		desc = "Incorrect username or password error message is displayed";
		waitElementToLoad(loginErrorMessage);
		moveAndHighlightElement(loginErrorMessage);
		reportPass(methodName, desc);
	}

	public void verifyNullFields(){
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		desc = "Empty username and password field validators are displayed";
		waitElementToLoad(usernameValidationError);
		waitElementToLoad(passwordValidationError);
		moveAndHighlightElement(usernameValidationError);
		moveAndHighlightElement(passwordValidationError);
		reportPass(methodName, desc);
	}

	public void verifyUserIsLoggedOut() {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		desc = "Successfully logged out.";
		actualPageTitle = driver.getTitle();
		validateTitle(actualPageTitle, expectedPageTitle);
		reportPass(methodName, desc);
	}
	
	public static String getUserName() {
		return username;
	}

	public void setUserName(String username) {
		this.username = username;
	}

}
