package test;

import java.lang.reflect.Method;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import base.BaseClass;
import pageObjects.timetracker.HomePage;
import pageObjects.timetracker.LoginPage;

@Listeners(utilities.TestNGListeners.class) //need to uncomment this when running this TC only
public class TimeTracker_Login extends BaseClass {

    LoginPage loginPage;
    HomePage homePage;
    public static String sheetName = "Login"; // sheet name of the test data to be used
    public static String tcName = "TimeTracker_Login"; // test case name
    public static String username;
    public static String password;

    public void loginToTimeTracker(String username, String password){
        loginPage = new LoginPage(getDriver());
        homePage = new HomePage(getDriver());
        //loginPage.proceedToLoginPage();
        loginPage.setUsername(sheetName,tcName,username);
        loginPage.setPassword(sheetName,tcName,password);
        loginPage.clickLogin();
        if(username.toUpperCase().equals("VALID USERNAME") && password.toUpperCase().equals("VALID PASSWORD")){
            homePage.verifySuccessfulLogin();
        } else if(username.toUpperCase().equals("NULL") && password.toUpperCase().equals("NULL")){
            loginPage.verifyNullFields();
        } else{
            loginPage.verifyLoginErrorMessage();
        }
    }

    @Test
    public void ValidUsernameAndPassword() {
        username = "Valid Username";
        password = "Valid Password";
        loginToTimeTracker(username, password);
    }

    @Test
    public void InvalidUsernameAndValidPassword() {
        username = "Invalid Username";
        password = "Valid Password";
        loginToTimeTracker(username, password);
    }

    @Test
    public void ValidUsernameAndInvalidPassword() {
        username = "Valid Username";
        password = "Invalid Password";
        loginToTimeTracker(username, password);
    }

    @Test
    public void InvalidUsernameAndPassword() {
        username = "Invalid Username";
        password = "Invalid Password";
        loginToTimeTracker(username, password);
    }

    @Test
    public void NullUsernameAndPassword() {
        username = "Null";
        password = "NUll";
        loginToTimeTracker(username, password);
    }

    @AfterMethod
    public String getTcName(Method method){
        return method.getName();
    }
    
}
