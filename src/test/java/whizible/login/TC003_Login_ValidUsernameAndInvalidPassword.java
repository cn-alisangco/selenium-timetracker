package whizible.login;

import base.BaseClass;
import reusables.whizible.LoginModule;
import utilities.ExcelReader;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
@Listeners(utilities.TestNGListeners.class)
public class TC003_Login_ValidUsernameAndInvalidPassword extends BaseClass {
    @Test
    public void execute(){
    	ExcelReader eReader = new ExcelReader(System.getProperty("user.dir") + testDataLoc, "Login");
    	LoginModule loginModule = new LoginModule(getDriver());
    	loginModule.login(
    			eReader.testData("TC003_Login_ValidUsernameAndInvalidPassword", "username"),
    			eReader.testData("TC003_Login_ValidUsernameAndInvalidPassword", "password")
    			);
    	loginModule.vfyLogin("INVALID_PASSWORD");
    }
}
