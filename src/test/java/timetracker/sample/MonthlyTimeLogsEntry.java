package timetracker.sample;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import base.BaseClass;
import pageObjects.timetracker.v2.LoginPage;
import utilities.ExcelReader;

public class MonthlyTimeLogsEntry extends BaseClass {
	private LoginPage loginPage;

	private void initialize() {
		loginPage = PageFactory.initElements(getDriver(), LoginPage.class);
	}

	@Test
	public void execute() {
		initialize();
		/*
		 * ExcelReader eReader = new ExcelReader(System.getProperty("user.dir") +
		 * testDataLoc, "Login"); loginPage.login(
		 * eReader.testData("dean_login_credentials", "username"),
		 * eReader.testData("dean_login_credentials", "password") );
		 */

		ExcelReader timelogs = new ExcelReader(System.getProperty("user.dir") + testDataLoc, "Timelogs_202304");

//    	for(int i = 1; i <= timelogs.getDataRowSize(); i++ ) {
		String id = String.valueOf(1);
		System.out.println("TimeSheetPeriod: " + timelogs.testData(id, "TimeSheetPeriod"));
		System.out.println("Date:            " + timelogs.testData(id, "Date"));
		System.out.println("timeIn:          " + timelogs.testData(id, "timeIn"));
		System.out.println("timeOut:         " + timelogs.testData(id, "timeOut"));
		System.out.println("/*******************/");
		System.out.println(" ");
//    	}
	}
}
