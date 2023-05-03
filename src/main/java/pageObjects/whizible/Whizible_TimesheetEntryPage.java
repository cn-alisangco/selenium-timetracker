package pageObjects.whizible;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import userHelper.UserHelper;

public class Whizible_TimesheetEntryPage extends UserHelper {
    WebDriver driver;
    public String desc;
    public String methodName;
    public String quickEntryTime;
    public String taskStr;
    public String time;
    By moreProjectsBtn = By.xpath("//*[@id='btnMoreProjects']");
    By viewTimesheetBtn = By.xpath("//*[@id='DivDailyFilterPanel']/div/div[1]/ul/li[1]/a");
    By createTaskBtn = By.xpath("//*[@id='modalapendbtn']");
    By saveBtn = By.xpath("//*[@id='btnSave']");
    By quickEntryTxtField = By.xpath("//input[@id='txtQuickEntry']");
    By addQuickEntryBtn = By.xpath("//*[@id='btnQuickEntry']");
    By alertMsg = By.xpath("//*[@id=\"alertMsg\"]");
    By confirmationMessage = By.xpath("//*[@id=\"ConfirmMessagemodalinfo\"]/div[1]/div/div[1]/h4");
    By confirmationMessageYesBtn = By.xpath("//*[@id=\"ConfirmMessagemodalinfo\"]/div[1]/div/div[3]/button[2]");
    
    By header = By.xpath("//thead[@id=\"timesheetHeader\"]/tr[1]/th");
    By expandDatesBtn = By.xpath("//i[@class='click-me fas fa-caret-left']");
    By dateTimeTxtbox;
    By taskDescTxtbox;
    By warningMsg = By.xpath("//div[@id='ConfirmMessagemodalinfo']/div[@class='modal-dialog ui-draggable']/div");
    By warningMsgYesBtn = By.xpath("//div[@id='ConfirmMessagemodalinfo']/div[@class='modal-dialog ui-draggable']/div/div[3]/button[2]");
    By workHours;
    By overlayProgress = By.xpath("//div[@class='loadingoverlay_progress_wrapper']");
    
    List<WebElement> headerNames;
    ArrayList<String> headerNamesStringAL = new ArrayList<String>();
    ArrayList<String> timeSheetHours = new ArrayList<String>();
    ArrayList<Integer> dateColumn = new ArrayList<Integer>();
    ArrayList<String> date = new ArrayList<String>();
    
    private static int columnNum;
    private static String projectTask;
    private static String project;
    private static String dateSelected;

    public Whizible_TimesheetEntryPage(WebDriver driver) {
        this.driver = driver;
    }

    public void verifyTimesheetEntryPage(){
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        waitElementToLoad(moreProjectsBtn);
        moveAndHighlightElement(moreProjectsBtn);
        desc = "Successfully verified that the Timesheet Entry page is displayed";
        reportPass(methodName, desc);
    }

    public void clickMoreProjectsBtn(){
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        waitElementToLoad(moreProjectsBtn);
        moveAndHighlightElement(moreProjectsBtn);
        hover(moreProjectsBtn);
        click(moreProjectsBtn);
        desc = "Clicked the More Projects+ button";
        reportPass(methodName, desc);
    }

    public void clickViewTimesheetBtn(){
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        waitElementToLoad(viewTimesheetBtn);
        moveAndHighlightElement(viewTimesheetBtn);
        hover(viewTimesheetBtn);
        click(viewTimesheetBtn);
        desc = "Clicked the View Timesheet button";
        reportPass(methodName, desc);
    }

    public void clickCreateTaskBtn(){
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        waitElementToLoad(createTaskBtn);
        moveAndHighlightElement(createTaskBtn);
        hover(createTaskBtn);
        click(createTaskBtn);
        desc = "Clicked the Create Task button";
        reportPass(methodName, desc);
    }

    public void clickSaveBtn(){
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        waitElementToLoad(saveBtn);
        moveAndHighlightElement(saveBtn);
        hover(saveBtn);
        click(saveBtn);
        desc = "Clicked the Save button";
        reportPass(methodName, desc);
    }

    public void populateQuickEntryField(String sheetName, String tcName, String columnName){
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        waitElementToLoad(quickEntryTxtField);
        moveAndHighlightElement(quickEntryTxtField);
        quickEntryTime = sendKeys(quickEntryTxtField, sheetName, tcName, columnName);
        desc = String.format("User entered %s in the Quick Entry text field", quickEntryTime);
        reportPass(methodName, desc);
    }

    public void clickAddQuickEntryBtn(){
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        waitElementToLoad(addQuickEntryBtn);
        moveAndHighlightElement(addQuickEntryBtn);
        hover(addQuickEntryBtn);
        click(addQuickEntryBtn);
        desc = "Clicked the Add Quick Entry button";
        reportPass(methodName, desc);
        Wait(5000);
    }

    public void clickTaskQuickEntryCheckbox(String sheetName,String tcName,String columnName){
        taskStr = getDataFromExcel(sheetName, tcName, columnName);
        By quickEntryCheckbox = By.xpath("//span[text()='task']/../../following-sibling::td/div[@class='custom_chckbox']/label".replace("task", taskStr));
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        waitElementToLoad(quickEntryCheckbox);
        moveAndHighlightElement(quickEntryCheckbox);
        hover(quickEntryCheckbox);
        click(quickEntryCheckbox);
        desc = String.format("Click the %s quick entry checkbox", taskStr);
        reportPass(methodName, desc);
    }

    public boolean verifyAlertMessage(){
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        waitElementToLoad(alertMsg);
        moveAndHighlightElement(alertMsg);
        if(getText(alertMsg).toUpperCase().contains("SUCCESSFULLY")){
            desc = "Successfully entered the time using Quick Entry";
            reportPass(methodName, desc);
            return true;
        } else{
            desc = getText(alertMsg);
            reportPass(methodName, desc);
            return false;
        }
    }

    public boolean verifyTimeSlot(String sheetName, String tcName, String columnName){
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        taskStr = getDataFromExcel(sheetName, tcName, columnName);
        int counter = 2;
        while(counter < 7){
            By enteredTime = By.xpath("//tbody/tr/td/div/span[text()='task']/../../following-sibling::td[counter]/div/input".replace("task", taskStr).replace("counter", String.valueOf(counter)));
            waitElementToLoad(enteredTime);
            moveAndHighlightElement(enteredTime);
            if(!getValueAttribute(enteredTime).equals("00:00")){
                desc = "Successfully verified that time has been entered in at least 1 day of the week";
                reportPass(methodName, desc);
                return false;
            }
            counter++;
        }
        desc = "Successfully verified that no time has been entered for the selected week";
        reportPass(methodName, desc);
        return true;
    }

    public void verifyEnteredTime(String sheetName,String tcName,String columnName1, String columnName2){
        Wait(5000);
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        taskStr = getDataFromExcel(sheetName, tcName, columnName1);
        time = getDataFromExcel(sheetName, tcName, columnName2);
        int counter = 2;
        while(counter < 7){
            By enteredTime = By.xpath("//tbody/tr/td/div/span[text()='task']/../../following-sibling::td[counter]/div/input".replace("task", taskStr).replace("counter", String.valueOf(counter)));
            waitElementToLoad(enteredTime);
            moveAndHighlightElement(enteredTime);
            validateText("equal", getValueAttribute(enteredTime), time);
            counter++;
        }
        desc = "Successfully verified the time entered using Quick Entry function";
        reportPass(methodName, desc);
    }

    public boolean verifyConfirmationMessage(){
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        desc = "Successfully verified the existence of the confirmation message";
        reportPass(methodName, desc);
        return elementExistenceFlag(confirmationMessage);
    }

    public void clickConfirmationMessageYesBtn(){
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        waitElementToLoad(confirmationMessageYesBtn);
        moveAndHighlightElement(confirmationMessageYesBtn);
        hover(confirmationMessageYesBtn);
        click(confirmationMessageYesBtn);
        desc = "Clicked the Yes button";
        reportPass(methodName, desc);
    }

    public void switchingToInlineFrame(){
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        switchToFrameByNameorID("frmNewVersion");
        desc = "Switched to inline frame";
        reportPass(methodName, desc);
    }
    
	public void getHeaderNames() {
		expandDates();

		headerNames = driver.findElements(header);
		int headerNamesSize = headerNames.size();

		for (int i = 0; i < headerNamesSize; i++) {
			headerNamesStringAL.add(headerNames.get(i).getText());
		}
	}
	
	public void expandDates() {
		try {
			waitElementToLoad(expandDatesBtn);
	        moveAndHighlightElement(expandDatesBtn);
			click(expandDatesBtn);
		} catch (org.openqa.selenium.StaleElementReferenceException ex) {
			waitElementToLoad(expandDatesBtn);
	        moveAndHighlightElement(expandDatesBtn);
			click(expandDatesBtn);
		}
	}
	
	public Boolean checkIfDateExists(String sheetName, String tcName, String columnName) {
		//expandDates();
		
		setDateSelected(getExcelData(sheetName, tcName, columnName));
		Boolean status = false;
		
		//dates starts at index 2 and ends in index 8
		for (int i = 2; i < 9; i++) {
			String headerName = headerNamesStringAL.get(i);
			String[] headerNameSplit = headerName.split(",");
			if (headerNameSplit[0].equals(getDateSelected())) {
				columnNum = i;
				date.add(getDateSelected());
				status = true;
			}
		}
		return status;
	}
	
	public void setHours(String sheetName, String tcName, String columnName1, String columnName2) {
		// columnName1 = task | columnName2 = time

		projectTask = getExcelData(sheetName, tcName, columnName1);

		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		desc = "Entered hours successfully for date " + getDateSelected();
		dateTimeTxtbox = By.xpath("//*[text()=\"" + projectTask + "\"]/ancestor::td/following-sibling::td[" + columnNum
				+ "]/div/input[1]");
		try {
			waitElementToLoad(dateTimeTxtbox);
			moveAndHighlightElement(dateTimeTxtbox);
			sendKeysCtrlADelete(dateTimeTxtbox);
			dateColumn.add(columnNum);
			String hours = sendKeys(dateTimeTxtbox, sheetName, tcName, columnName2);
			StringBuilder sb = new StringBuilder(hours);
			sb.insert((sb.length() - 2), ':');
			timeSheetHours.add(sb.toString());
			click(dateTimeTxtbox);
		} catch (org.openqa.selenium.StaleElementReferenceException ex) {
			waitElementToLoad(dateTimeTxtbox);
			moveAndHighlightElement(dateTimeTxtbox);
			sendKeysCtrlADelete(dateTimeTxtbox);
			dateColumn.add(columnNum);
			String hours = sendKeys(dateTimeTxtbox, sheetName, tcName, columnName2);
			StringBuilder sb = new StringBuilder(hours);
			sb.insert((sb.length() - 2), ':');
			timeSheetHours.add(sb.toString());
			click(dateTimeTxtbox);
		}
		reportPass(methodName, desc);
	}
	
	public void clickDescription() {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		desc = "Clicked description";
		taskDescTxtbox = By.xpath("//*[text()=\""+projectTask+"\"]/ancestor::td/following-sibling::td["+columnNum+"]/div/input[1]/following-sibling::div/textarea");
		waitElementToLoad(taskDescTxtbox);
        moveAndHighlightElement(taskDescTxtbox);
		click(taskDescTxtbox);
		reportPass(methodName, desc);
	}
	
	public void setDescription(String sheetName, String tcName, String columnName) {
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		desc = "Entered description successfully for date "+getDateSelected();
		taskDescTxtbox = By.xpath("//*[text()=\""+projectTask+"\"]/ancestor::td/following-sibling::td["+columnNum+"]/div/input[1]/following-sibling::div/textarea");
		waitElementToLoad(taskDescTxtbox);
        moveAndHighlightElement(taskDescTxtbox);
		clear(taskDescTxtbox);
		sendKeys(taskDescTxtbox, sheetName, tcName, columnName);
		reportPass(methodName, desc);
		
	}
	
	public Boolean checkIfWarningMessageIsDisplayed() {
		Boolean status = false;
		if (elementExistenceFlag(warningMsg) == true) {
			status = true;
		}
		return status;
	}
	
	public void clickYesOnWarningMessage() {
		waitElementToLoad(warningMsgYesBtn);
        moveAndHighlightElement(warningMsgYesBtn);
		click(warningMsgYesBtn);
	}
	
	public void clickOnDateTimeTextbox() {
		waitElementToLoad(dateTimeTxtbox);
        moveAndHighlightElement(dateTimeTxtbox);
		click(dateTimeTxtbox);
	}
	
	public void verifyWorkHours() {
		waitUntilOverlayIsNotDisplayed();
		expandDates();
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		for (int i = 0; i < timeSheetHours.size(); i++) {
			dateTimeTxtbox = By.xpath("//*[text()=\"" + projectTask + "\"]/ancestor::td/following-sibling::td[" + dateColumn.get(i)
					+ "]/div/input[1]");
			waitElementToLoad(dateTimeTxtbox);
	        moveAndHighlightElement(dateTimeTxtbox);
	        String expectedWorkHours = getAttribute(dateTimeTxtbox);
	        project = Whizible_SelectProjectOrTaskWindow.getProject();
			workHours = By.xpath("//*[text()[contains(.,\'"+project+"\')]]/ancestor::td/following-sibling::td["+dateColumn.get(i)+"]/label");
			waitElementToLoad(workHours);
	        moveAndHighlightElement(workHours);
			String actualWorkHours = getText(workHours);
			validateText("equal",timeSheetHours.get(i),actualWorkHours);
			validateText("equal",actualWorkHours,expectedWorkHours);
			desc = "Verified the work hours for date "+date.get(i)+". Expected: "+expectedWorkHours+" | Actual: "+actualWorkHours;
			reportPass(methodName, desc);
		}
	}
	
	public void waitUntilOverlayIsNotDisplayed() {
		waitElementToBeInvinsible(overlayProgress);
	}

	public static String getDateSelected() {
		return dateSelected;
	}

	public static void setDateSelected(String dateSelected) {
		Whizible_TimesheetEntryPage.dateSelected = dateSelected;
	}
	
}
