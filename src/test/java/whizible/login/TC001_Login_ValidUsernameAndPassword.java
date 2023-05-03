package whizible.login;

import base.BaseClass;
import reusables.whizible.LoginModule;
import utilities.ExcelReader;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(utilities.TestNGListeners.class)
public class TC001_Login_ValidUsernameAndPassword extends BaseClass {
	
    @Test
    public void execute(){
    	ExcelReader eReader = new ExcelReader(System.getProperty("user.dir") + testDataLoc, "Login");
    	LoginModule loginModule = new LoginModule(getDriver());

    	loginModule.login(
    			eReader.testData("TC001_Login_ValidUsernameAndPassword", "username"),
    			eReader.testData("TC001_Login_ValidUsernameAndPassword", "password")
    			);
    	loginModule.vfyLogin("ALL_VALID");
    }
}