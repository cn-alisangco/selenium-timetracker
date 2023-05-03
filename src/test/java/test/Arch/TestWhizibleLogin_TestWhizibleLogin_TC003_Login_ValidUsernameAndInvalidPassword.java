package test.Arch;

import base.BaseClass;
import pages.Arch.ReusablesWhizible_LoginModule;
import utilities.ExcelReader;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
@Listeners(utilities.TestNGListeners.class)
public class TestWhizibleLogin_TestWhizibleLogin_TC003_Login_ValidUsernameAndInvalidPassword extends BaseClass {
    @Test
    public void execute(){
    	ExcelReader eReader = new ExcelReader(System.getProperty("user.dir") + testDataLoc, "Login");
    	ReusablesWhizible_LoginModule loginModule = new ReusablesWhizible_LoginModule(getDriver());
    	loginModule.login(
    			eReader.testData("TC003_Login_ValidUsernameAndInvalidPassword", "username"),
    			eReader.testData("TC003_Login_ValidUsernameAndInvalidPassword", "password")
    			);
    	loginModule.vfyLogin("INVALID_PASSWORD");
    }
}
