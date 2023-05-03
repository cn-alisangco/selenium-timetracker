package test.Whiz.Timesheet_Entry;

import base.BaseClass;
import pageObjects.whizible.Whizible_HomePage;
import pageObjects.whizible.Whizible_LoginPage;
import pageObjects.whizible.Whizible_SelectProjectOrTaskWindow;
import pageObjects.whizible.Whizible_TimesheetEntryPage;

import org.testng.annotations.Test;

import utilities.ReadExcelData;

public class TC003_Timesheet_Entry_QuickEntry_WeeklySingleTask extends BaseClass {
    Whizible_LoginPage loginPage;
    Whizible_HomePage homePage;
    Whizible_TimesheetEntryPage timesheetEntryPage;
    Whizible_SelectProjectOrTaskWindow selectProjectOrTaskWindow;
    public static String sheetName = "Timesheet_Entry_Select Project";
    public static String tcName = "Whizible_Timesheet_Entry_Select Project";
    public static String project = "Project";
    public static String task = "Task";
    public static String time = "Time";
    public static String username = "Valid Username";
    public static String password = "Valid Password";

    public void loginToWhizible(String username, String password){
        loginPage = new Whizible_LoginPage(getDriver());
        homePage = new Whizible_HomePage(getDriver());
        loginPage.setUsername(sheetName,tcName+"1",username);
        loginPage.setPassword(sheetName,tcName+"1",password);
        loginPage.clickLogin();
        if(username.toUpperCase().equals("VALID USERNAME") && password.toUpperCase().equals("VALID PASSWORD")){
            homePage.verifySuccessfulLogin();
        } else if(username.toUpperCase().equals("NULL") && password.toUpperCase().equals("NULL")){
            loginPage.verifyEmptyUsernameField();
            loginPage.verifyEmptyPasswordField();
        } else{
            loginPage.verifyLoginErrorMessage();
        }
    }
    @Test
    public void quickEntry(){
        loginToWhizible(username, password);
        homePage = new Whizible_HomePage(getDriver());
        timesheetEntryPage = new Whizible_TimesheetEntryPage(getDriver());
        selectProjectOrTaskWindow = new Whizible_SelectProjectOrTaskWindow(getDriver());

        // navigate to Timesheet Entry page
        homePage.clickTimesheetPageBtn();
        homePage.clickTimesheetEntryPageBtn();
        timesheetEntryPage.switchingToInlineFrame();
        timesheetEntryPage.verifyTimesheetEntryPage();

        int counter = 1;
        int tdRowCount = ReadExcelData.getTdCount();
        while(counter < tdRowCount + 1){
            String counterStr = String.valueOf(counter);
            // select project in the Select Project / Task window
            timesheetEntryPage.clickMoreProjectsBtn();
            selectProjectOrTaskWindow.verifySelectProjectOrTaskWindow();
            selectProjectOrTaskWindow.selectProject(sheetName, tcName+counterStr, project);
            selectProjectOrTaskWindow.clickTaskCheckbox(sheetName, tcName+counterStr, task);
            selectProjectOrTaskWindow.clickSelectBtn();

            // quick entry
            if(timesheetEntryPage.verifyTimeSlot(sheetName, tcName+counterStr, task)){
                timesheetEntryPage.clickTaskQuickEntryCheckbox(sheetName, tcName+counterStr, task);
                timesheetEntryPage.populateQuickEntryField(sheetName, tcName+counterStr, time);
                timesheetEntryPage.clickAddQuickEntryBtn();
                if(timesheetEntryPage.verifyAlertMessage()){
                    timesheetEntryPage.verifyEnteredTime(sheetName, tcName+counterStr, task, time);
                }
            }
            counter++;
        }
        homePage.clickLogoutButton();
    }
}


