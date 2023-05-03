package test.Whiz.Timesheet_Entry;

import base.BaseClass;
import pageObjects.whizible.Whizible_HomePage;
import pageObjects.whizible.Whizible_LoginPage;
import pageObjects.whizible.Whizible_TimesheetEntryPage;
import pageObjects.whizible.Whizible_ViewTimesheetPage;

import org.testng.annotations.Test;

public class TC004_Timesheet_Entry_SubmitTimesheet extends BaseClass {
    Whizible_LoginPage loginPage;
    Whizible_HomePage homePage;
    Whizible_TimesheetEntryPage timesheetEntryPage;
    Whizible_ViewTimesheetPage viewTimesheetPage;
    public static String username = "Valid Username";
    public static String password = "Valid Password";
    public static String sheetName = "Timesheet_Entry_Create Task";
    public static String tcName = "Whizible_Timesheet_Entry_Create Task";

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
    public void submitTimesheet(){
        loginToWhizible(username, password);
        homePage = new Whizible_HomePage(getDriver());
        timesheetEntryPage = new Whizible_TimesheetEntryPage(getDriver());
        viewTimesheetPage = new Whizible_ViewTimesheetPage(getDriver());

        // navigate to Timesheet Entry page
        homePage.clickTimesheetPageBtn();
        homePage.clickTimesheetEntryPageBtn();
        timesheetEntryPage.switchingToInlineFrame();
        timesheetEntryPage.verifyTimesheetEntryPage();

        // submit timesheet
        timesheetEntryPage.clickViewTimesheetBtn();
        viewTimesheetPage.verifyViewTimesheetPage();
        if(viewTimesheetPage.verifyActualHours() && !viewTimesheetPage.getTimesheetStatus().toUpperCase().equals("SUBMITTED")){
            viewTimesheetPage.clickSubmitBtn();
            viewTimesheetPage.getTimesheetStatus();
            viewTimesheetPage.clickBackBtn();
        } else{
            viewTimesheetPage.getTimesheetStatus();
            viewTimesheetPage.clickBackBtn();
        }
        
        homePage.clickLogoutButton();
    }
}
