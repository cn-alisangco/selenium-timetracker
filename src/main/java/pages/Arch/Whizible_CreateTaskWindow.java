package pages.Arch;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Whizible_CreateTaskWindow extends UserHelper {
    WebDriver driver;
    public String desc;
    public String methodName;
    public String date;
    public String project;
    public String task;
    public String taskType;
    public String priority;
    public String actualTime;
    By createTaskHeader = By.xpath("//*[text()='Create Task']");
    By dateField = By.xpath("//*[@id=\"CTselectdate\"]");
    By projectTab = By.xpath("//*[@id=\"createtaskmodal\"]/div[1]/div/div[2]/div/div/div/div[2]/div/div[2]/div/div[1]/div/button");
    By projectDropdown = By.xpath("//*[@id=\"cboProject\"]");
    By taskField = By.xpath("//*[@id=\"txtTaskName\"]");
    By taskTypeTab = By.xpath("//*[@id=\"createtaskmodal\"]/div[1]/div/div[2]/div/div/div/div[4]/div/div[2]/div/div[1]/div/button");
    By taskTypeDropdown = By.xpath("//*[@id=\"cboTaskType\"]");
    By priorityTab = By.xpath("//*[@id=\"createtaskmodal\"]/div[1]/div/div[2]/div/div/div/div[6]/div/div[2]/div/div[1]/div/button");
    By priorityDropdown = By.xpath("//*[@id=\"cboPriority\"]");
    By actualTimeField = By.xpath("//*[@id=\"txtWorkHrs\"]");
    By saveAndCreateNewBtn = By.xpath("//*[@id=\"btnSaveCreateTask\"]");
    By closeBtn = By.xpath("//*[@id=\"createtaskmodal\"]/div[1]/div/div[1]/button");

    public Whizible_CreateTaskWindow(WebDriver driver) {
        this.driver = driver;
    }

    public void verifyCreateTaskWindow(){
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        waitElementToLoad(createTaskHeader);
        moveAndHighlightElement(createTaskHeader);
        desc = "Successfully verified that the Create Task window is displayed";
        reportPass(methodName, desc);
    }

    public void populateDateField(String sheetName, String tcName, String columnName) {
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        waitElementToLoad(dateField);
        moveAndHighlightElement(dateField);
        date = sendKeys(dateField,sheetName, tcName, columnName);
        desc = String.format("Entered %s in the date field", date);
        reportPass(methodName, desc);
    }

    public void selectProject(String sheetName,String tcName,String columnName){
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        try{
            waitElementToLoad(projectTab);
            moveAndHighlightElement(projectTab);
            hover(projectTab);
            click(projectTab);
            project = selectByVisibleText(projectDropdown,sheetName, tcName, columnName);
            desc = String.format("User selected %s from the Project dropdown", project);
            reportPass(methodName, desc);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void populateTaskField(String sheetName, String tcName, String columnName) {
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        waitElementToLoad(taskField);
        moveAndHighlightElement(taskField);
        task = sendKeys(taskField,sheetName, tcName, columnName);
        desc = String.format("Entered %s in the task name field", task);
        reportPass(methodName, desc);
    }

    public void selectTask(String sheetName,String tcName,String columnName){
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        try{
            waitElementToLoad(taskTypeTab);
            moveAndHighlightElement(taskTypeTab);
            hover(taskTypeTab);
            click(taskTypeTab);
            taskType = selectByVisibleText(taskTypeDropdown,sheetName, tcName, columnName);
            desc = String.format("User selected %s from the Task Type dropdown", taskType);
            reportPass(methodName, desc);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void selectPriority(String sheetName,String tcName,String columnName){
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        try{
            waitElementToLoad(priorityTab);
            moveAndHighlightElement(priorityTab);
            hover(priorityTab);
            click(priorityTab);
            priority = selectByVisibleText(priorityDropdown,sheetName, tcName, columnName);
            desc = String.format("User selected %s from the Priority dropdown", priority);
            reportPass(methodName, desc);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void populateActualTimeField(String sheetName, String tcName, String columnName) {
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        waitElementToLoad(actualTimeField);
        moveAndHighlightElement(actualTimeField);
        actualTime = sendKeys(actualTimeField,sheetName, tcName, columnName);
        desc = String.format("Entered %s in the Actual Time field", actualTime);
        reportPass(methodName, desc);
    }

    public void clickSaveAndCreateNewBtn(){
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        waitElementToLoad(saveAndCreateNewBtn);
        moveAndHighlightElement(saveAndCreateNewBtn);
        hover(saveAndCreateNewBtn);
        click(saveAndCreateNewBtn);
        desc = "Clicked the Save and Create New button";
        reportPass(methodName, desc);
        Wait(10000);
    }

    public void clickCloseBtn(){
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        waitElementToLoad(closeBtn);
        moveAndHighlightElement(closeBtn);
        hover(closeBtn);
        click(closeBtn);
        desc = "Clicked the Closed button";
        reportPass(methodName, desc);
    }

    public void verifyTaskInTimesheet(String sheetName,String tcName,String columnName){
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        task = getExcelData(sheetName, tcName, columnName);
        By taskInTimesheet = By.xpath("//tr/td/div/span[text()='task']".replace("task", task));
        waitElementToLoad(taskInTimesheet);
        moveAndHighlightElement(taskInTimesheet);
        desc = String.format("Successfully verified that %s is displayed in the timesheet", task);
        reportPass(methodName, desc);
    }
}
