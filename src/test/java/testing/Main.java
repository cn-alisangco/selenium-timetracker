package testing;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;

import base.BaseClass;
import io.github.bonigarcia.wdm.WebDriverManager;
import utilities.ExcelReader;
import utilities.TimeParser;

public class Main extends BaseClass{
    private static WebDriver driver;

	
	public static void main(String[] args) {
		/*
		 * WebDriverManager.chromedriver().setup(); ChromeDriver driver = new
		 * ChromeDriver();
		 * 
		 * driver.get("https://testpages.herokuapp.com/basic_html_form.html");
		 * 
		 * WebElement e = driver.findElement(By.name("comments"));
		 */
//		String value = "10:12 am";
//		TimeParser time = new TimeParser(value);
//		System.out.println(time.getHour());
//		System.out.println(time.getMinute());
//		System.out.println(time.getPeriod());
		
		ExcelReader eReader = new ExcelReader(System.getProperty("user.dir") + "\\testData\\Whizible_testing_2.xlsx", "Timesheet_Entry_Select Project");
		System.out.println(eReader.getDataRowSize());
		
		int i = 1;
		while(i <= eReader.getDataRowSize()) {
			System.out.println(eReader.testData(String.valueOf(i), "Time"));
			i++;
		}
		
		i = 1;
		while(i <= eReader.getDataRowSize()) {
			System.out.println(eReader.testData(String.valueOf(i), "Task"));
			i++;
		}
		
		System.out.println(eReader.testData("4", "TestCase"));
//		System.out.println(time.getHour());
//		System.out.println(time.getMinute());
//		System.out.println(time.getPeriod());
		
	}
}
