package pages.Arch;

import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import utilities.UserHelper;

public class Whiz_HomePage extends UserHelper {
    WebDriver driver;
    
    @FindBy(id="EmployeeName")
    WebElement lblEmployeeName;
    
    @FindBy(xpath="//li[@class='dropdown user user-menu']/a")
    WebElement ddLogout;
    
    @FindBy(css=".btn#logout")
    WebElement btnLogout;

    @FindBy(xpath="//button[@id='btnLogout']")
    WebElement modalBtnLogout;

    public Whiz_HomePage(WebDriver driver) {
        this.driver = driver;
    }
    
    public void clickSidebarMenu(String lblMenu) {
    	By locator = By.xpath("//span[text()='" + lblMenu + "']/parent::a");
    	WebElement e = driver.findElement(locator);
    	waitForClickable(e);
    	moveAndHighlightElement(e);
    	e.click();
    	
    	reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), 
    			"Clicked the " + lblMenu);
    }

    public void vfySuccessLogin() {
    	String employeeName;
        waitForElement(lblEmployeeName);
        moveAndHighlightElement(lblEmployeeName);
        
        employeeName = lblEmployeeName.getText();
        reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), 
        		"Successfully logged in using " + employeeName + " credentials");
    }
    
	public void clickLogoutButton() {
		switchToDefaultContent();

		waitForElement(ddLogout);
        moveAndHighlightElement(ddLogout);
        ddLogout.click();
        
        waitForElement(btnLogout);
        moveAndHighlightElement(btnLogout);
        btnLogout.click();
		
		waitForElement(modalBtnLogout);
        moveAndHighlightElement(modalBtnLogout);
        modalBtnLogout.click();
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), 
				"Clicked the logout button");
	}
	

}
