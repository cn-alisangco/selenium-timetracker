package base;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {

	public static ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();
	public static String category;
	public static String testDataLoc;
	
	public WebDriver getDriver() {
        //Get driver from ThreadLocalMap
        return driver.get();
    }
	
	public void setBrowserZoom() {
		JavascriptExecutor jse = (JavascriptExecutor)getDriver();
		jse.executeScript("document.body.style.zoom = '100%';");
	}

	@Parameters({"categoryXML", "browser", "url", "td"})//These parameters can be configured in testng_Agile.xml
	@BeforeClass
	public void invokeBrowser(String categoryXML, String browser, String url, String td) {
		category = categoryXML; // this will be called by TestNGListeners.java to assign the category
		String actualBrowser = browser;
		String baseURL = url;
		testDataLoc = td;
		
		switch (actualBrowser.toLowerCase()) {
			case "chrome":
				WebDriverManager.chromedriver().setup();
				driver.set(new ChromeDriver());
				break;
			case "firefox":
				WebDriverManager.firefoxdriver().setup();
				driver.set(new FirefoxDriver());
				break;
			case "ie":
				WebDriverManager.iedriver().setup();
				driver.set(new InternetExplorerDriver());
				break;
			case "edge":
				WebDriverManager.edgedriver().setup();
				driver.set(new EdgeDriver());
				break;
			}
		
		getDriver().get(baseURL);
		getDriver().manage().window().maximize();
		
		// SETS BROWSER ZOOM TO 100%
		JavascriptExecutor jse = (JavascriptExecutor)getDriver();
		jse.executeScript("document.body.style.zoom = '100%';");
		
		getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
	}

//	@AfterClass(alwaysRun=true)
//	public void tearDown() {
//		getDriver().close();
//		getDriver().quit();
//	}
}