package pageObjects.timetracker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Reporter;

import com.aventstack.extentreports.Status;

import pages.Arch.UserHelper;
import utilities.TestNGListeners;

public class DailyWorkHoursPage extends UserHelper {

	WebDriver driver;
	public String desc;
	public String methodName;
	HomePage homePage = new HomePage(driver);
	
	public static String date;
	private static String taskSelected;
	private static String projectName;
	private static String actualWH;
	private static String workTypeName;
	private static String taskDesc;
	
	By projectDdown = By.xpath("//select[@name=\"SelectedProjectCode\"]");
	By tasksWithLogsBtn = By.xpath("//a[@id=\"todayTask\"]");
	By activeTasksBtn = By.xpath("//a[@id=\"active\"]");
	By allTasksBtn = By.xpath("//*[@id=\"all\"]");
	By generalTasksBtn = By.xpath("//*[@id=\"general\"]");
	By taskName;
	//By taskName = By.xpath("//*[@id=\"taskTableDetails\"]/tbody"); // old
	By workHours = By.xpath("//input[@id=\"logHours\"]");
	By workTypeDdown = By.xpath("//select[@name=\"SelectedDAType\"]");
	By taskDescription = By.xpath("//textarea[@id=\"remarks\"]");
	By saveBtn = By.xpath("//a[@id=\"saveHours\"]");
	By task = By.xpath("//*[@id=\"taskTableDetails\"]/tbody/tr/td[1]/div/div[1]/span");
	By closeBtn = By.xpath("/html/body/div[4]/div[1]/a/span");
	By dwhCloseBtn = By.xpath("/html/body/div[9]/div[1]/a");
	By removeBtn = By.xpath("//td[@class=\"bgGray noborderAll left detailSummary\"]/div[2]/div/span[3]/a");
	By okBtn = By.xpath("/html/body/div[5]/div[3]/div/button[1]/span");
	
	By dailyWorkHrsModal = By.xpath("//div[@id=\"taskLog-modal\"]");
	
	private static String month;
	private static String year;
	
	By monthYearDWH = By.xpath("//table[@id=\"summaryHours\"]/tbody/tr[1]/td[2]");
	By dateDWH = By.xpath("//table[@id=\"summaryHours\"]/tbody/tr[2]/td[2]");
	By dayDWH = By.xpath("//table[@id=\"summaryHours\"]/tbody/tr[3]/td[1]");
	By deliveryMilestoneDdown = By.xpath("//select[@name=\"ddlDMS\"]");
	
	By dwhModal = By.xpath("//div[@class=\"ui-dialog ui-widget ui-widget-content ui-corner-all ui-draggable customUIDialog\"][3]");
	
	private static String deliverable;
	private static String projectTask;

	By allocatedHours;
	
	public DailyWorkHoursPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public void waitModalToAppear() {
		try {
			waitElementToLoad(dwhModal);
		} catch (StaleElementReferenceException e) {
			// TODO Auto-generated catch block
			waitElementToLoad(dwhModal);
			e.printStackTrace();
			}
	}
	
	public void printProjects() {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		desc ="List of projects are displayed.";
		click(projectDdown);
		List<WebElement> projects = selectGetOptions(projectDdown);
		for (int i = 0; i < projects.size(); i++) {
			System.out.println(projects.get(i).getText());
		}
		reportPass(methodName,desc);
		
	}

	public void selectProject(String sheetName,String tcName,String columnName) {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		
		waitModalToAppear();
		
		try {
			waitElementToLoad(projectDdown);
			moveAndHighlightElement(projectDdown);
			//Wait(1000);
			setProjectName(selectByVisibleText(projectDdown,sheetName,tcName,columnName));
			desc = "Selected the Project Name: "+getProjectName()+".";
			reportPass(methodName,desc);
		} catch (StaleElementReferenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
	}
	
	public void clickTasksWithLogs() {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		desc ="Clicked the Tasks with Logs button.";
		waitElementToLoad(tasksWithLogsBtn);
		moveAndHighlightElement(tasksWithLogsBtn);
		//Wait(1000);
		click(tasksWithLogsBtn);
		reportPass(methodName,desc);
	}

	public void clickActiveTasks() {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		desc ="Clicked the Active Tasks button.";
		waitElementToLoad(activeTasksBtn);
		moveAndHighlightElement(activeTasksBtn);
		//Wait(1000);
		click(activeTasksBtn);
		reportPass(methodName,desc);
	}
	
	public void clickAllTasks() {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		desc = "Clicked the All Tasks button.";
		waitElementToLoad(allTasksBtn);
		moveAndHighlightElement(allTasksBtn);
		//Wait(1000);
		click(allTasksBtn);
		reportPass(methodName,desc);
	}
	
	public void clickGeneralTasks() {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		desc = "Clicked the General Tasks button.";
		waitElementToLoad(generalTasksBtn);
		moveAndHighlightElement(generalTasksBtn);
		//Wait(1000);
		click(generalTasksBtn);
		reportPass(methodName,desc);
	}

	public void clickTask(String sheetName,String tcName,String columnName) {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		
		projectTask = getExcelData(sheetName,tcName,columnName);
		setTaskSelected(projectTask);
		desc = "Clicked the Task: "+getTaskSelected()+".";
		taskName = By.xpath("//*[text()=\""+getTaskSelected()+"\"]");
		waitElementToLoad(taskName);
		moveAndHighlightElement(taskName);
		// Wait(2000);
		click(taskName);
		// Thread.sleep(1000);
		// taskSelected = getText(task);
		reportPass(methodName, desc);
	}
	
	public void checkAllocatedHours() {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		allocatedHours = By.xpath("//*[text()=\""+getTaskSelected()+"\"]/ancestor::td[@class='cell-width noborderRight noborderLeft data taskLabel']/following-sibling::td[1]");
		desc = "Successfully verified the allocated hours for "+getTaskSelected()+". Hours: "+getText(allocatedHours);
		waitElementToLoad(allocatedHours);
		moveAndHighlightElement(allocatedHours);
		reportPass(methodName, desc);
	}

	public void enterWorkHours(String sheetName,String tcName,String columnName) {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		
		waitElementToLoad(workHours);
		moveAndHighlightElement(workHours);
		//Wait(1000);
		setActualWH(sendKeys(workHours,sheetName,tcName,columnName));
		desc = "Entered the Work Hours: "+getActualWH()+".";
		reportPass(methodName,desc);
	}

	public void selectWorkType(String sheetName,String tcName,String columnName) {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		waitElementToLoad(workTypeDdown);
		moveAndHighlightElement(workTypeDdown);
		//Wait(1000);
		setWorkTypeName(selectByVisibleText(workTypeDdown,sheetName,tcName,columnName));
		desc = "Selected the Work Type: "+getWorkTypeName()+".";
		reportPass(methodName,desc);
	}

	public void enterTaskDescription(String sheetName,String tcName,String columnName) {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		
		waitElementToLoad(taskDescription);
		moveAndHighlightElement(taskDescription);
		//Wait(1000);
		setTaskDesc(sendKeys(taskDescription,sheetName,tcName,columnName));
		desc = "Entered the Task Description"+getTaskDesc()+".";
		reportPass(methodName,desc);
	}

	public void saveWorkHours() {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		desc = "Saved the work hours.";
		waitElementToLoad(saveBtn);
		click(saveBtn);
		By warningWidget = By.xpath("//div[@class=\"ui-dialog ui-widget ui-widget-content ui-corner-all customUIDialog\"][2]");
		By okBtn = By.xpath("//*[text()=\"Ok\"]");
		
		if (elementExistenceFlag(warningWidget) == true) {
			//Wait(2000);
			waitElementToLoad(okBtn);
			click(okBtn);
			//Wait(1000);
		}
		
		waitElementToLoad(closeBtn);
		click(closeBtn);
		reportPass(methodName,desc);
	}
	
	public void closeDailyWorkHours() {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		desc = "Closed the daily work hours page.";
		By modal = By.xpath("//div[@class=\"ui-dialog ui-widget ui-widget-content ui-corner-all ui-draggable customUIDialog\"][3]");
		sendKeysEsc(modal);
		reportPass(methodName,desc);
		//homePage.waitUntilTaskLogIsDisplayed();
		//homePage.waitToLoadPage();
	}
	
	public void clickRemoveWorkHours() {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		desc = "Clicked remove button.";
		waitElementToLoad(removeBtn);
		moveAndHighlightElement(removeBtn);
		Wait(1000);
		click(removeBtn);
		reportPass(methodName,desc);
		
	}
	
	public void okRemoveWorkHours() {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		desc = "Clicked ok button.";
		waitElementToLoad(okBtn);
		click(okBtn);
		waitElementToLoad(closeBtn);
		click(closeBtn);
//		waitElementToLoad(dwhCloseBtn);
//		click(dwhCloseBtn);
		reportPass(methodName,desc);
		
	}
	
	public void verifyDateMonthYearIsDisplayed() {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		desc = "Successfully verified that correct date is displayed.";
		String expectedMonthYear;
		String expectedDate;
		String actualMonthYear;
		String actualDate;
		
		month = convertMonthToAbbreviation(HomePage.getDateMonth());
		year = HomePage.getDateYear();
		expectedDate = HomePage.getDateNum();
		expectedMonthYear = month + " " + year;
		actualMonthYear = getText(monthYearDWH);
		actualDate = getText(dateDWH);
		
		waitElementToLoad(monthYearDWH);
		moveAndHighlightElement(monthYearDWH);
		waitElementToLoad(dateDWH);
		moveAndHighlightElement(dateDWH);

		// No element we can compare this to
		waitElementToLoad(dayDWH);
		moveAndHighlightElement(dayDWH);
		
		validateText("equal",actualMonthYear,expectedMonthYear);
		validateText("equal",actualDate,expectedDate);
		reportPass(methodName, desc);

	}

	public String convertMonthToAbbreviation(String month) {
		String monthAbbv = "";
		switch (month) {
		case "1":
			monthAbbv = "Jan";
			break;
		case "2":
			monthAbbv = "Feb";
			break;
		case "3":
			monthAbbv = "Mar";
			break;
		case "4":
			monthAbbv = "Apr";
			break;
		case "5":
			monthAbbv = "May";
			break;
		case "6":
			monthAbbv = "Jun";
			break;
		case "7":
			monthAbbv = "Jul";
			break;
		case "8":
			monthAbbv = "Aug";
			break;
		case "9":
			monthAbbv = "Sep";
			break;
		case "10":
			monthAbbv = "Oct";
			break;
		case "11":
			monthAbbv = "Nov";
			break;
		case "12":
			monthAbbv = "Dec";
			break;
		default:
			break;
		}
		
		return monthAbbv;
	}
	
	public void selectDeliverableOrMilestone(String sheetName,String tcName,String columnName) {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		
		
		try {
			waitElementToLoad(deliveryMilestoneDdown);
			moveAndHighlightElement(deliveryMilestoneDdown);
			//Wait(1000);
			deliverable = selectByVisibleText(deliveryMilestoneDdown,sheetName,tcName,columnName);
			desc = "Selected the Deliverable/Milestone: "+deliverable+".";
			reportPass(methodName,desc);
		} catch (StaleElementReferenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		
	}
	
	public void printDeliverableOrMilestone() {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		desc ="List of delivery or milestone are displayed.";
		click(deliveryMilestoneDdown);
		List<WebElement> delivery = selectGetOptions(deliveryMilestoneDdown);
		for (int i = 0; i < delivery.size(); i++) {
			System.out.println(delivery.get(i).getText());
		}
		reportPass(methodName,desc);
		
	}

	public static String getTaskSelected() {
		return taskSelected;
	}

	public static void setTaskSelected(String taskSelected) {
		DailyWorkHoursPage.taskSelected = taskSelected;
	}

	public static String getProjectName() {
		return projectName;
	}

	public static void setProjectName(String projectName) {
		DailyWorkHoursPage.projectName = projectName;
	}

	public static String getActualWH() {
		return actualWH;
	}

	public static void setActualWH(String actualWH) {
		DailyWorkHoursPage.actualWH = actualWH;
	}

	public static String getWorkTypeName() {
		return workTypeName;
	}

	public static void setWorkTypeName(String workTypeName) {
		DailyWorkHoursPage.workTypeName = workTypeName;
	}

	public static String getTaskDesc() {
		return taskDesc;
	}

	public static void setTaskDesc(String taskDesc) {
		DailyWorkHoursPage.taskDesc = taskDesc;
	}
	


}
