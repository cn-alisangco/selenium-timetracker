package test;

import base.BaseClass;
import pageObjects.timetracker.FileALeavePage;
import pageObjects.timetracker.HomePage;
import pageObjects.timetracker.LoginPage;

import org.openqa.selenium.By;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(utilities.TestNGListeners.class) //need to uncomment this when running this TC only
public class TimeTracker_FileALeave extends BaseClass {
    LoginPage loginPage;
    HomePage homePage;
    FileALeavePage fileALeavePage;

    public static String sheetName = "File A Leave";
    public static String tcName = "TimeTracker_FileALeave";
    public static String column;
    public static String username = "Valid Username";
    public static String password = "Valid Password";

    public static int counter;
    public static int innerCounter;
    public static String stringCounter;
    public static String remarksColPathStr = "//*[@id='counter']/td[10]";
    public static String fileALeaveXPathStr = "//*[@id='counter']/td[8]/div/a[1]";
    By remarksColPath;
    By fileALeaveXPath;

    By notEnoughCreditsError = By.xpath("//div[@class='ui-dialog ui-widget ui-widget-content ui-corner-all customUIDialog'][2]/div[2]/p");
    By notEnoughCreditsOkBtn = By.xpath("//*[@class='ui-dialog-buttonset']/button");

    public static boolean flag;

    public void loginToTimeTracker(String username, String password){
        loginPage = new LoginPage(getDriver());
        homePage = new HomePage(getDriver());
        loginPage.proceedToLoginPage();
        loginPage.setUsername(sheetName,tcName+"1",username);
        loginPage.setPassword(sheetName,tcName+"1",password);
        loginPage.clickLogin();
        if(username.toUpperCase().equals("VALID USERNAME") && password.toUpperCase().equals("VALID PASSWORD")){
            homePage.verifySuccessfulLogin();
        } else if(username.toUpperCase().equals("NULL") && password.toUpperCase().equals("NULL")){
            loginPage.verifyNullFields();
        } else{
            loginPage.verifyLoginErrorMessage();
        }
    }

    public void initialization(){
        loginToTimeTracker(username, password);
        homePage = new HomePage(getDriver());
        fileALeavePage = new FileALeavePage(getDriver());
        counter = 1;
    }

    @Test
    public void FileALeaveBtnsAreActive(){
        initialization();
        while(counter < homePage.getTotalDates() + 1){
            stringCounter = String.format("0%s",String.valueOf(counter));
            fileALeaveXPath = By.xpath(fileALeaveXPathStr.replace("counter", stringCounter));
            remarksColPath = By.xpath(remarksColPathStr.replace("counter", stringCounter));
            if(homePage.verifyRemarksCol(remarksColPath) && homePage.verifyFileALeaveIsAvailable(fileALeaveXPath)){
                homePage.clickFileALeave(fileALeaveXPath);
                fileALeavePage.clickCancelBtn();
            }
            counter++;
        }
    }
    
    @Test
    public void FileALeaveCloseButton(){
        initialization();
        while(counter < homePage.getTotalDates() + 1){
            stringCounter = String.format("0%s",String.valueOf(counter));
            fileALeaveXPath = By.xpath(fileALeaveXPathStr.replace("counter", stringCounter));
            remarksColPath = By.xpath(remarksColPathStr.replace("counter", stringCounter));
            if(homePage.verifyRemarksCol(remarksColPath) && homePage.verifyFileALeaveIsAvailable(fileALeaveXPath)){
                homePage.clickFileALeave(fileALeaveXPath);
                fileALeavePage.clickCloseBtn();
            }
            counter++;
        }
    }


    @Test
    public void DateAppliedIsEqualToCurrentDate(){
        initialization();
        while(counter < homePage.getTotalDates() + 1){
            stringCounter = String.format("0%s",String.valueOf(counter));
            fileALeaveXPath = By.xpath(fileALeaveXPathStr.replace("counter", stringCounter));
            remarksColPath = By.xpath(remarksColPathStr.replace("counter", stringCounter));
            if(homePage.verifyRemarksCol(remarksColPath) && homePage.verifyFileALeaveIsAvailable(fileALeaveXPath)){
                homePage.clickFileALeave(fileALeaveXPath);
                fileALeavePage.verifyDateApplied();
                fileALeavePage.clickCancelBtn();
            }
            counter++;
        }
    }

    @Test
    public void LeaveTypesAreSelectable(){
        initialization();
        column = "Leave Type";
        while(counter < homePage.getTotalDates() + 1){
            stringCounter = String.format("0%s",String.valueOf(counter));
            fileALeaveXPath = By.xpath(fileALeaveXPathStr.replace("counter", stringCounter));
            remarksColPath = By.xpath(remarksColPathStr.replace("counter", stringCounter));
            if(homePage.verifyRemarksCol(remarksColPath) && homePage.verifyFileALeaveIsAvailable(fileALeaveXPath)){
                homePage.clickFileALeave(fileALeaveXPath);
                innerCounter = 1;
                while(innerCounter < 14){
                    fileALeavePage.selectLeaveType(sheetName, tcName+innerCounter,column);
                    innerCounter++;
                }
                fileALeavePage.clickCancelBtn();
            }
            counter++;
        }
    }

    @Test
    public void RunningAndProjectedLeaveBalancesAreVisible(){
        initialization();
        while(counter < homePage.getTotalDates() + 1){
            stringCounter = String.format("0%s",String.valueOf(counter));
            fileALeaveXPath = By.xpath(fileALeaveXPathStr.replace("counter", stringCounter));
            remarksColPath = By.xpath(remarksColPathStr.replace("counter", stringCounter));
            if(homePage.verifyRemarksCol(remarksColPath) && homePage.verifyFileALeaveIsAvailable(fileALeaveXPath)){
                homePage.clickFileALeave(fileALeaveXPath);
                fileALeavePage.verifyRunningLeaveBalance();
                fileALeavePage.verifyProjectedLeaveBalance();
                fileALeavePage.clickCancelBtn();
            }
            counter++;
        }
    }

    @Test
    public void LeaveStartAndEndDatesAreVisible(){
        initialization();
        while(counter < homePage.getTotalDates() + 1){
            stringCounter = String.format("0%s",String.valueOf(counter));
            fileALeaveXPath = By.xpath(fileALeaveXPathStr.replace("counter", stringCounter));
            remarksColPath = By.xpath(remarksColPathStr.replace("counter", stringCounter));
            if(homePage.verifyRemarksCol(remarksColPath) && homePage.verifyFileALeaveIsAvailable(fileALeaveXPath)){
                homePage.clickFileALeave(fileALeaveXPath);
                fileALeavePage.verifyFromDate();
                fileALeavePage.verifyToDate();
                fileALeavePage.clickCancelBtn();
            }
            counter++;
        }
    }

    @Test
    public void HalfDayCheckboxIsClickable(){
        initialization();
        while(counter < homePage.getTotalDates() + 1){
            stringCounter = String.format("0%s",String.valueOf(counter));
            fileALeaveXPath = By.xpath(fileALeaveXPathStr.replace("counter", stringCounter));
            remarksColPath = By.xpath(remarksColPathStr.replace("counter", stringCounter));
            if(homePage.verifyRemarksCol(remarksColPath) && homePage.verifyFileALeaveIsAvailable(fileALeaveXPath)){
                homePage.clickFileALeave(fileALeaveXPath);
                fileALeavePage.markHalfDayCheckbox();
                fileALeavePage.unmarkedHalfDayCheckbox();
                fileALeavePage.clickCancelBtn();
            }
            counter++;
        }
    }

    @Test
    public void ReasonsAreSelectable(){
        initialization();
        column = "Reason";
        while(counter < homePage.getTotalDates() + 1){
            stringCounter = String.format("0%s",String.valueOf(counter));
            fileALeaveXPath = By.xpath(fileALeaveXPathStr.replace("counter", stringCounter));
            remarksColPath = By.xpath(remarksColPathStr.replace("counter", stringCounter));
            if(homePage.verifyRemarksCol(remarksColPath) && homePage.verifyFileALeaveIsAvailable(fileALeaveXPath)){
                homePage.clickFileALeave(fileALeaveXPath);
                innerCounter = 1;
                while(innerCounter < 5){
                    fileALeavePage.selectReason(sheetName, tcName+innerCounter,column);
                    innerCounter++;
                }
                fileALeavePage.clickCancelBtn();
            }
            counter++;
        }
    }

    @Test
    public void CommentTextBoxAcceptsText(){
        initialization();
        column = "Comment";
        while(counter < homePage.getTotalDates() + 1){
            stringCounter = String.format("0%s",String.valueOf(counter));
            fileALeaveXPath = By.xpath(fileALeaveXPathStr.replace("counter", stringCounter));
            remarksColPath = By.xpath(remarksColPathStr.replace("counter", stringCounter));
            if(homePage.verifyRemarksCol(remarksColPath) && homePage.verifyFileALeaveIsAvailable(fileALeaveXPath)){
                homePage.clickFileALeave(fileALeaveXPath);
                fileALeavePage.populateCommentTextBox(sheetName, tcName+"1", column);
                fileALeavePage.clickCancelBtn();
            }
            counter++;
        }
    }

    @Test
    public void ContactNumberFieldAcceptsText(){
        initialization();
        column = "Contact Number";
        while(counter < homePage.getTotalDates() + 1){
            stringCounter = String.format("0%s",String.valueOf(counter));
            fileALeaveXPath = By.xpath(fileALeaveXPathStr.replace("counter", stringCounter));
            remarksColPath = By.xpath(remarksColPathStr.replace("counter", stringCounter));
            if(homePage.verifyRemarksCol(remarksColPath) && homePage.verifyFileALeaveIsAvailable(fileALeaveXPath)){
                homePage.clickFileALeave(fileALeaveXPath);
                fileALeavePage.populateContactNumberField(sheetName, tcName+"1", column);
                fileALeavePage.clickCancelBtn();
            }
            counter++;
        }
    }

    @Test
    public void SubmitAllEmptyFields(){
        initialization();
        while(counter < homePage.getTotalDates() + 1){
            stringCounter = String.format("0%s",String.valueOf(counter));
            fileALeaveXPath = By.xpath(fileALeaveXPathStr.replace("counter", stringCounter));
            remarksColPath = By.xpath(remarksColPathStr.replace("counter", stringCounter));
            if(homePage.verifyRemarksCol(remarksColPath) && homePage.verifyFileALeaveIsAvailable(fileALeaveXPath)){
                homePage.clickFileALeave(fileALeaveXPath);
                fileALeavePage.clickSubmitBtn();
                fileALeavePage.verifyMissingLeaveTypeError();
                fileALeavePage.verifyMissingReasonError();
                fileALeavePage.verifyMissingCommentError();
                fileALeavePage.verifyMissingContactNumberError();
                fileALeavePage.clickCancelBtn();
            }
            counter++;
        }
    }

    @Test
    public void SubmitOnePopulatedRequiredField(){
        initialization();
        while(counter < homePage.getTotalDates() + 1){
            stringCounter = String.format("0%s",String.valueOf(counter));
            fileALeaveXPath = By.xpath(fileALeaveXPathStr.replace("counter", stringCounter));
            remarksColPath = By.xpath(remarksColPathStr.replace("counter", stringCounter));
            if(homePage.verifyRemarksCol(remarksColPath) && homePage.verifyFileALeaveIsAvailable(fileALeaveXPath)){
                homePage.clickFileALeave(fileALeaveXPath);
                column = "Leave Type";
                fileALeavePage.selectLeaveType(sheetName, tcName+"1",column);
                fileALeavePage.clickSubmitBtn();
                fileALeavePage.verifyMissingReasonError();
                fileALeavePage.verifyMissingCommentError();
                fileALeavePage.verifyMissingContactNumberError();
                fileALeavePage.clickCancelBtn();
            }
            counter++;
        }
    }


    @Test
    public void SubmitTwoPopulatedRequiredFields(){
        initialization();
        while(counter < homePage.getTotalDates() + 1){
            stringCounter = String.format("0%s",String.valueOf(counter));
            fileALeaveXPath = By.xpath(fileALeaveXPathStr.replace("counter", stringCounter));
            remarksColPath = By.xpath(remarksColPathStr.replace("counter", stringCounter));
            if(homePage.verifyRemarksCol(remarksColPath) && homePage.verifyFileALeaveIsAvailable(fileALeaveXPath)){
                homePage.clickFileALeave(fileALeaveXPath);
                column = "Leave Type";
                fileALeavePage.selectLeaveType(sheetName, tcName+"1",column);
                column = "Reason";
                fileALeavePage.selectReason(sheetName, tcName+"1",column);
                fileALeavePage.clickSubmitBtn();
                fileALeavePage.verifyMissingCommentError();
                fileALeavePage.verifyMissingContactNumberError();
                fileALeavePage.clickCancelBtn();
            }
            counter++;
        }
    }

    @Test
    public void SubmitThreePopulatedRequiredFields(){
        initialization();
        while(counter < homePage.getTotalDates() + 1){
            stringCounter = String.format("0%s",String.valueOf(counter));
            fileALeaveXPath = By.xpath(fileALeaveXPathStr.replace("counter", stringCounter));
            remarksColPath = By.xpath(remarksColPathStr.replace("counter", stringCounter));
            if(homePage.verifyRemarksCol(remarksColPath) && homePage.verifyFileALeaveIsAvailable(fileALeaveXPath)){
                homePage.clickFileALeave(fileALeaveXPath);
                column = "Leave Type";
                fileALeavePage.selectLeaveType(sheetName, tcName+"1",column);
                column = "Reason";
                fileALeavePage.selectReason(sheetName, tcName+"1",column);
                column = "Comment";
                fileALeavePage.populateCommentTextBox(sheetName, tcName+"1", column);
                fileALeavePage.clickSubmitBtn();
                fileALeavePage.verifyMissingContactNumberError();
                fileALeavePage.clickCancelBtn();
            }
            counter++;
        }
    }

    @Test
	public void SubmitAllPopulatedRequiredFields() {
		initialization();
		// specific date only
		counter = 2;
		stringCounter = String.format("0%s", String.valueOf(counter));
		fileALeaveXPath = By.xpath(fileALeaveXPathStr.replace("counter", stringCounter));
		remarksColPath = By.xpath(remarksColPathStr.replace("counter", stringCounter));
		if (homePage.verifyRemarksCol(remarksColPath) && homePage.verifyFileALeaveIsAvailable(fileALeaveXPath)) {
			homePage.clickFileALeave(fileALeaveXPath);
			column = "Leave Type";
			String leaveType = fileALeavePage.selectLeaveType(sheetName, tcName + "1", column);
			column = "Reason";
			fileALeavePage.selectReason(sheetName, tcName + "1", column);
			column = "Comment";
			fileALeavePage.populateCommentTextBox(sheetName, tcName + "1", column);
			column = "Contact Number";
			fileALeavePage.populateContactNumberField(sheetName, tcName + "1", column);
			fileALeavePage.clickSubmitBtnAndWait();
			homePage.verifyLeaveIsSubmitted(remarksColPath, leaveType);
		}

	}
    
    @Test
    public void SubmitAllPopulatedRequiredFieldsNotEnoughBalance(){
        initialization();
        
		// specific date only
		counter = 2;
		stringCounter = String.format("0%s", String.valueOf(counter));
		fileALeaveXPath = By.xpath(fileALeaveXPathStr.replace("counter", stringCounter));
		remarksColPath = By.xpath(remarksColPathStr.replace("counter", stringCounter));
		if (homePage.verifyRemarksCol(remarksColPath) && homePage.verifyFileALeaveIsAvailable(fileALeaveXPath)) {
			homePage.clickFileALeave(fileALeaveXPath);
			column = "Leave Type";
			String leaveType = fileALeavePage.selectLeaveType(sheetName, tcName + "2", column);
			column = "Reason";
			fileALeavePage.selectReason(sheetName, tcName + "1", column);
			column = "Comment";
			fileALeavePage.populateCommentTextBox(sheetName, tcName + "1", column);
			column = "Contact Number";
			fileALeavePage.populateContactNumberField(sheetName, tcName + "1", column);
			fileALeavePage.clickSubmitBtnAndWait();
			fileALeavePage.verifyNotEnoughBalance(notEnoughCreditsError);
			fileALeavePage.clickOkInNotEnoughBalanceErrorMessage(notEnoughCreditsOkBtn);
			
			homePage.verifyLeaveIsSubmitted(remarksColPath, leaveType);
		}
	}
}
