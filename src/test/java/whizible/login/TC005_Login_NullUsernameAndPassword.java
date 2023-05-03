package whizible.login;

import base.BaseClass;
import reusables.whizible.LoginModule;
import utilities.ExcelReader;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(utilities.TestNGListeners.class)
public class TC005_Login_NullUsernameAndPassword extends BaseClass {
    @Test
    public void execute(){
    	ExcelReader eReader = new ExcelReader(System.getProperty("user.dir") + testDataLoc, "Login");
    	LoginModule loginModule = new LoginModule(getDriver());
    	loginModule.login(
    			eReader.testData("TC005_Login_NullUsernameAndPassword", "username"),
    			eReader.testData("TC005_Login_NullUsernameAndPassword", "password")
    			);
    	loginModule.vfyLogin("NULL_CREDENTIALS");
    }
}
