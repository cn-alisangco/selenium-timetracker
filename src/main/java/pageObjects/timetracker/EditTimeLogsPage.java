package pageObjects.timetracker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Reporter;

import com.aventstack.extentreports.Status;

import userHelper.UserHelper;
import utilities.TestNGListeners;

public class EditTimeLogsPage extends UserHelper {
	WebDriver driver;
	int num;
	public String desc;
	public String methodName;
	
	private static String timeInHr;
	private static String timeInMin;
	private static String timeInDayPart;
	private static String timeInLoc;
	private static String timeOutHr;
	private static String timeOutMin;
	private static String timeOutDayPart;
	private static String timeOutLoc;
	private static String remarks;
	private static String reason;
	private static String expectedErrorMsg = "Time-In cannot be later than or equal to Time-Out";
	private static String expectedMissingOverrideErrorMsg = "Please add reason for manual override.";
	private static String dateOrig;
	private static String dateSplit;
	private static String dateNum;
	private static String dateNumAppend;
	private static String expectedTimeInInfo;
	private static String expectedTimeOutInfo;
	private static int timeInHrLength;
	private static int timeOutHrLength;
	private static String timeInHrIndex0;
	private static String timeOutHrIndex0;
	private static String timeInHrIndex1;
	private static String timeOutHrIndex1;
	private static ArrayList<String> dateRowAL = new ArrayList<String>();
	private static ArrayList<String> dateWithTimeLogsAL = new ArrayList<String>();
	private static ArrayList<String> timeInAL = new ArrayList<String>();
	private static ArrayList<String> timeOutAL = new ArrayList<String>();
	private static ArrayList<String> reasonOverrideAL = new ArrayList<String>();
	private static ArrayList<String> dateAL = new ArrayList<String>();
	
	By manualTimeIn;
	By manualHrIn;
	By manualMinIn;
	By manualFieldInDayPart;
	By manualFieldInLoc;
	By manualTimeOut;
	By manualHrOut;
	By manualMinOut;
	By manualFieldOutDayPart;
	By manualFieldOutLoc;
	By remarksTxtbox;
	By reasonOverride;
	By saveTimelogs = By.xpath("//a[@id=\"editTimeSheetSave\"]");
	By cancelTimeLogs = By.xpath("//a[@id=\"editTimeSheetCancel\"]");
	By errorMsg;
	By missingOverrideErrorMsg;
	By dateNew;
	By timeLogRows = By.xpath("//*[@id=\"tblManualEditTimeLogs\"]/tbody/tr/td[1]");

	public EditTimeLogsPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public int getNumberOfDays() {
		List<WebElement> tlRowsList = driver.findElements(timeLogRows);
		return tlRowsList.size();
	}
	
	public void setDateArrayList() {
		List<WebElement> tlRowsList = driver.findElements(timeLogRows);
		dateAL.clear();
		// iterate list, get text value and store in array list
		for (int i = 0; i < tlRowsList.size(); i++) {
			dateAL.add(tlRowsList.get(i).getText());
		}
	}
	
	public String checkIfDateExists(String date) {
		
		setDateArrayList();
		
		String status = "No";
		if (dateAL.contains(date)) {
			status = "Yes";
			dateWithTimeLogsAL.add(date);
			dateSplit = date;
			String[] dateNumSplit = dateSplit.split("/2022");
			dateNum = dateNumSplit[0].split("/")[1];
			dateNumAppend = 0 + dateNum;
			dateRowAL.add(dateNumAppend);
		}
		return status;
	}

	public void selectDate(String date) {
		manualTimeIn = By.xpath("//td[text()=\""+date+"\"]/following-sibling::td[2]/div[3]/input");
		manualHrIn = By.xpath("//td[text()=\""+date+"\"]/following-sibling::td[2]/div[2]/input[1]");
		manualMinIn = By.xpath("//td[text()=\""+date+"\"]/following-sibling::td[2]/div[2]/input[2]");
		manualFieldInDayPart = By.xpath("//td[text()=\""+date+"\"]/following-sibling::td[2]/div[2]/select[1]");
		manualFieldInLoc = By.xpath("//td[text()=\""+date+"\"]/following-sibling::td[2]/div[2]/select[2]");
		manualTimeOut = By.xpath("//td[text()=\""+date+"\"]/following-sibling::td[3]/div[3]/input");
		manualHrOut = By.xpath("//td[text()=\""+date+"\"]/following-sibling::td[3]/div[2]/input[1]");
		manualMinOut = By.xpath("//td[text()=\""+date+"\"]/following-sibling::td[3]/div[2]/input[2]");
		manualFieldOutDayPart = By.xpath("//td[text()=\""+date+"\"]/following-sibling::td[3]/div[2]/select[1]");
		manualFieldOutLoc = By.xpath("//td[text()=\""+date+"\"]/following-sibling::td[3]/div[2]/select[2]");
		remarksTxtbox = By.xpath("//td[text()=\""+date+"\"]/following-sibling::td[4]/select");		
		reasonOverride = By.xpath("//td[text()=\""+date+"\"]/following-sibling::td[5]/div/input");
		errorMsg = By.xpath("//td[text()=\""+date+"\"]/following-sibling::td[2]/div[4]");
		missingOverrideErrorMsg = By.xpath("//td[text()=\""+date+"\"]/following-sibling::td[5]/div/label");	
	}
	
	public void setTimeIn(String sheetName,String tcName,String columnName1,String columnName2,String columnName3,String columnName4, String date) {
		// ---Time IN
		selectDate(date);
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		desc = "Successfully entered Time In details for date "+date+".";
		waitElementToLoad(manualTimeIn);
		moveAndHighlightElement(manualTimeIn);
		click(manualTimeIn);
		setTimeInHr(sendKeys(manualHrIn,sheetName,tcName,columnName1));
		moveAndHighlightElement(manualHrIn);
		setTimeInMin(sendKeys(manualMinIn,sheetName,tcName,columnName2));
		moveAndHighlightElement(manualMinIn);
		setTimeInDayPart(sendKeys(manualFieldInDayPart,sheetName,tcName,columnName3));
		moveAndHighlightElement(manualFieldInDayPart);
		setTimeInLoc(sendKeys(manualFieldInLoc,sheetName,tcName,columnName4));
		moveAndHighlightElement(manualFieldInLoc);
		
		addToTimeInArrayList();
		
		click(manualHrIn);
		reportPass(methodName,desc);

	}
	
	public void addToTimeInArrayList() {
		timeInHr = getTimeInHr();
		timeInMin = getTimeInMin();
		timeInDayPart = getTimeInDayPart();
		timeInLoc = getTimeInLoc();
		
		// If Time IN Hour entered is 2 digits and the first digit is 0
		timeInHrLength = timeInHr.length(); // 2
		timeInHrIndex0 = Character.toString(timeInHr.charAt(0)); // 0

		if (timeInHrLength == 2 && timeInHrIndex0.equalsIgnoreCase("0")) {
			timeInHrIndex1 = Character.toString(timeInHr.charAt(1));
			timeInHr = timeInHrIndex1;
		}
		
		expectedTimeInInfo = timeInHr + ":" + timeInMin + " " + timeInDayPart + " " + timeInLoc;
		timeInAL.add(expectedTimeInInfo);
	}
	
	public void setTimeOut(String sheetName,String tcName,String columnName1,String columnName2,String columnName3,String columnName4, String date) {
		// ---Time OUT
		selectDate(date);
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		desc = "Successfully entered Time Out details for date "+date+".";
		waitElementToLoad(manualTimeOut);
		moveAndHighlightElement(manualTimeOut);
		
		click(manualTimeOut);
		setTimeOutHr(sendKeys(manualHrOut,sheetName,tcName,columnName1));
		moveAndHighlightElement(manualHrOut);

		setTimeOutMin(sendKeys(manualMinOut,sheetName,tcName,columnName2));
		moveAndHighlightElement(manualMinOut);

		setTimeOutDayPart(sendKeys(manualFieldOutDayPart,sheetName,tcName,columnName3));
		moveAndHighlightElement(manualFieldOutDayPart);

		setTimeOutLoc(sendKeys(manualFieldOutLoc,sheetName,tcName,columnName4));
		moveAndHighlightElement(manualFieldOutLoc);
		
		addToTimeOutArrayList();
		
		click(manualHrOut);
		reportPass(methodName,desc);
	}
	
	public void addToTimeOutArrayList() {
		timeOutHr = getTimeOutHr();
		timeOutMin = getTimeOutMin();
		timeOutDayPart = getTimeOutDayPart();
		timeOutLoc = getTimeOutLoc();
		
		// If Time OUT Hour entered is 2 digits and the first digit is 0
		timeOutHrLength = timeOutHr.length(); // 2
		timeOutHrIndex0 = Character.toString(timeOutHr.charAt(0)); // 0

		if (timeOutHrLength == 2 && timeOutHrIndex0.equalsIgnoreCase("0")) {
			timeOutHrIndex1 = Character.toString(timeOutHr.charAt(1));
			timeOutHr = timeOutHrIndex1;
		}
		
		expectedTimeOutInfo = timeOutHr + ":" + timeOutMin + " " + timeOutDayPart + " " + timeOutLoc;
		timeOutAL.add(expectedTimeOutInfo);
	}

	public void setRemarks(String sheetName,String tcName,String columnName, String date) {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		desc = "Successfully entered Remarks for date "+date+".";
		waitElementToLoad(remarksTxtbox);
		moveAndHighlightElement(remarksTxtbox);
		click(remarksTxtbox);
		//remarks = selectByVisibleText(remarksTxtbox,sheetName,tcName,columnName);
		remarks = sendKeys(remarksTxtbox,sheetName,tcName,columnName);
		reportPass(methodName,desc);
	}

	public void setReasonOverride(String sheetName,String tcName,String columnName, String date) {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		desc ="Successfully entered Reason for TITO Override for date "+date+".";
		waitElementToLoad(reasonOverride);
		moveAndHighlightElement(reasonOverride);
		setReason(sendKeys(reasonOverride,sheetName,tcName,columnName));
		addToReasonArrayList();
		reportPass(methodName,desc);
	}

	public void addToReasonArrayList() {
		reasonOverrideAL.add(getReason());
	}

	public void saveTimeLogs() {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		desc = "Clicked save time logs button.";
		waitElementToLoad(saveTimelogs);
		moveAndHighlightElement(saveTimelogs);
		click(saveTimelogs);
		reportPass(methodName,desc);
	}

	public void cancelTimeLogs() {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		desc = "Successfully cancelled the saving of the time logs.";
		waitElementToLoad(cancelTimeLogs);
		moveAndHighlightElement(cancelTimeLogs);
		Wait(1000);
		click(cancelTimeLogs);
		reportPass(methodName,desc);
	}
	
	public void unSelectTimeIn(String date) {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		desc = "Unselect Time In checkbox";
		selectDate(date);
		waitElementToLoad(manualTimeIn);
		moveAndHighlightElement(manualTimeIn);
		//Wait(1000);
		click(manualTimeIn);
		reportPass(methodName,desc);
	}
	
	public void unSelectTimeOut(String date) {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		desc = "Unselect Time Out checkbox";
		selectDate(date);
		waitElementToLoad(manualTimeOut);
		moveAndHighlightElement(manualTimeOut);
		//Wait(1000);
		click(manualTimeOut);
		reportPass(methodName,desc);
		
	}
	
	public void removeReasonOverride(String date) {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		desc ="Successfully removed TITO Reason Override.";
		selectDate(date);
		waitElementToLoad(reasonOverride);
		moveAndHighlightElement(reasonOverride);
		//Wait(1000);
		clear(reasonOverride);
		reportPass(methodName,desc);
		
	}
	
	public void verifyIfErrorMessageIsDisplayed() {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		waitElementToLoad(errorMsg);
		moveAndHighlightElement(errorMsg);
		String actualErrorMsg =  getText(errorMsg);
		validateText("equal", actualErrorMsg, expectedErrorMsg);
		desc ="Error Message is displayed. Expected: "+expectedErrorMsg+" | Actual: "+actualErrorMsg;
		reportPass(methodName,desc);
	}
	
	public void verifyIfMissingOverrideReasonErrorMessageIsDisplayed() {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		waitElementToLoad(missingOverrideErrorMsg);
		moveAndHighlightElement(missingOverrideErrorMsg);
		String actualMissingOverrideErrorMsg =  getText(missingOverrideErrorMsg);
		validateText("equal", actualMissingOverrideErrorMsg, expectedMissingOverrideErrorMsg);
		desc ="Error Message is displayed. Expected: "+expectedMissingOverrideErrorMsg+" | Actual: "+actualMissingOverrideErrorMsg;
		
		reportPass(methodName,desc);
	}

	public static String getTimeInHr() {
		return timeInHr;
	}

	public static void setTimeInHr(String timeInHr) {
		EditTimeLogsPage.timeInHr = timeInHr;
	}

	public static String getTimeInMin() {
		return timeInMin;
	}

	public static void setTimeInMin(String timeInMin) {
		EditTimeLogsPage.timeInMin = timeInMin;
	}

	public static String getTimeInDayPart() {
		return timeInDayPart;
	}

	public static void setTimeInDayPart(String timeInDayPart) {
		EditTimeLogsPage.timeInDayPart = timeInDayPart;
	}

	public static String getTimeInLoc() {
		return timeInLoc;
	}

	public static void setTimeInLoc(String timeInLoc) {
		EditTimeLogsPage.timeInLoc = timeInLoc;
	}
	
	public static String getReason() {
		return reason;
	}

	public static void setReason(String reason) {
		EditTimeLogsPage.reason = reason;
	}

	public static String getTimeOutHr() {
		return timeOutHr;
	}

	public static void setTimeOutHr(String timeOutHr) {
		EditTimeLogsPage.timeOutHr = timeOutHr;
	}

	public static String getTimeOutMin() {
		return timeOutMin;
	}

	public static void setTimeOutMin(String timeOutMin) {
		EditTimeLogsPage.timeOutMin = timeOutMin;
	}

	public static String getTimeOutDayPart() {
		return timeOutDayPart;
	}

	public static void setTimeOutDayPart(String timeOutDayPart) {
		EditTimeLogsPage.timeOutDayPart = timeOutDayPart;
	}

	public static String getTimeOutLoc() {
		return timeOutLoc;
	}

	public static void setTimeOutLoc(String timeOutLoc) {
		EditTimeLogsPage.timeOutLoc = timeOutLoc;
	}

	public static ArrayList<String> getTimeInAL() {
		return timeInAL;
	}

	public static void setTimeInAL(ArrayList<String> timeInAL) {
		EditTimeLogsPage.timeInAL = timeInAL;
	}

	public static ArrayList<String> getDateRowAL() {
		return dateRowAL;
	}

	public static void setDateRowAL(ArrayList<String> dateRowAL) {
		EditTimeLogsPage.dateRowAL = dateRowAL;
	}

	public static ArrayList<String> getTimeOutAL() {
		return timeOutAL;
	}

	public static void setTimeOutAL(ArrayList<String> timeOutAL) {
		EditTimeLogsPage.timeOutAL = timeOutAL;
	}

	public static ArrayList<String> getReasonOverrideAL() {
		return reasonOverrideAL;
	}

	public static void setReasonOverrideAL(ArrayList<String> reasonOverrideAL) {
		EditTimeLogsPage.reasonOverrideAL = reasonOverrideAL;
	}
	
	public static void clearArrayList() {
		dateRowAL.clear();
		dateWithTimeLogsAL.clear();
		timeInAL.clear();
		timeOutAL.clear();
		reasonOverrideAL.clear();
	}

	public static ArrayList<String> getDateWithTimeLogsAL() {
		return dateWithTimeLogsAL;
	}

	public static void setDateWithTimeLogsAL(ArrayList<String> dateWithTimeLogsAL) {
		EditTimeLogsPage.dateWithTimeLogsAL = dateWithTimeLogsAL;
	}
}
