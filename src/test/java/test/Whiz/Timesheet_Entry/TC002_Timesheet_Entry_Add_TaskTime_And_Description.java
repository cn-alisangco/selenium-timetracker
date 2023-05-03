package test.Whiz.Timesheet_Entry;

import base.BaseClass;
import pageObjects.whizible.Whizible_HomePage;
import pageObjects.whizible.Whizible_LoginPage;
import pageObjects.whizible.Whizible_SelectProjectOrTaskWindow;
import pageObjects.whizible.Whizible_TimesheetEntryPage;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import utilities.ReadExcelData;

@Listeners(utilities.TestNGListeners.class)
public class TC002_Timesheet_Entry_Add_TaskTime_And_Description extends BaseClass {

	Whizible_LoginPage loginPage;
	Whizible_HomePage homePage;
	Whizible_TimesheetEntryPage timesheetEntryPage;
	Whizible_SelectProjectOrTaskWindow selectProjectOrTaskWindow;

	public static String sheetName = "Copy of Whiz"; // sheet name of the test data to be used
	public static String tcName = "Timesheet_AddTaskTime_And_Desc_"; // test case name
	public static String username = "Username";
	public static String password = "Password";
	public static String project = "Project";
	public static String task = "Task";
	public static String date = "Date";
	public static String time = "Time";
	public static String taskDesc = "TaskDesc";

	@Test
	public void AddTaskTimeAndDescription() {
		loginPage = new Whizible_LoginPage(getDriver());
		homePage = new Whizible_HomePage(getDriver());
		timesheetEntryPage = new Whizible_TimesheetEntryPage(getDriver());
		selectProjectOrTaskWindow = new Whizible_SelectProjectOrTaskWindow(getDriver());

		int counter = 0;
		
		loginPage.setUsername(sheetName, tcName+counter, username);
		loginPage.setPassword(sheetName, tcName+counter, password);
		loginPage.clickLogin();

		homePage.clickTimesheetPageBtn();
		homePage.clickTimesheetEntryPageBtn();

		timesheetEntryPage.switchingToInlineFrame();
		timesheetEntryPage.verifyTimesheetEntryPage();
		
		// Gets the column header names and store in array list of String
		timesheetEntryPage.getHeaderNames();

		timesheetEntryPage.clickMoreProjectsBtn();
		selectProjectOrTaskWindow.verifySelectProjectOrTaskWindow();
		selectProjectOrTaskWindow.selectProject(sheetName, tcName+counter, project);
		selectProjectOrTaskWindow.clickTaskCheckbox(sheetName, tcName+counter, task);
		selectProjectOrTaskWindow.clickSelectBtn();
		
        //int tdRowCount = ReadExcelData.getTdCount();
        int tdRowCount = 3;
        timesheetEntryPage.expandDates();
        
        while(counter < tdRowCount ){
        	
        	// Iterates inside the array list of String containing the header names. Start from index[2] until index[8] (These are the indexes of the dates in the timesheet entry page).
        	Boolean dateExists = timesheetEntryPage.checkIfDateExists(sheetName, tcName+counter, date);
    		
    		if (dateExists == true) {
    			timesheetEntryPage.setHours(sheetName, tcName+counter, task, time);
    			timesheetEntryPage.clickDescription();

    			if (timesheetEntryPage.checkIfWarningMessageIsDisplayed() == true) {
    				timesheetEntryPage.clickYesOnWarningMessage();
    				timesheetEntryPage.clickOnDateTimeTextbox();
    			}

    			timesheetEntryPage.setDescription(sheetName, tcName+counter, taskDesc);
    		}
    		counter++;

        }
        timesheetEntryPage.clickSaveBtn();
		timesheetEntryPage.verifyWorkHours();
		homePage.clickLogoutButton();

	}
}



//// single date only
//	Boolean dateExists = timesheetEntryPage.checkIfDateExists(sheetName, tcName+counter, date);
//	//System.out.println(Whizible_TimesheetEntryPage.getDateSelected()+" exists? "+dateExists);
//	
//	if (dateExists == true) {
//		timesheetEntryPage.setHours(sheetName, tcName+counter, task, time);
//		timesheetEntryPage.clickDescription();
//
//		if (timesheetEntryPage.checkIfWarningMessageIsDisplayed() == true) {
//			timesheetEntryPage.clickYesOnWarningMessage();
//			timesheetEntryPage.clickOnDateTimeTextbox();
//		}
//
//		timesheetEntryPage.setDescription(sheetName, tcName+counter, taskDesc);
//		timesheetEntryPage.clickSaveBtn();
//		timesheetEntryPage.verifyWorkHours();
//}
//	homePage.clickLogoutButton();