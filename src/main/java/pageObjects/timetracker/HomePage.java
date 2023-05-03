package pageObjects.timetracker;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
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

import pages.Arch.UserHelper;
import utilities.ReadExcelData;
import utilities.TestNGListeners;

public class HomePage extends UserHelper {

	WebDriver driver;
	public String desc;
	public String methodName;
	public static String expectedUsername;
	public static String actualUsername;

	public static String SrcBase64 = null;
	public static String filePath;

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

	By loggedInUser = By.xpath("//*[@id=\"user\"]/a[1]");
	By logOutBtn = By.xpath("//a[@href=\"/Security/Logout\"]");

	// By timeSheetPeriodDropDown = By.id("TSPeriod");
	By timeSheetPeriodDropDown = By.xpath("//div[@class=\"jqTransformSelectWrapper\"]");

	By timesheetBtn = By.xpath("//*[@id=\"topapps\"]/li[2]/a");
	By myTimesheetsBtn = By.xpath("//*[@id=\"topapps\"]/li[2]/ul/li[1]/a");

	By shiftsBtn = By.xpath("//*[@id=\"topapps\"]/li[3]/a");
	By shiftsTypeBtn = By.xpath("//*[@id=\"topapps\"]/li[3]/ul/li[1]/a");

	By leavesBtn = By.xpath("//*[@id=\"topapps\"]/li[4]/a");
	By leaveBalancesBtn = By.xpath("//*[@id=\"topapps\"]/li[4]/ul/li[1]");

	By monthlyCreditingBtn = By.xpath("//*[@id=\"topapps\"]/li[5]/a");
	By monthlyCreditingHistoryBtn = By.xpath("//*[@id=\"topapps\"]/li[5]/ul/li");

	By reportsBtn = By.xpath("//*[@id=\"topapps\"]/li[6]/a");
	By leaveSOABtn = By.xpath("//*[@id=\"topapps\"]/li[6]/ul/li");
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
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		expectedUsername = LoginPage.getUserName();
		actualUsername = getText(loggedInUser);
		desc = "Successfully logged in using " + actualUsername + " credentials.";
		waitElementToLoad(loggedInUser);
		moveAndHighlightElement(loggedInUser);
		validateText("equal", actualUsername, expectedUsername);
		reportPass(methodName, desc);
	}

	public void selectTimeSheetPeriod(String sheetName, String tcName, String columnName) {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		waitElementToLoad(timeSheetPeriodDropDown);
		click(timeSheetPeriodDropDown);
		String timeSheetPeriod = getDataFromExcel(sheetName, tcName, columnName);
		By tsPeriod = By.xpath("//*[text() = \"" + timeSheetPeriod + "\"]");
		click(tsPeriod);
		desc = "Selected " + timeSheetPeriod + " time sheet period.";
		moveAndHighlightElement(timeSheetPeriodDropDown);
		reportPass(methodName, desc);
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
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		By selectedDate = By.linkText(date);
		desc = "Clicked the " + date + " date.";
		waitElementToLoad(selectedDate);
		moveAndHighlightElement(selectedDate);
		click(selectedDate);
		reportPass(methodName, desc);
	}

	public void verifyTimeLogs() {

		verifyTimeInDetails();

		verifyTimeOutDetails();

		verifyReasonTITOOverride();

		EditTimeLogsPage.clearArrayList();
	}

	public void verifyTimeInDetails() {
		getTimeInArrayList();
		getDateRowArrayList();
		getDateWithTimeLogs();
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

		for (int i = 0; i < dateRowAL.size(); i++) {
			timeInDetails = By.xpath("//tr[@id=\"" + dateRowAL.get(i) + "\"]/td[4]");
			actualTimeInInfo = getText(timeInDetails);
			expectedTimeInInfo = timeInAL.get(i);
			waitElementToLoad(timeInDetails);
			moveAndHighlightElement(timeInDetails);

			validateText("equal", actualTimeInInfo, expectedTimeInInfo);
			desc = "Successfully verified Time In Details of Date " + dateWithTimeLogsAL.get(i) + ".";
			reportPass(methodName, desc);
		}
	}

	public void verifyTimeOutDetails() {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		getTimeOutArrayList();
		getDateRowArrayList();
		getDateWithTimeLogs();

		for (int i = 0; i < dateRowAL.size(); i++) {
			timeOutDetails = By.xpath("//tr[@id=\"" + dateRowAL.get(i) + "\"]/td[5]");
			actualTimeOutInfo = getText(timeOutDetails);
			expectedTimeOutInfo = timeOutAL.get(i);
			waitElementToLoad(timeOutDetails);
			moveAndHighlightElement(timeOutDetails);

			validateText("equal", actualTimeOutInfo, expectedTimeOutInfo);
			desc = "Successfully verified Time Out Details of Date " + dateWithTimeLogsAL.get(i) + ".";
			reportPass(methodName, desc);
		}
	}

	public void verifyReasonTITOOverride() {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		getDateRowArrayList();
		getDateWithTimeLogs();
		getReasonOverrideArrayList();

		for (int i = 0; i < dateRowAL.size(); i++) {
			reasonTITOOverride = By.xpath("//tr[@id=\"" + dateRowAL.get(i) + "\"]/td[10]");
			actualTITOOverrideMessage = getText(reasonTITOOverride);
			waitElementToLoad(reasonTITOOverride);
			moveAndHighlightElement(reasonTITOOverride);
			expectedTITOOverrideMessage = "TITO Reason: " + reasonAL.get(i);

			validateText("equal", actualTITOOverrideMessage, expectedTITOOverrideMessage);
			desc = "Successfully verified Reason for TITO Override of Date " + dateWithTimeLogsAL.get(i) + ".";
			reportPass(methodName, desc);
		}
	}

	public void verifyDetailsAreLeftBlanked() {
		getDateRowArrayList();
		getDateWithTimeLogs();
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		for (int i = 0; i < dateRowAL.size(); i++) {
			timeInDetails = By.xpath("//tr[@id=\"" + dateRowAL.get(i) + "\"]/td[4]");
			timeOutDetails = By.xpath("//tr[@id=\"" + dateRowAL.get(i) + "\"]/td[5]");
			reasonTITOOverride = By.xpath("//tr[@id=\"" + dateRowAL.get(i) + "\"]/td[10]");

			waitElementToLoad(timeInDetails);
			moveAndHighlightElement(timeInDetails);

			waitElementToLoad(timeOutDetails);
			moveAndHighlightElement(timeOutDetails);

			waitElementToLoad(reasonTITOOverride);
			moveAndHighlightElement(reasonTITOOverride);

			expectedTimeInInfo = "";
			expectedTimeOutInfo = "";
			expectedTITOOverrideMessage = "";

			actualTimeInInfo = getText(timeInDetails);
			actualTimeOutInfo = getText(timeOutDetails);
			actualTITOOverrideMessage = getText(reasonTITOOverride);

			validateText("equal", actualTimeInInfo, expectedTimeInInfo);
			validateText("equal", actualTimeOutInfo, expectedTimeOutInfo);
			validateText("equal", actualTITOOverrideMessage, expectedTITOOverrideMessage);

			desc = "Successfully verified Time log details are left blanked for Date " + dateWithTimeLogsAL.get(i)
					+ ".";
			reportPass(methodName, desc);
		}

	}

	public void clickLogout() {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		desc = "Clicked the Logout button.";
		waitElementToLoad(logOutBtn);
		// moveAndHighlightElement(logOutBtn);
		Wait(1000);
		click(logOutBtn);
		reportPass(methodName, desc);
		EditTimeLogsPage.clearArrayList();
	}

	public void clickMyTimesheets() {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String desc = "Clicked My Timesheets button";
		hover(timesheetBtn);
		Wait(1000);
		click(myTimesheetsBtn);
		reportPass(methodName, desc);
	}

	public void clickShiftTypes() {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String desc = "Clicked Shift Types button";
		hover(shiftsBtn);
		Wait(1000);
		click(shiftsTypeBtn);
		reportPass(methodName, desc);
	}

	public void clickLeavesBalances() {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String desc = "Clicked Balances button";
		hover(leavesBtn);
		Wait(1000);
		click(leaveBalancesBtn);
		reportPass(methodName, desc);
	}

	public void clickMonthlyCreditingHistory() {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String desc = "Clicked Monthly Crediting History button";
		hover(monthlyCreditingBtn);
		Wait(1000);
		click(monthlyCreditingHistoryBtn);
		reportPass(methodName, desc);
	}

	public void clickLeaveSOA() {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String desc = "Clicked Leave SOA button";
		hover(reportsBtn);
		Wait(1000);
		click(leaveSOABtn);
		reportPass(methodName, desc);
	}

	public void dateNotFound() {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String desc = "Date was not in the time period selected. ";
		reportFail(methodName, desc);
	}

	public void getTimeInArrayList() {
		timeInAL = EditTimeLogsPage.getTimeInAL();
	}

	public void getTimeOutArrayList() {
		timeOutAL = EditTimeLogsPage.getTimeOutAL();
	}

	public void getReasonOverrideArrayList() {
		reasonAL = EditTimeLogsPage.getReasonOverrideAL();
	}

	public void getDateRowArrayList() {
		dateRowAL = EditTimeLogsPage.getDateRowAL();
	}

	public void getDateWithTimeLogs() {
		dateWithTimeLogsAL = EditTimeLogsPage.getDateWithTimeLogsAL();
	}

	public void clickFileALeave(By locator) {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		desc = "User clicked the File A Leave button";
		waitElementToLoad(locator);
		moveAndHighlightElement(locator);
		hover(locator);
		click(locator);
		reportPass(methodName, desc);
	}

	public boolean verifyFileALeaveIsAvailable(By locator) {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		desc = "Successfully verified the existence of File A Leave action";
		reportPass(methodName, desc);
		return elementExistenceFlag(locator);
	}

	public boolean verifyRemarksCol(By locator) {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		if (!getText(locator).equals("")) {
			desc = "Successfully verified that the Remarks column contains a text";
			waitElementToLoad(locator);
			moveAndHighlightElement(locator);
			reportPass(methodName, desc);
			return false;
		}
		desc = "Successfully verified that the Remarks column is empty";
		reportPass(methodName, desc);
		return true;
	}

	public void clickWhizHours(String sheetName, String tcName, String columnName) {
		// String date = getExcelData(sheetName,tcName,columnName);
		date = getExcelData(sheetName, tcName, columnName);
		dateSplit = date;
		String[] dateMonthDateYearSplit = dateSplit.split("/");
		setDateMonth(dateMonthDateYearSplit[0]);
		setDateNum(dateMonthDateYearSplit[1]);
		setDateYear(dateMonthDateYearSplit[2]);
		setDateRowNum(0 + getDateNum());

		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		whizHoursBtn = By.xpath("//tr[@id=\"" + getDateRowNum() + "\"]/td[8]/div/a[2]");

		try {
			waitElementToLoad(whizHoursBtn);
			moveAndHighlightElement(whizHoursBtn);
			click(whizHoursBtn);
		} catch (org.openqa.selenium.StaleElementReferenceException ex) {
			waitElementToLoad(whizHoursBtn);
			moveAndHighlightElement(whizHoursBtn);
			click(whizHoursBtn);
		}
		desc = "Clicked the Whiz Hours button for date " + date;
		reportPass(methodName, desc);

	}
	
	public void hoverToWhizHoursButton(String sheetName, String tcName, String columnName) {
		date = getExcelData(sheetName, tcName, columnName);
		dateSplit = date;
		String[] dateMonthDateYearSplit = dateSplit.split("/");
		setDateNum(dateMonthDateYearSplit[1]);
		setDateRowNum(0 + getDateNum());
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		whizHoursBtn = By.xpath("//tr[@id=\"" + getDateRowNum() + "\"]/td[8]/div/a[2]");
		desc = "Hovered to the Whiz Hours button for date " + date;
		waitElementToLoad(whizHoursBtn);
		moveAndHighlightElement(whizHoursBtn);
		reportPass(methodName, desc);
	}

	public void clickShowTaskLog() {
		taskLog = By.xpath("//tr[@id=\"" + getDateRowNum() + "\"]/td[@title=\"Show Task Logs\"]");
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		desc = "Clicked the show task log";
		try {
			waitElementToLoad(taskLog);
			moveAndHighlightElement(taskLog);
			click(taskLog);
		} catch (org.openqa.selenium.StaleElementReferenceException ex) {
			waitElementToLoad(taskLog);
			moveAndHighlightElement(taskLog);
			click(taskLog);
		}
		reportPass(methodName, desc);
	}

	public void clickHideTaskLog() {
		taskLog = By.xpath("//tr[@id=\"" + getDateRowNum() + "\"]/td[@title=\"Hide Task Logs\"]");
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		desc = "Clicked the hide task log";
		try {
			waitElementToLoad(taskLog);
			moveAndHighlightElement(taskLog);
			click(taskLog);
		} catch (org.openqa.selenium.StaleElementReferenceException ex) {
			waitElementToLoad(taskLog);
			moveAndHighlightElement(taskLog);
			click(taskLog);
		}
		reportPass(methodName, desc);
	}

	public void waitUntilTaskLogIsDisplayed() {
		taskLog = By.xpath("//tr[@id=\"" + getDateRowNum() + "\"]/td[@title=\"Show Task Logs\"]");
		try {
			waitElementToLoad(taskLog);
			moveAndHighlightElement(taskLog);
		} catch (org.openqa.selenium.StaleElementReferenceException ex) {
			waitElementToLoad(taskLog);
			moveAndHighlightElement(taskLog);
		}
	}

	public void waitUntilTaskLogIsNotDisplayed() {
		taskLog = By.xpath("//tr[@id=\"" + getDateRowNum() + "\"]/td[@class=\"width2 panelBlank\"]");
		try {
			waitElementToLoad(taskLog);
			moveAndHighlightElement(taskLog);
		} catch (org.openqa.selenium.StaleElementReferenceException ex) {
			waitElementToLoad(taskLog);
			moveAndHighlightElement(taskLog);
		}
	}

	public static String getDateRowNum() {
		return dateRowNum;
	}

	public static void setDateRowNum(String dateRowNum) {
		HomePage.dateRowNum = dateRowNum;
	}

	public static String getDateNum() {
		return dateNum;
	}

	public static void setDateNum(String dateNum) {
		HomePage.dateNum = dateNum;
	}

	public static String getDateMonth() {
		return dateMonth;
	}

	public static void setDateMonth(String dateMonth) {
		HomePage.dateMonth = dateMonth;
	}

	public static String getDateYear() {
		return dateYear;
	}

	public static void setDateYear(String dateYear) {
		HomePage.dateYear = dateYear;
	}

	public void verifyTaskLogs() {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		desc = "Successfully verified task logs for date " + date;
		verifyProjectDisplayed();
		verifyTaskDisplayed();
		verifyWorkTypeDisplayed();
		verifyWorkHoursDisplayed();
		verifyDescriptionDisplayed();
		reportPass(methodName, desc);
	}

	public void verifyProjectDisplayed() {
		project = By.xpath("//tr[@class=\"tasksContainer\"][" + getDateNum() + "]/td/table/tbody/tr/td[1]");
		expectedProjectName = DailyWorkHoursPage.getProjectName();
		actualProjectName = getText(project);
		waitElementToLoad(project);
		moveAndHighlightElement(project);
		validateText("equal", actualProjectName, expectedProjectName);
	}

	public void verifyTaskDisplayed() {
		task = By.xpath("//tr[@class=\"tasksContainer\"][" + getDateNum() + "]/td/table/tbody/tr/td[2]");
		expectedTask = DailyWorkHoursPage.getTaskSelected();
		actualTask = getText(task);
		waitElementToLoad(task);
		moveAndHighlightElement(task);
		validateText("equal", actualTask, expectedTask);
	}

	public void verifyWorkTypeDisplayed() {
		workType = By.xpath("//tr[@class=\"tasksContainer\"][" + getDateNum() + "]/td/table/tbody/tr/td[3]");
		expectedWorkType = DailyWorkHoursPage.getWorkTypeName();
		actualWorkType = getText(workType);
		waitElementToLoad(workType);
		moveAndHighlightElement(workType);
		
		validateText("equal", actualWorkType, expectedWorkType);
	}

	public void verifyWorkHoursDisplayed() {
		workHours = By.xpath("//tr[@class=\"tasksContainer\"][" + getDateNum() + "]/td/table/tbody/tr/td[4]");
		expectedWorkHours = DailyWorkHoursPage.getActualWH();
		actualWorkHours = getText(workHours);
		waitElementToLoad(workHours);
		moveAndHighlightElement(workHours);
		validateText("equal", actualWorkHours, expectedWorkHours);
	}

	public void verifyDescriptionDisplayed() {
		description = By.xpath("//tr[@class=\"tasksContainer\"][" + getDateNum() + "]/td/table/tbody/tr/td[5]");
		expectedTaskDesc = DailyWorkHoursPage.getTaskDesc();
		actualTaskDesc = getText(description);
		waitElementToLoad(description);
		moveAndHighlightElement(description);
		validateText("equal", actualTaskDesc, expectedTaskDesc);
	}
	
	public void waitContainerToLoad() {
		try {
			waitElementToLoad(container);
		} catch (StaleElementReferenceException e) {
			// TODO Auto-generated catch block
			waitElementToLoad(container);
			e.printStackTrace();
			}
		Wait(1000);
	}
	
	public void verifyLeaveIsSubmitted(By remarks, String leaveType) {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		waitElementToLoad(remarks);
		while(getText(remarks).length() == 0) {
			Wait(1000);
		}
		moveAndHighlightElement(remarks);
		String expectedRemarks = "Submitted " + leaveType;
		String actualRemarks = getText(remarks);
		validateText("equal", actualRemarks, expectedRemarks);
		desc = "Successfully verified leave was submitted.";
		reportPass(methodName, desc);
	}

}
