package test;

import base.BaseClass;
import pageObjects.timetracker.FileALeavePage;
import pageObjects.timetracker.HomePage;
import pageObjects.timetracker.LoginPage;

import org.openqa.selenium.By;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import utilities.ReadExcelData;

@Listeners(utilities.TestNGListeners.class) //need to uncomment this when running this TC only
public class TC9_File_A_Leave extends BaseClass {

    LoginPage loginPage;
    HomePage homePage;
    FileALeavePage fileALeavePage;

    public static String sheetName = "File A Leave_Specific Date";
    public static String tcName = "TC9_File_A_Leave_";
    public static String username = "Valid Username";
    public static String password = "Valid Password";
    public static String column;
    public static String dateStringCounter;
    public static String stringCounter;
    public static String remarksColPathStr = "//*[@id='counter']/td[10]";
    public static String fileALeaveXPathStr = "//*[@id='counter']/td[8]/div/a[1]";
    By remarksColPath;
    By fileALeaveXPath;
    By notEnoughCreditsError = By.xpath("//*[@id='dialog-modal']");
    By notEnoughCreditsOkBtn = By.xpath("//*[@id='OkBtn']/span/span");

    public void loginToTimeTracker(String username, String password) {
        loginPage = new LoginPage(getDriver());
        homePage = new HomePage(getDriver());
        loginPage.proceedToLoginPage();
        loginPage.setUsername(sheetName, tcName+"1", username);
        loginPage.setPassword(sheetName, tcName+"1", password);
        loginPage.clickLogin();
        if (username.toUpperCase().equals("VALID USERNAME") && password.toUpperCase().equals("VALID PASSWORD")) {
            homePage.verifySuccessfulLogin();
        } else if (username.toUpperCase().equals("NULL") && password.toUpperCase().equals("NULL")) {
            loginPage.verifyNullFields();
        } else {
            loginPage.verifyLoginErrorMessage();
        }
    }

    @Test
    public void SubmitAllPopulatedRequiredFields() {
        loginToTimeTracker(username, password);
        homePage = new HomePage(getDriver());
        fileALeavePage = new FileALeavePage(getDriver());

//        int counter = 1;
        int counter = 11;
//        int tdRowCount = ReadExcelData.getTdCount();
//        while(counter < tdRowCount){
            try{
                stringCounter = String.valueOf(counter);
                column = "Date";
                dateStringCounter = String.format("0%s", String.valueOf(homePage.getDate(sheetName, tcName+stringCounter, column)));
                fileALeaveXPath = By.xpath(fileALeaveXPathStr.replace("counter", dateStringCounter));
                remarksColPath = By.xpath(remarksColPathStr.replace("counter", dateStringCounter));

                if (homePage.verifyRemarksCol(remarksColPath) && homePage.verifyFileALeaveIsAvailable(fileALeaveXPath)) {
                    homePage.clickFileALeave(fileALeaveXPath);
                    column = "Leave Type";
                    String leaveType = fileALeavePage.selectLeaveType(sheetName, tcName+stringCounter, column);
                    column = "Reason";
                    fileALeavePage.selectReason(sheetName, tcName+stringCounter, column);
                    column = "Comment";
                    fileALeavePage.populateCommentTextBox(sheetName, tcName+stringCounter, column);
                    column = "Contact Number";
                    fileALeavePage.populateContactNumberField(sheetName, tcName+stringCounter, column);
                    fileALeavePage.clickSubmitBtnAndWait();
                    homePage.verifyLeaveIsSubmitted(remarksColPath, leaveType);
                    if (homePage.elementExistenceFlag(notEnoughCreditsError)) {
                        fileALeavePage.verifyNotEnoughBalance(notEnoughCreditsError);
                        fileALeavePage.clickOkInNotEnoughBalanceErrorMessage(notEnoughCreditsOkBtn);
                        fileALeavePage.clickCancelBtn();
                    } else{
                        System.out.println("File A Leave action is not available for the specified date");
                    }
                }
            } catch (Exception e){
                if(fileALeavePage.verifyCancelBtn()){
                    fileALeavePage.clickCancelBtn();
                }
                System.out.println("Something went wrong!");
            }
//            counter++;
//        }
    }
}
