package pageObjects.timetracker.v2;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import utilities.UserHelper;

public class NavigationBar extends UserHelper{

	WebDriver driver;
	
	//Locators
	@FindBy(id = "topapps") WebElement navBar;
	@FindBy(xpath = "//ul[@id='topapps']/li/a[contains(text(), 'Timesheet')]") WebElement leavesLink;
	
	
	//Constructor
	public NavigationBar(WebDriver driver) {
		this.driver = driver;
	}
	
	public void hoverOverNavBarLink(String linkName) {
		
		String elementXPath = "//ul[@id='topapps']/li/a[contains(text(), '" + linkName + "')]";
		WebElement navBarlink = driver.findElement(By.xpath(elementXPath));
		waitForElement(navBarlink);
		
		moveAndHighlightElement(navBarlink);
		hover(navBarlink);
		//navBarlink.click();
		
		String methodName = "Hover over '" + linkName + "' link from the top navigation bar";
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), methodName);
	}
	
	public void navigateToPage(String sectionName, String subSectionName) {
		
		String elementXPath = "//ul[@id='topapps']/li/a[contains(text(), '" + sectionName + "')]/following-sibling::ul//a[contains(text(), '" + subSectionName + "')]";
		
		//click section
		hoverOverNavBarLink(sectionName);
		
		//click subsection
		WebElement navBarSublink = driver.findElement(By.xpath(elementXPath));
		waitElementToBeClickable(navBarSublink);
		moveAndHighlightElement(navBarSublink);
		
		navBarSublink.click();
		
		String methodName = "Click '" + subSectionName + "' link from the '" + sectionName + "' submenu in the top navigation bar";
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), methodName);
	}
}
