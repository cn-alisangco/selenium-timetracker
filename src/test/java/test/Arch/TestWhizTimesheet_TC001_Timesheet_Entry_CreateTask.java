package test.Arch;

import base.BaseClass;
import org.testng.annotations.Test;
import pageObjects.*;
import pages.Arch.Whizible_CreateTaskWindow;
import pages.Arch.Whizible_HomePage;
import pages.Arch.Whizible_LoginPage;
import pages.Arch.Whizible_TimesheetEntryPage;
import utilities.ReadExcelData;

public class TestWhizTimesheet_TC001_Timesheet_Entry_CreateTask extends BaseClass {
    Whizible_LoginPage loginPage;
    Whizible_HomePage homePage;
    Whizible_TimesheetEntryPage timesheetEntryPage;
    Whizible_CreateTaskWindow createTaskWindow;
    public static String sheetName = "Timesheet_Entry_Create Task";
    public static String tcName = "Whizible_Timesheet_Entry_Create Task";
    public static String date = "Date";
    public static String project = "Project";
    public static String task = "Task";
    public static String taskType = "Task Type";
    public static String priority = "Priority";
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
    public void createTask(){
        loginToWhizible(username, password);
        homePage = new Whizible_HomePage(getDriver());
        timesheetEntryPage = new Whizible_TimesheetEntryPage(getDriver());
        createTaskWindow = new Whizible_CreateTaskWindow(getDriver());

        // navigate to Timesheet Entry page
        homePage.clickTimesheetPageBtn();
        homePage.clickTimesheetEntryPageBtn();
        timesheetEntryPage.switchingToInlineFrame();
        timesheetEntryPage.verifyTimesheetEntryPage();

        // go to create task window
        timesheetEntryPage.clickCreateTaskBtn();
        createTaskWindow.verifyCreateTaskWindow();

        int counter = 1;
        int tdRowCount = ReadExcelData.getTdCount();
        String counterStr;
//      while(counter < tdRowCount + 1){
        while(counter < tdRowCount){
            counterStr = String.valueOf(counter);
            createTaskWindow.populateDateField(sheetName, tcName+counterStr, date);
            createTaskWindow.selectProject(sheetName, tcName+counterStr, project);
            createTaskWindow.populateTaskField(sheetName, tcName+counterStr, task);
            createTaskWindow.selectTask(sheetName, tcName+counterStr, taskType);
            createTaskWindow.selectPriority(sheetName, tcName+counterStr, priority);
            createTaskWindow.populateActualTimeField(sheetName, tcName+counterStr, time);
            createTaskWindow.clickSaveAndCreateNewBtn();
            counter++;
        }
        createTaskWindow.clickCloseBtn();

        counter = 1;
        tdRowCount = ReadExcelData.getTdCount();
//      while(counter < tdRowCount + 1){
        while(counter < tdRowCount){
            counterStr = String.valueOf(counter);
            createTaskWindow.verifyTaskInTimesheet(sheetName, tcName+counterStr,task);
            counter++;
        }
        
        homePage.clickLogoutButton();
    }
}
