package test.Arch;

import base.BaseClass;
import pages.Arch.ReusablesWhizible_LoginModule;
import utilities.ExcelReader;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(utilities.TestNGListeners.class)
public class TestWhizibleLogin_TC001_Login_ValidUsernameAndPassword extends BaseClass {
	
    @Test
    public void execute(){
    	ExcelReader eReader = new ExcelReader(System.getProperty("user.dir") + testDataLoc, "Login");
    	ReusablesWhizible_LoginModule loginModule = new ReusablesWhizible_LoginModule(getDriver());

    	loginModule.login(
    			eReader.testData("TC001_Login_ValidUsernameAndPassword", "username"),
    			eReader.testData("TC001_Login_ValidUsernameAndPassword", "password")
    			);
    	loginModule.vfyLogin("ALL_VALID");
    }
}