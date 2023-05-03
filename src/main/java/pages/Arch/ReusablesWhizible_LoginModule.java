package pages.Arch;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class ReusablesWhizible_LoginModule{
	private static Whiz_LoginPage loginPage;
	private static Whiz_HomePage homePage;

	public ReusablesWhizible_LoginModule(WebDriver driver) {
		loginPage = PageFactory.initElements(driver, Whiz_LoginPage.class);
		homePage = PageFactory.initElements(driver, Whiz_HomePage.class);
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
