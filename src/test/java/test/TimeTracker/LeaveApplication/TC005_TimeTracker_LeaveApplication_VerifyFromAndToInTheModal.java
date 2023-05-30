package test.TimeTracker.LeaveApplication;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import base.BaseClass;
import junit.framework.Assert;
import pageObjects.timetracker.HomePage;
import pageObjects.timetracker.v2.FileALeave_Modal;
import pageObjects.timetracker.v2.LoginPage;
import pageObjects.timetracker.v2.MyTimeLogsPage;
import utilities.ExcelReader;
import utilities.UserHelper;

public class TC005_TimeTracker_LeaveApplication_VerifyFromAndToInTheModal extends BaseClass {

	LoginPage loginPage;
	MyTimeLogsPage myTimeLogsPage;
	FileALeave_Modal fileALeaveModal;

	//Test Data
	String sheetName = "Login";
	String recordID = "valid_credentials";
	String dateFormat = "MM/dd/yyyy";

	private void initialize() {
		loginPage = PageFactory.initElements(getDriver(), LoginPage.class);
		myTimeLogsPage = PageFactory.initElements(getDriver(), MyTimeLogsPage.class);
		fileALeaveModal = PageFactory.initElements(getDriver(), FileALeave_Modal.class);
	}

	@Test
	public void TC005_TimeTracker_LeaveApplication_VerifyFromAndToInTheModal() {

		initialize();

		// Log in to Timetracker with valid credentials
		HashMap<String, String> loginCredentials = loginPage.getLoginCredentialsTestData(testDataLoc, sheetName,
				recordID);
		loginPage.login(loginCredentials.get("username"), loginCredentials.get("password"));

		// get all regular shift dates and store in a variable
		List<String> regularShiftDates = myTimeLogsPage.getRegularShiftDates("MM/dd/yyyy");
		System.out.println(regularShiftDates);
				
		// get all "File a Leave" links/button for regular shift dates and store in a variable
		List<WebElement> fileALeaveLinks = myTimeLogsPage.getAllFileALeaveButtons();

		

		// Iterate over each fileALeaveLink
		for (int i = 0; i < fileALeaveLinks.size(); i++) {
			
			//store regular shift date for current iteration
			String currentRegularShiftDate = regularShiftDates.get(i);

			//click the file a leave link with the same index as the regular shift date
			myTimeLogsPage.clickFileALeaveButton(i);

			//Verify File A Leave modal is displayed
			fileALeaveModal.verifyFileALeaveModalIsDisplayed();

			//get from and to date strings in the modal
			String fromDate = fileALeaveModal.getFromDate();
			String toDate = fileALeaveModal.getToDate();
			
			//Verify format of from and to dates in the modal is correct (i.e. MM/dd/yyyy)
			fileALeaveModal.isInCorrectDateFormat("From",  fromDate, dateFormat);
			fileALeaveModal.isInCorrectDateFormat("To", toDate, dateFormat);
			
			//Verify from and to dates is equal to the time log date
			fileALeaveModal.verifyFromDateIsEqualTimeLogDate(fromDate, currentRegularShiftDate);
			fileALeaveModal.verifyToDateIsEqualTimeLogDate(toDate, currentRegularShiftDate);
			
			//Close modal
			fileALeaveModal.clickCloseButton();

			//verify File A Leave modal is NOT displayed
			fileALeaveModal.verifyFileALeaveModalIsNotDislayed();
		}

	}

}
