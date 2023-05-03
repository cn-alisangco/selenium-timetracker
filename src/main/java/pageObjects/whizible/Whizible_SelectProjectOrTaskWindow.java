package pageObjects.whizible;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import userHelper.UserHelper;

public class Whizible_SelectProjectOrTaskWindow extends UserHelper {
    WebDriver driver;
    public String desc;
    public String methodName;
    private static String project;
    public String taskStr;
    By selectProjectOrTaskHeader = By.xpath("//*[@id='selectprojecttask']/div[1]/div/div[1]/h4");
    By projectSelectTab = By.xpath("//*[@id='selectprojecttask']/div[1]/div/div[2]/div/div[1]/div/div/div/button");
    By projectDropdown = By.xpath("//*[@id='cboFilteredProject']");
    By selectBtn = By.xpath("//*[@id=\"selectprojecttask\"]/div[1]/div/div[4]/button[2]");

    public Whizible_SelectProjectOrTaskWindow(WebDriver driver) {
        this.driver = driver;
    }

    public void verifySelectProjectOrTaskWindow(){
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        waitElementToLoad(selectProjectOrTaskHeader);
        moveAndHighlightElement(selectProjectOrTaskHeader);
        desc = "Successfully verified that the Select Project or Task window is displayed";
        reportPass(methodName, desc);
    }

    public void selectProject(String sheetName,String tcName,String columnName){
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        try{
            waitElementToLoad(projectSelectTab);
            moveAndHighlightElement(projectSelectTab);
            hover(projectSelectTab);
            click(projectSelectTab);
            setProject(selectByVisibleText(projectDropdown,sheetName, tcName, columnName));
            desc = String.format("User selected %s from the Project dropdown", project);
            reportPass(methodName, desc);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void clickTaskCheckbox(String sheetName,String tcName,String columnName){
        taskStr = getDataFromExcel(sheetName, tcName, columnName);
        By task = By.xpath("//div[text()='task']".replace("task", taskStr));
        By taskCheckbox = By.xpath("//div[text()='task']/../preceding-sibling::td/div[@class='custom_chckbox']".replace("task", taskStr));
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        waitElementToLoad(taskCheckbox);
        moveAndHighlightElement(taskCheckbox);
        hover(taskCheckbox);
        click(taskCheckbox);
        desc = String.format("Clicked the %s task checkbox", getText(task));
        reportPass(methodName, desc);
    }

    public void clickSelectBtn(){
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        waitElementToLoad(selectBtn);
        moveAndHighlightElement(selectBtn);
        hover(selectBtn);
        click(selectBtn);
        desc = "Clicked the Select button";
        reportPass(methodName, desc);
        Wait(10000);
    }

	public static String getProject() {
		return project;
	}

	public static void setProject(String project) {
		Whizible_SelectProjectOrTaskWindow.project = project;
	}
}
