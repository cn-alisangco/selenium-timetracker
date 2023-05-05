package pageObjects.timetracker.v2;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.hotkey.Keys;
import org.testng.Assert;
import org.testng.Reporter;

import com.aventstack.extentreports.Status;

import utilities.UserHelper;
import utilities.ReadExcelData;
import utilities.TestNGListeners;

public class HomePage extends UserHelper {

	WebDriver driver;
	
	public static String expectedUsername;
	public static String actualUsername;

	public static String SrcBase64 = null;
//	public static String filePath;

	private static String dateRow;
	private static String actualTimeInInfo;
	private static String expectedTimeInInfo;
	private static String actualTimeOutInfo;
	private static String expectedTimeOutInfo;
	private static String actualTITOOverrideMessage;
	private static String expectedTITOOverrideMessage;

	String expectedElapsedTime;
	String expectedElapsedTimeHr;
	String expectedElapsedTimeMin;

	String actualElapsedTime;
	String actualReason;

	int timeInHrLength;
	int timeOutHrLength;
	String timeInHrIndex0;
	String timeOutHrIndex0;
	String timeInHrIndex1;
	String timeOutHrIndex1;

	int num;
	int elapsedTimeHr;
	int elapsedTimeMin;
	int convertedInHr;
	int InHR;
	int convertedInMin;
	int convertedOutHr;
	int OutHR;
	int convertedOutMin;

	String expectedProjectName;
	String actualProjectName;

	String expectedTask;
	String actualTask;

	String expectedWorkHours;
	String actualWorkHours;

	String expectedWorkType;
	String actualWorkType;

	String expectedTaskDesc;
	String actualTaskDesc;

//	By loggedInUser = By.xpath("//*[@id=\"user\"]/a[1]");
//	By logOutBtn = By.xpath("//a[@href=\"/Security/Logout\"]");
//
//	// By timeSheetPeriodDropDown = By.id("TSPeriod");
//	By timeSheetPeriodDropDown = By.xpath("//div[@class=\"jqTransformSelectWrapper\"]");
//
//	By timesheetBtn = By.xpath("//*[@id=\"topapps\"]/li[2]/a");
//	By myTimesheetsBtn = By.xpath("//*[@id=\"topapps\"]/li[2]/ul/li[1]/a");
//
//	By shiftsBtn = By.xpath("//*[@id=\"topapps\"]/li[3]/a");
//	By shiftsTypeBtn = By.xpath("//*[@id=\"topapps\"]/li[3]/ul/li[1]/a");
//
//	By leavesBtn = By.xpath("//*[@id=\"topapps\"]/li[4]/a");
//	By leaveBalancesBtn = By.xpath("//*[@id=\"topapps\"]/li[4]/ul/li[1]");
//
//	By monthlyCreditingBtn = By.xpath("//*[@id=\"topapps\"]/li[5]/a");
//	By monthlyCreditingHistoryBtn = By.xpath("//*[@id=\"topapps\"]/li[5]/ul/li");
//
//	By reportsBtn = By.xpath("//*[@id=\"topapps\"]/li[6]/a");
//	By leaveSOABtn = By.xpath("//*[@id=\"topapps\"]/li[6]/ul/li");
	
    @FindBy(xpath="//*[@id=\"user\"]/a[1]")
    WebElement loggedInUser;
    @FindBy(xpath="//a[@href=\"/Security/Logout\"]")
    WebElement btnLogout;
    
    @FindBy(xpath="//div[@class=\"jqTransformSelectWrapper\"]")
    WebElement dropdownTimeSheetPeriod;
    
    @FindBy(xpath="//*[@id=\"topapps\"]/li[2]/a")
    WebElement btnTimesheet;
    @FindBy(xpath="//*[@id=\"topapps\"]/li[2]/ul/li[1]/a")
    WebElement btnMyTimesheets;
    
    @FindBy(xpath="//*[@id=\"topapps\"]/li[3]/a")
    WebElement btnShifts;
    @FindBy(xpath="//*[@id=\"topapps\"]/li[3]/ul/li[1]/a")
    WebElement btnShiftsType;
    
    @FindBy(xpath="//*[@id=\"topapps\"]/li[4]/a")
    WebElement btnLeaves;
    @FindBy(xpath="//*[@id=\"topapps\"]/li[4]/ul/li[1]")
    WebElement btnLeaveBalances;
    
    @FindBy(xpath="//*[@id=\"topapps\"]/li[5]/a")
    WebElement btnMonthlyCrediting;
    @FindBy(xpath="//*[@id=\"topapps\"]/li[5]/ul/li")
    WebElement btnMonthlyCreditingHistory;
    
    @FindBy(xpath="//*[@id=\"topapps\"]/li[6]/a")
    WebElement btnReports;
    @FindBy(xpath="//*[@id=\"topapps\"]/li[6]/ul/li")
    WebElement btnLeaveSOA;
	
	By remarksColPath;
	public static String remarksColPathStr = "//*[@id='counter']/td[10]";

	By timeInDetails;
	By timeOutDetails;
	By reasonTITOOverride;
	By taskLog;

	public List<WebElement> weekdayDates;
	ArrayList<String> dateAL = new ArrayList<String>();

	private static ArrayList<String> dateRowAL = new ArrayList<String>();
	ArrayList<String> dateWithTimeLogsAL = new ArrayList<String>();
	ArrayList<String> timeInAL = new ArrayList<String>();
	ArrayList<String> timeOutAL = new ArrayList<String>();
	ArrayList<String> reasonAL = new ArrayList<String>();

	private static String dateSplit;
	private static String dateNum;
	private static String dateRowNum;
	private static String dateMonth;
	private static String dateYear;
	private static String date;

	By whizHoursBtn;
	By project;
	By task;
	By workHours;
	By workType;
	By description;
	
	By container = By.xpath("//div[@id=\"container\"]");

	public HomePage(WebDriver driver) {
		this.driver = driver;
	}

	public void verifySuccessfulLogin() {
//		expectedUsername = LoginPage.getUserName();
		actualUsername = loggedInUser.getText();
		waitForElement(loggedInUser);
		moveAndHighlightElement(loggedInUser);
		validateText("equal", actualUsername, expectedUsername);
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Successfully logged in using " + actualUsername + " credentials.");
	}

	public void selectTimeSheetPeriod(String sheetName, String tcName, String columnName) {
		waitForElement(dropdownTimeSheetPeriod);
		dropdownTimeSheetPeriod.click();;
		String timeSheetPeriod = getDataFromExcel(sheetName, tcName, columnName);
		By tsPeriod = By.xpath("//*[text() = \"" + timeSheetPeriod + "\"]");
		actionsClick(tsPeriod);
		moveAndHighlightElement(dropdownTimeSheetPeriod);
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Selected " + timeSheetPeriod + " time sheet period.");
	}

	public int getTotalDates() {
		String dateRows = "//*[@class=\"width2 selectDate\"]";
		List<WebElement> dates = driver.findElements(By.xpath(dateRows));
		setDateArrayList(dates);
		return dates.size();
	}

	public void setDateArrayList(List<WebElement> dates) {
		dateAL.clear();
		// iterate list, get text value and store in array list
		for (int i = 0; i < dates.size(); i++) {
			dateAL.add(dates.get(i).getText());
		}
	}

	public String getDate(String sheetName, String tcName, String columnName) {
		// String date = getDataFromExcel(sheetName, tcName, columnName);
		date = getDataFromExcel(sheetName, tcName, columnName);
		return date;
	}

	public String checkIfDateExists(String date) {
		getTotalDates();
		String status = "No";
		for (int j = 0; j < dateAL.size(); j++) {
			if (dateAL.contains(date)) {
				status = "Yes";
			}
		}
		return status;
	}

	public void clickDate(String date) {
		By selectedDate = By.linkText(date);
		waitElementToBeClickable(selectedDate);
//		moveAndHighlightElement(selectedDate);
		actionsClick(selectedDate);
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Clicked the " + date + " date.");
	}

//	public void verifyTimeLogs() {
//
//		verifyTimeInDetails();
//
//		verifyTimeOutDetails();
//
//		verifyReasonTITOOverride();
//
//		EditTimeLogsPage.clearArrayList();
//	}
//
//	public void verifyTimeInDetails() {
//		getTimeInArrayList();
//		getDateRowArrayList();
//		getDateWithTimeLogs();
//
//		for (int i = 0; i < dateRowAL.size(); i++) {
//			timeInDetails = By.xpath("//tr[@id=\"" + dateRowAL.get(i) + "\"]/td[4]");
//			actualTimeInInfo = getText(timeInDetails);
//			expectedTimeInInfo = timeInAL.get(i);
//			waitElementToLoad(timeInDetails);
//			moveAndHighlightElement(timeInDetails);
//
//			validateText("equal", actualTimeInInfo, expectedTimeInInfo);
//			reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Successfully verified Time In Details of Date " + dateWithTimeLogsAL.get(i) + ".");
//		}
//	}
//
//	public void verifyTimeOutDetails() {
//		getTimeOutArrayList();
//		getDateRowArrayList();
//		getDateWithTimeLogs();
//
//		for (int i = 0; i < dateRowAL.size(); i++) {
//			timeOutDetails = By.xpath("//tr[@id=\"" + dateRowAL.get(i) + "\"]/td[5]");
//			actualTimeOutInfo = getText(timeOutDetails);
//			expectedTimeOutInfo = timeOutAL.get(i);
//			waitElementToLoad(timeOutDetails);
//			moveAndHighlightElement(timeOutDetails);
//
//			validateText("equal", actualTimeOutInfo, expectedTimeOutInfo);
//			reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Successfully verified Time Out Details of Date " + dateWithTimeLogsAL.get(i) + ".");
//		}
//	}
//
//	public void verifyReasonTITOOverride() {
//		getDateRowArrayList();
//		getDateWithTimeLogs();
//		getReasonOverrideArrayList();
//
//		for (int i = 0; i < dateRowAL.size(); i++) {
//			reasonTITOOverride = By.xpath("//tr[@id=\"" + dateRowAL.get(i) + "\"]/td[10]");
//			actualTITOOverrideMessage = getText(reasonTITOOverride);
//			waitElementToLoad(reasonTITOOverride);
//			moveAndHighlightElement(reasonTITOOverride);
//			expectedTITOOverrideMessage = "TITO Reason: " + reasonAL.get(i);
//
//			validateText("equal", actualTITOOverrideMessage, expectedTITOOverrideMessage);
//			reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Successfully verified Reason for TITO Override of Date " + dateWithTimeLogsAL.get(i) + ".");
//		}
//	}
//
//	public void verifyDetailsAreLeftBlanked() {
//		getDateRowArrayList();
//		getDateWithTimeLogs();
//		for (int i = 0; i < dateRowAL.size(); i++) {
//			timeInDetails = By.xpath("//tr[@id=\"" + dateRowAL.get(i) + "\"]/td[4]");
//			timeOutDetails = By.xpath("//tr[@id=\"" + dateRowAL.get(i) + "\"]/td[5]");
//			reasonTITOOverride = By.xpath("//tr[@id=\"" + dateRowAL.get(i) + "\"]/td[10]");
//
//			waitElementToLoad(timeInDetails);
//			moveAndHighlightElement(timeInDetails);
//
//			waitElementToLoad(timeOutDetails);
//			moveAndHighlightElement(timeOutDetails);
//
//			waitElementToLoad(reasonTITOOverride);
//			moveAndHighlightElement(reasonTITOOverride);
//
//			expectedTimeInInfo = "";
//			expectedTimeOutInfo = "";
//			expectedTITOOverrideMessage = "";
//
//			actualTimeInInfo = getText(timeInDetails);
//			actualTimeOutInfo = getText(timeOutDetails);
//			actualTITOOverrideMessage = getText(reasonTITOOverride);
//
//			validateText("equal", actualTimeInInfo, expectedTimeInInfo);
//			validateText("equal", actualTimeOutInfo, expectedTimeOutInfo);
//			validateText("equal", actualTITOOverrideMessage, expectedTITOOverrideMessage);
//
//			reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Successfully verified Time log details are left blanked for Date " + dateWithTimeLogsAL.get(i)
//			+ ".");
//		}
//
//	}
//
//	public void clickLogout() {
//		waitElementToLoad(logOutBtn);
//		// moveAndHighlightElement(logOutBtn);
//		Wait(1000);
//		click(logOutBtn);
//		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Clicked the Logout button.");
//		EditTimeLogsPage.clearArrayList();
//	}
//
//	public void clickMyTimesheets() {
//		hover(timesheetBtn);
//		Wait(1000);
//		click(myTimesheetsBtn);
//		reportPass(methodName, desc);
//		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Clicked My Timesheets button");
//	}
//
//	public void clickShiftTypes() {
//		hover(shiftsBtn);
//		Wait(1000);
//		click(shiftsTypeBtn);
//		reportPass(methodName, desc);
//		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Clicked Shift Types button");
//	}
//
//	public void clickLeavesBalances() {
//		hover(leavesBtn);
//		Wait(1000);
//		click(leaveBalancesBtn);
//		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Clicked Balances button");
//	}
//
//	public void clickMonthlyCreditingHistory() {
//		hover(monthlyCreditingBtn);
//		Wait(1000);
//		click(monthlyCreditingHistoryBtn);
//		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Clicked Monthly Crediting History button");
//	}
//
//	public void clickLeaveSOA() {
//		hover(reportsBtn);
//		Wait(1000);
//		click(leaveSOABtn);
//		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Clicked Leave SOA button");
//	}
//
//	public void dateNotFound() {
//		reportFail(Thread.currentThread().getStackTrace()[1].getMethodName(), "Date was not in the time period selected. ");
//	}
//
//	public void getTimeInArrayList() {
//		timeInAL = EditTimeLogsPage.getTimeInAL();
//	}
//
//	public void getTimeOutArrayList() {
//		timeOutAL = EditTimeLogsPage.getTimeOutAL();
//	}
//
//	public void getReasonOverrideArrayList() {
//		reasonAL = EditTimeLogsPage.getReasonOverrideAL();
//	}
//
//	public void getDateRowArrayList() {
//		dateRowAL = EditTimeLogsPage.getDateRowAL();
//	}
//
//	public void getDateWithTimeLogs() {
//		dateWithTimeLogsAL = EditTimeLogsPage.getDateWithTimeLogsAL();
//	}
//
//	public void clickFileALeave(By locator) {
//		waitElementToLoad(locator);
//		moveAndHighlightElement(locator);
//		hover(locator);
//		click(locator);
//		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "User clicked the File A Leave button");
//	}
//
//	public boolean verifyFileALeaveIsAvailable(By locator) {
//		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Successfully verified the existence of File A Leave action");
//		return elementExistenceFlag(locator);
//	}
//
//	public boolean verifyRemarksCol(By locator) {
//		if (!getText(locator).equals("")) {
//			desc = ;
//			waitElementToLoad(locator);
//			moveAndHighlightElement(locator);
//			reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Successfully verified that the Remarks column contains a text");
//			return false;
//		}
//		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Successfully verified that the Remarks column is empty");
//		return true;
//	}
//
//	public void clickWhizHours(String sheetName, String tcName, String columnName) {
//		// String date = getExcelData(sheetName,tcName,columnName);
//		date = getExcelData(sheetName, tcName, columnName);
//		dateSplit = date;
//		String[] dateMonthDateYearSplit = dateSplit.split("/");
//		setDateMonth(dateMonthDateYearSplit[0]);
//		setDateNum(dateMonthDateYearSplit[1]);
//		setDateYear(dateMonthDateYearSplit[2]);
//		setDateRowNum(0 + getDateNum());
//
//		whizHoursBtn = By.xpath("//tr[@id=\"" + getDateRowNum() + "\"]/td[8]/div/a[2]");
//
//		try {
//			waitElementToLoad(whizHoursBtn);
//			moveAndHighlightElement(whizHoursBtn);
//			click(whizHoursBtn);
//		} catch (org.openqa.selenium.StaleElementReferenceException ex) {
//			waitElementToLoad(whizHoursBtn);
//			moveAndHighlightElement(whizHoursBtn);
//			click(whizHoursBtn);
//		}
//		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Clicked the Whiz Hours button for date " + date);
//
//	}
//	
//	public void hoverToWhizHoursButton(String sheetName, String tcName, String columnName) {
//		date = getExcelData(sheetName, tcName, columnName);
//		dateSplit = date;
//		String[] dateMonthDateYearSplit = dateSplit.split("/");
//		setDateNum(dateMonthDateYearSplit[1]);
//		setDateRowNum(0 + getDateNum());
//		whizHoursBtn = By.xpath("//tr[@id=\"" + getDateRowNum() + "\"]/td[8]/div/a[2]");
//		waitElementToLoad(whizHoursBtn);
//		moveAndHighlightElement(whizHoursBtn);
//		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Hovered to the Whiz Hours button for date " + date;);
//	}
//
//	public void clickShowTaskLog() {
//		taskLog = By.xpath("//tr[@id=\"" + getDateRowNum() + "\"]/td[@title=\"Show Task Logs\"]");
//		try {
//			waitElementToLoad(taskLog);
//			moveAndHighlightElement(taskLog);
//			click(taskLog);
//		} catch (org.openqa.selenium.StaleElementReferenceException ex) {
//			waitElementToLoad(taskLog);
//			moveAndHighlightElement(taskLog);
//			click(taskLog);
//		}
//		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Clicked the show task log");
//	}
//
//	public void clickHideTaskLog() {
//		taskLog = By.xpath("//tr[@id=\"" + getDateRowNum() + "\"]/td[@title=\"Hide Task Logs\"]");
//		try {
//			waitElementToLoad(taskLog);
//			moveAndHighlightElement(taskLog);
//			click(taskLog);
//		} catch (org.openqa.selenium.StaleElementReferenceException ex) {
//			waitElementToLoad(taskLog);
//			moveAndHighlightElement(taskLog);
//			click(taskLog);
//		}
//		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Clicked the hide task log");
//	}
//
//	public void waitUntilTaskLogIsDisplayed() {
//		taskLog = By.xpath("//tr[@id=\"" + getDateRowNum() + "\"]/td[@title=\"Show Task Logs\"]");
//		try {
//			waitElementToLoad(taskLog);
//			moveAndHighlightElement(taskLog);
//		} catch (org.openqa.selenium.StaleElementReferenceException ex) {
//			waitElementToLoad(taskLog);
//			moveAndHighlightElement(taskLog);
//		}
//	}
//
//	public void waitUntilTaskLogIsNotDisplayed() {
//		taskLog = By.xpath("//tr[@id=\"" + getDateRowNum() + "\"]/td[@class=\"width2 panelBlank\"]");
//		try {
//			waitElementToLoad(taskLog);
//			moveAndHighlightElement(taskLog);
//		} catch (org.openqa.selenium.StaleElementReferenceException ex) {
//			waitElementToLoad(taskLog);
//			moveAndHighlightElement(taskLog);
//		}
//	}
//
//	public static String getDateRowNum() {
//		return dateRowNum;
//	}
//
//	public static void setDateRowNum(String dateRowNum) {
//		HomePage.dateRowNum = dateRowNum;
//	}
//
//	public static String getDateNum() {
//		return dateNum;
//	}
//
//	public static void setDateNum(String dateNum) {
//		HomePage.dateNum = dateNum;
//	}
//
//	public static String getDateMonth() {
//		return dateMonth;
//	}
//
//	public static void setDateMonth(String dateMonth) {
//		HomePage.dateMonth = dateMonth;
//	}
//
//	public static String getDateYear() {
//		return dateYear;
//	}
//
//	public static void setDateYear(String dateYear) {
//		HomePage.dateYear = dateYear;
//	}
//
//	public void verifyTaskLogs() {
//		verifyProjectDisplayed();
//		verifyTaskDisplayed();
//		verifyWorkTypeDisplayed();
//		verifyWorkHoursDisplayed();
//		verifyDescriptionDisplayed();
//		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Successfully verified task logs for date " + date);
//	}
//
//	public void verifyProjectDisplayed() {
//		project = By.xpath("//tr[@class=\"tasksContainer\"][" + getDateNum() + "]/td/table/tbody/tr/td[1]");
//		expectedProjectName = DailyWorkHoursPage.getProjectName();
//		actualProjectName = getText(project);
//		waitElementToLoad(project);
//		moveAndHighlightElement(project);
//		validateText("equal", actualProjectName, expectedProjectName);
//	}
//
//	public void verifyTaskDisplayed() {
//		task = By.xpath("//tr[@class=\"tasksContainer\"][" + getDateNum() + "]/td/table/tbody/tr/td[2]");
//		expectedTask = DailyWorkHoursPage.getTaskSelected();
//		actualTask = getText(task);
//		waitElementToLoad(task);
//		moveAndHighlightElement(task);
//		validateText("equal", actualTask, expectedTask);
//	}
//
//	public void verifyWorkTypeDisplayed() {
//		workType = By.xpath("//tr[@class=\"tasksContainer\"][" + getDateNum() + "]/td/table/tbody/tr/td[3]");
//		expectedWorkType = DailyWorkHoursPage.getWorkTypeName();
//		actualWorkType = getText(workType);
//		waitElementToLoad(workType);
//		moveAndHighlightElement(workType);
//		
//		validateText("equal", actualWorkType, expectedWorkType);
//	}
//
//	public void verifyWorkHoursDisplayed() {
//		workHours = By.xpath("//tr[@class=\"tasksContainer\"][" + getDateNum() + "]/td/table/tbody/tr/td[4]");
//		expectedWorkHours = DailyWorkHoursPage.getActualWH();
//		actualWorkHours = getText(workHours);
//		waitElementToLoad(workHours);
//		moveAndHighlightElement(workHours);
//		validateText("equal", actualWorkHours, expectedWorkHours);
//	}
//
//	public void verifyDescriptionDisplayed() {
//		description = By.xpath("//tr[@class=\"tasksContainer\"][" + getDateNum() + "]/td/table/tbody/tr/td[5]");
//		expectedTaskDesc = DailyWorkHoursPage.getTaskDesc();
//		actualTaskDesc = getText(description);
//		waitElementToLoad(description);
//		moveAndHighlightElement(description);
//		validateText("equal", actualTaskDesc, expectedTaskDesc);
//	}
//	
//	public void waitContainerToLoad() {
//		try {
//			waitElementToLoad(container);
//		} catch (StaleElementReferenceException e) {
//			// TODO Auto-generated catch block
//			waitElementToLoad(container);
//			e.printStackTrace();
//			}
//		Wait(1000);
//	}
//	
//	public void verifyLeaveIsSubmitted(By remarks, String leaveType) {
//		waitElementToLoad(remarks);
//		while(getText(remarks).length() == 0) {
//			Wait(1000);
//		}
//		moveAndHighlightElement(remarks);
//		String expectedRemarks = "Submitted " + leaveType;
//		String actualRemarks = getText(remarks);
//		validateText("equal", actualRemarks, expectedRemarks);
//		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Successfully verified leave was submitted.");
//	}

}
