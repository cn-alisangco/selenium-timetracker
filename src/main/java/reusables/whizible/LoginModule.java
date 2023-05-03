package reusables.whizible;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import pageObjects.whizible.HomePage;
import pageObjects.whizible.LoginPage;

public class LoginModule{
	private static LoginPage loginPage;
	private static HomePage homePage;

	public LoginModule(WebDriver driver) {
		loginPage = PageFactory.initElements(driver, LoginPage.class);
		homePage = PageFactory.initElements(driver, HomePage.class);
	}

	private void validCredentials() {
		homePage.vfySuccessLogin();
		homePage.clickSidebarMenu("Timesheet");
		homePage.clickSidebarMenu("Timesheet Entry");
		homePage.clickLogoutButton();
	}
	
	private void invalidCredentials() {
		loginPage.vfyMsgErrLogin();
	} 
	
	private void nullCredentials() {
		loginPage.vfyMsgErrEmptyTxtUsername();
		loginPage.vfyMsgErrEmptyTxtPassword();
	}
	
	public void vfyLogin(String scenario) {
		
		switch (scenario) {
			case "ALL_VALID":
				validCredentials();
				break;
			case "INVALID_USERNAME":
			case "INVALID_PASSWORD":
			case "ALL_INVALID":
				invalidCredentials();
				break;
			case "NULL_CREDENTIALS":
				nullCredentials();
				break;
			default:
				// do nothing
				break;
		}
	}
	
	public void login(String username, String password){
		loginPage.enterUsername(username);
		loginPage.enterPassword(password);
		loginPage.clickSignIn();
	}
}
