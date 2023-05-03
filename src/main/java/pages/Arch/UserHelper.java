package pages.Arch;

import static org.testng.Assert.ARRAY_MISMATCH_TEMPLATE;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import org.testng.Assert;
import org.testng.Reporter;

import com.aventstack.extentreports.MediaEntityBuilder;

import base.BaseClass;
import jdk.jfr.Timespan;
import utilities.ReadExcelData;
import utilities.TestNGListeners;

public class UserHelper extends ReadExcelData {

	BaseClass bcObj = new BaseClass();

	// WebDriver driver = BaseClass.driver;
	WebDriver driver = bcObj.getDriver();
	public static Select selectObj;
	public static LocalDateTime now = LocalDateTime.now();
	public static String dt = now.toString().replace(":", ".");
	public static String SrcBase64String;
	public static String scrFilePath = System.getProperty("user.dir") + "\\extentReports\\screenshots\\";
	public static String scrFileWithPath;
	public static WebDriverWait wait;
	public static String highlighter = "arguments[0].style.border='2px solid blue'";
	public String scroll = "arguments[0].scrollIntoView(true);";
	String style = "";
	public JavascriptExecutor jse;

	// TestNGListeners listener = new TestNGListeners();

	// Driver Commands and Actions------------------------------------------
	public void click(By locator) {
		WebElement element = driver.findElement(locator);
		element.click();
		//Wait(1000);
	}

	public String sendKeys(By locator, String sheetName, String tcName, String columnName) {
		WebElement element = driver.findElement(locator);
		String value = getExcelData(sheetName, tcName, columnName);
		element.click();
		element.sendKeys(value);
		//Wait(1000);
		return value;
	}

	public String selectByVisibleText(By locator, String sheetName, String tcName, String columnName) {
		WebElement element = driver.findElement(locator);
		String value = getExcelData(sheetName, tcName, columnName);
		selectObj = new Select(element);
		selectObj.selectByVisibleText(value);
		Wait(200);
		return value;
	}

	public String selectByValue(By locator, String sheetName, String tcName, String columnName) {
		WebElement element = driver.findElement(locator);
		String value = getExcelData(sheetName, tcName, columnName);
		selectObj = new Select(element);
		selectObj.selectByValue(value);
		Wait(200);
		return value;
	}
	
	public List<WebElement> selectGetOptions(By locator) {
		WebElement element = driver.findElement(locator);
		selectObj = new Select(element);
		return selectObj.getOptions();
	}
	

	public void doubleClick(By locator) {
		WebElement element = driver.findElement(locator);
		Actions actions = new Actions(driver);
		actions.moveToElement(element).doubleClick().build().perform();
	}

	public void hover(By locator) {
		WebElement element = driver.findElement(locator);
		Actions actions = new Actions(driver);
		actions.moveToElement(element).build().perform();
	}

	public void clear(By locator) {
		WebElement element = driver.findElement(locator);
		element.clear();
		Wait(200);
	}

	public String getText(By locator) {
		WebElement element = driver.findElement(locator);
		String text = element.getText();
		Wait(200);
		return text;

	}
	
	public String getAttribute(By locator) {
		WebElement element = driver.findElement(locator);
		String text = element.getAttribute("value");
		Wait(200);
		return text;
		
	}

	// Page Title Validation-----------------------------------------------
	public void validateTitle(String actualTitle, String expectedTitle) {
		Assert.assertEquals(actualTitle, expectedTitle);
	}

	// Text Validation-----------------------------------------------------
	public void validateText(String keyword, String actualText, String expectedText) { //(String keyword, By actualLocator, String actualText, String expectedText)
		switch (keyword.toUpperCase()) {

		case "EQUAL":
			//WebElement element = driver.findElement(actualLocator);
			Assert.assertEquals(actualText, expectedText);
			break;
		case "CONTAINS":
			Assert.assertTrue(actualText.contains(expectedText));
		}
	}

	// Windows-------------------------------------------------------------
	public String setParentWindow() {
		String parentWindow = driver.getWindowHandle();
		return parentWindow;
	}

	public Set<String> getWindowHandles() {
		Set<String> windowHandles = driver.getWindowHandles();
		return windowHandles;
	}

	public void switchToChildWindow(String parentWindow, Set<String> windowHandles) {
		try {
			Set<String> windows = windowHandles;
			Iterator<String> iterator = windows.iterator();
			while (iterator.hasNext()) {
				String childWindow = iterator.next();
				if (!parentWindow.equalsIgnoreCase(childWindow)) {
					driver.switchTo().window(childWindow);
				}
			}
		} catch (Exception e) {
			System.out.println("Unable to switch to child window. " + e.getStackTrace());
		}
	}

	public void switchToParentWindow(Set<String> windowHandles) {
		try {
			Set<String> windows = windowHandles;
			List<Object> windowsList = Arrays.asList(windows.toArray());
			driver.switchTo().window(windowsList.get(0).toString());
		} catch (Exception e) {
			System.out.println("Unable to switch to parent window. " + e.getStackTrace());
		}
	}

	// Frames--------------------------------------------------------------
	public void switchToDefaultContent() {
		try {
			driver.switchTo().defaultContent();
			Wait(1000);
		} catch (NoSuchFrameException e) {
			System.out.println("Unable to locate frame. " + e.getStackTrace());

		} catch (Exception e) {
			System.out.println("Unable to locate frame. " + e.getStackTrace());
		}
	}

	public void switchToParentFrame() {
		try {
			driver.switchTo().parentFrame();
			Wait(1000);
		} catch (NoSuchFrameException e) {
			System.out.println("Unable to locate frame. " + e.getStackTrace());

		} catch (Exception e) {
			System.out.println("Unable to locate frame. " + e.getStackTrace());
		}
	}

	public void switchToFrameByIndex(int index) {

		try {
			driver.switchTo().frame(index);
			Wait(1000);
		} catch (NoSuchFrameException e) {
			System.out.println("Unable to locate frame. " + e.getStackTrace());

		} catch (Exception e) {
			System.out.println("Unable to locate frame. " + e.getStackTrace());
		}
	}

	public void switchToFrameByNameorID(String value) {
		try {
			driver.switchTo().frame(value);
			Wait(1000);
		} catch (NoSuchFrameException e) {
			System.out.println("Unable to locate frame. " + e.getStackTrace());

		} catch (Exception e) {
			System.out.println("Unable to locate frame. " + e.getStackTrace());
		}
	}

	public void switchToFrameByLocator(By locator) {
		try {
			WebElement frameElement = driver.findElement(locator);
			if (frameElement.isDisplayed()) {
				driver.switchTo().frame(frameElement);
				Wait(1000);
			} else {
				System.out.println("Unable to locate frame.");
			}
		} catch (NoSuchFrameException e) {
			System.out.println("Unable to locate frame ." + e.getStackTrace());
		} catch (StaleElementReferenceException e) {
			System.out.println("Element with is not attached to the page document. " + e.getStackTrace());
		} catch (Exception e) {
			System.out.println("Unable to locate to frame. " + e.getStackTrace());
		}
	}

	// Alerts--------------------------------------------------------------
	public boolean isAlertPresent() {

		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public void acceptAlert() {

		boolean alert = false;
		wait = new WebDriverWait(driver, Duration.ofSeconds(5));

		try {
			wait.until(ExpectedConditions.alertIsPresent());
			alert = true;
		} catch (TimeoutException e) {
			Reporter.log("No Alert present!");
			alert = false;
		}

		if (alert == true) {
			driver.switchTo().alert().accept();
		} else {
			return;
		}

		waitToLoadPage();
	}

	public void dismissAlert() {
		boolean alert = false;
		wait = new WebDriverWait(driver, Duration.ofSeconds(5));

		try {
			wait.until(ExpectedConditions.alertIsPresent());
			alert = true;
		} catch (TimeoutException e) {
			Reporter.log("No Alert present!");
			alert = false;
		}

		if (alert == true) {
			driver.switchTo().alert().dismiss();
		} else {
			return;
		}

		waitToLoadPage();
	}

	public String getAlertMessage() {
		boolean alert = false;
		wait = new WebDriverWait(driver, Duration.ofSeconds(5));

		try {
			wait.until(ExpectedConditions.alertIsPresent());
			alert = true;
		} catch (TimeoutException e) {
			Reporter.log("No Alert present!");
			alert = false;
		}

		if (alert == true) {
			return driver.switchTo().alert().getText();

		} else {
			return "No Alert Message!";
		}
	}

	public void setAlertText(String sheetName, String tcName, String columnName) {
		String value = getExcelData(sheetName, tcName, columnName);
		boolean alert = false;
		wait = new WebDriverWait(driver, Duration.ofSeconds(5));

		try {
			wait.until(ExpectedConditions.alertIsPresent());
			alert = true;
		} catch (TimeoutException e) {
			Reporter.log("No Alert present!");
			alert = false;
		}

		if (alert == true) {
			driver.switchTo().alert().sendKeys(value);
		} else {
			return;
		}

		waitToLoadPage();
	}

	// Move and Highlight---------------------------------------------------
	public void moveAndHighlightElement(By locator) {
		WebElement element = driver.findElement(locator);
		Actions actions = new Actions(driver);
		jse = (JavascriptExecutor) driver;
		actions.moveToElement(element).build().perform();
		jse.executeScript(scroll, element);
		jse.executeScript(highlighter, element);
	}

	// Wait-----------------------------------------------------------------
	public void pageLoadTimeout(Duration duration) {
		driver.manage().timeouts().pageLoadTimeout(duration);
	}
	
	public void waitElementToLoad(By locator) {
		waitToLoadPage();
		WebElement element = driver.findElement(locator);

		while (!element.isDisplayed()) {
			try {
				if (element.isDisplayed()) {
					break;
				}
			} catch (NoSuchElementException ex) {
				wait = new WebDriverWait(driver, Duration.ofSeconds(120)); // you can modify the time here
				wait.until(ExpectedConditions.visibilityOf(element));
			}
		}
	}

	public void waitToLoadPage() {
		jse = (JavascriptExecutor) driver;

		String state = jse.executeScript("return document.readyState;").toString();

		while (!state.equalsIgnoreCase("complete")) {
			try {
				if (state.equalsIgnoreCase("complete")) {
					break;
				} else {
					//driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS); // you can modify the time here
					pageLoadTimeout(Duration.ofSeconds(120));
				}
			} catch (Exception ex) {
				driver.navigate().refresh();
			}
		}
	}

	public static void Wait(int ms) {
		try {
			Thread.sleep(ms);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void waitElementToBeClickable(By locator) {
		WebElement element = driver.findElement(locator);
		wait = new WebDriverWait(driver, Duration.ofSeconds(120)); // you can modify the time here
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}
	
	public void waitElementToBeInvinsible(By locator) {
		//WebElement element = 
		driver.findElement(locator);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, Duration.ofSeconds(5)); // you can modify the time here
		wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));

	}
	//Newly Added
	
	public void actionsClick(By locator) {
		WebElement element = driver.findElement(locator);
		Actions actions = new Actions(driver);
		actions.moveToElement(element).click().build().perform();;
	}
	
	public void actionsSendKeys(By locator, String sheetName, String tcName, String columnName) {
		WebElement element = driver.findElement(locator);
		String value = getExcelData(sheetName, tcName, columnName);
		Actions actions = new Actions(driver);
		actions.sendKeys(element, value).build().perform();
		
	}
	
	public void sendKeysEsc(By locator) {
		WebElement element = driver.findElement(locator);
		element.click();
		element.sendKeys(Keys.ESCAPE);
	}
	
	public void sendKeysCtrlADelete(By locator) {
		WebElement element = driver.findElement(locator);
		element.click();
		element.sendKeys(Keys.CONTROL + "A" + Keys.BACK_SPACE);
	}

	public void uploadaFile(String sheetName, String tcName, String columnName) {

		try {
			String filePath = System.getProperty("user.dir") + "\\sikuli_snap\\";
			String inputFilePath = System.getProperty("user.dir") + "\\testData\\";
			String fileName = getExcelData(sheetName, tcName, columnName);

			Screen s = new Screen();
			Pattern fileInputTextBox = new Pattern(filePath + "FileTextBox.png");
			Pattern openButton = new Pattern(filePath + "OpenButton.png");
			s.wait(fileInputTextBox, 30);
			s.type(fileInputTextBox, inputFilePath + fileName);
			s.click(openButton);
		} catch (FindFailed e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	// newly added 05/27
	public String getDataFromExcel(String sheetName, String tcName, String columnName) {
		String value = getExcelData(sheetName, tcName, columnName);
		//Wait(1000);
		return value;
		
	}

//	newly added 11/24
	public boolean elementExistenceFlag(By locator){
		waitToLoadPage();
		//waitElementToLoad(locator);
		WebElement element = driver.findElement(locator);
		return element.isDisplayed();
	}

//	newly added 12/16
	public String getValueAttribute(By locator){
		WebElement element = driver.findElement(locator);
		return element.getAttribute("value");
	}

	// ReportLogs-----------------------------------------------------------
	public void reportPass(String methodName, String desc) {

		// Added
		SrcBase64String = "";
		String SrcBase64 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
		SrcBase64String = SrcBase64;

		// copies screenshot to destLoc
		scrFileWithPath = scrFilePath + methodName + "_" + dt + ".png";
		File SrcFile = OutputType.FILE.convertFromBase64Png(SrcBase64);
		File DestFile = new File(scrFileWithPath);
		try {
			FileUtils.copyFile(SrcFile, DestFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*try {
			TestNGListeners.extentTest.get().pass(desc,
					MediaEntityBuilder.createScreenCaptureFromBase64String(SrcBase64String).build());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		TestNGListeners.extentTest.get().pass(desc,
				MediaEntityBuilder.createScreenCaptureFromBase64String(SrcBase64String).build());

		Reporter.log(desc);

	}
	
	public void reportFail(String methodName, String desc) {
		// Added
//				SrcBase64String = "";
//				String SrcBase64 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
//				SrcBase64String = SrcBase64;
//
//				// copies screenshot to destLoc
//				scrFileWithPath = scrFilePath + methodName + "_" + dt + ".png";
//				File SrcFile = OutputType.FILE.convertFromBase64Png(SrcBase64);
//				File DestFile = new File(scrFileWithPath);
//				try {
//					FileUtils.copyFile(SrcFile, DestFile);
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				
//				TestNGListeners.extentTest.get().fail(desc,
//						MediaEntityBuilder.createScreenCaptureFromBase64String(SrcBase64String).build());
//
//				//Reporter.log(desc);
				
				org.testng.Assert.fail(desc);

		
	}

}
