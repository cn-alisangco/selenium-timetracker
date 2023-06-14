package pageObjects.timetracker.v2;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import utilities.UserHelper;

public class DailyWorkHours extends UserHelper {
	WebDriver driver;
    
    /*---------Daily Work Hours----------*/
	//window options
    @FindBy(id="ui-dialog-title-taskLog-modal")
    WebElement dailyWorkHours;
    @FindBy(xpath="/html/body/div[9]/div[1]/a/span")
    WebElement closeDailyWorkHours;
    //day
    @FindBy(id="prevDay")
    WebElement prevDayArrow;
    @FindBy(id="nextDay")
    WebElement nextDayArrow;
    @FindBy(xpath="//*[@id=\"summaryHours\"]/tbody/tr[1]/td[2]")
    WebElement currentMonthYear;
    @FindBy(xpath="//*[@id=\"summaryHours\"]/tbody/tr[2]/td[2]")
    WebElement currentDay;
    //tasks bar
    @FindBy(id="ddlProjects")
    WebElement projectsDropdown;
    @FindBy(id="ddlDMS")
    WebElement deliveryMilestone;
    @FindBy(id="todayTask")
    WebElement tasksWithLogs;
    @FindBy(id="active")
    WebElement activeTasks;
    @FindBy(id="all")
    WebElement allTasks;
    @FindBy(id="general")
    WebElement generalTasks;
    //Actual Work Hours
    @FindBy(id="currentTask")
    WebElement currentTask;
    @FindBy(id="logHours")
    WebElement logHours;
    @FindBy(id="ddlDataType")
    WebElement typeOfWork;
    @FindBy(xpath="//*[@id=\"sideBar\"]/div[3]/div[3]")//*[@id="remarks"]
    WebElement remarks;
    @FindBy(id="saveHours")
    WebElement saveHours;
    
    /*----------Warning Pop-up----------*/
    @FindBy(id="ui-dialog-title-dialog-modal-confirm")
    WebElement warningPopup;
    @FindBy(xpath="/html/body/div[5]/div[3]/div/button[1]")
    WebElement warningOk;
    @FindBy(xpath="/html/body/div[5]/div[3]/div/button[2]")
    WebElement warningCancel;
    
	public DailyWorkHours(WebDriver driver) {
		this.driver = driver;
	}
    
	public void verifyInputWhizExists() {
		waitForElement(dailyWorkHours);
        boolean popupSuccess = dailyWorkHours!=null;
        if(!popupSuccess) {
    		throw new Error("Pop-up does not appear!");
    	}
        reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Verified Enter Hours pop-up exists");
	}
	
	public void closeInputWhizHours() {
		waitForElement(closeDailyWorkHours);
	    JavascriptExecutor executor = (JavascriptExecutor)driver;
	    executor.executeScript("arguments[0].click()", closeDailyWorkHours); //JSE used as regular click results in 'not steerable' error
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Closed Enter Hours pop-up");
	}
	
	public void chooseProjectFromDropdown(String project) {
		waitForElement(projectsDropdown);
		selectByVisibleText(projectsDropdown,project);
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Selected project from dropdown");
	}
	
	public void chooseMilestoneFromDropdown(String milestone) {
		waitForElement(deliveryMilestone);
		selectByVisibleText(deliveryMilestone,milestone);
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Selected milestone from dropdown");
	}
	
	public void clickTab(String tab) {
		switch(tab) {
			case "General Tasks":
				waitForElement(generalTasks);
				generalTasks.click();
				break;
			case "Task With Logs":
				waitForElement(tasksWithLogs);
				tasksWithLogs.click();
				break;
			case "Active Tasks":
				waitForElement(activeTasks);
				activeTasks.click();
				break;
			case "All Tasks":
				waitForElement(allTasks);
				allTasks.click();
				break;
			default:
				throw new Error("Tab to be clicked does not exist!");
		}
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Clicked task");
	}
	
	public String chooseRandomTask(int chosenLimit) {
		waitForElement(generalTasks);
		Random random = new Random();
		Actions actions = new Actions(driver);
		/*Randomize selection of task, defined by chosen limit
		 * uses Actions library as regular click results in 'not steerable' error*/
		String randomTask = String.valueOf(random.nextInt(chosenLimit) + 1);
		WebElement generalTask = driver.findElement(By.xpath("//*[@id=\"taskTableDetails\"]/tbody/tr["+randomTask+"]/td[1]/div/div[1]/span"));
		actions.click(generalTask).build().perform();
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Chose a General Task");
		return randomTask;
	}
	
	public void handleWarningPopup() {
		/*Check if warning popup appears after clicking Save
		 * if popup appears, dismiss the warning by clicking OK
		 * if no error appears, do nothing*/
		if(warningPopup!=null) {
			waitForElement(warningOk);
			warningOk.click();
			reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Dismissed warning popup");
		}else {
			reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "No warning popup appeared");
		}
	}
	
	public void enterActualHours(String actualHours) {
		waitForElement(logHours);
		logHours.clear();
		logHours.sendKeys(actualHours);
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Entered Actual Work Hours");
	}
	
	public void chooseTypeOfWork(String workType) {
		waitForElement(typeOfWork);
		moveAndHighlightElement(typeOfWork);
		selectByVisibleText(typeOfWork,workType);
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Selected type of work");
	}
	
	public void enterTaskDescription(String description) {
		/*Used Actions tab as JSE and regular click function does not work correctly*/
		Actions actions = new Actions(driver);
		actions.click(remarks).sendKeys(description).build().perform();
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Entered Description");
	}
	
	public void clickSave() {
		waitForElement(saveHours);
		saveHours.click();
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Clicked Save Hours");
	}
	
	public int getActualHours(String task) {
		WebElement actual = driver.findElement(By.xpath("//*[@id=\"taskTableDetails\"]/tbody/tr["+task+"]/td[3]"));
		int actualHours = Integer.valueOf(actual.getText());
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Obtained Actual Hours of chosen task");
		return actualHours;
	}
	
	public int getLoggedHours(String task) {
		WebElement logged = driver.findElement(By.xpath("//*[@id=\"taskTableDetails\"]/tbody/tr["+task+"]/td[4]"));
		int loggedHours = Integer.valueOf(logged.getText());
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Obtained Logged Hours of chosen task");
		return loggedHours;
	}
	
	public void verifyAllocatedHours(String randomTask) {
		WebElement allocatedHours = driver.findElement(By.xpath("//*[@id=\"taskTableDetails\"]/tbody/tr["+randomTask+"]/td[2]"));
		boolean isThereHours = allocatedHours.getText()!=null;
		if(!isThereHours) {
			throw new Error("No allocated hours!");
		}
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Verified task has allocated hours");
	}
	
	public void verifyTaskChosen() {
		waitForElement(currentTask);
		boolean isTaskSelected = currentTask!=null;
		if(!isTaskSelected) {
			throw new Error("Task not chosen!");
		}
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Verified correct task selected");
	}
	
	public void verifySelectedTasks() {
		/**verifies that task tab has been selected by checking if the first task exists*/
		boolean inTasks = driver.findElement(By.xpath("//*[@id=\"taskTableDetails\"]/tbody/tr[1]/td[1]/div/div[1]/span"))!=null;
		if(!inTasks) {
			throw new Error("No Tasks!");
		}
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Verified correct tab selected");
	}
	
	public void verifyChosenProject(String project) {
		waitForElement(projectsDropdown);
		Select select = new Select(projectsDropdown);
		String currentProject = select.getFirstSelectedOption().getText();
		boolean isSame = currentProject.equals(project);
		if(!isSame) {
			throw new Error("Different project!");
		}
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Verified correct chosen project");
	}
	
	public void verifyChosenMilestone(String milestone) {
		waitForElement(deliveryMilestone);
		Select select = new Select(deliveryMilestone);
		String currentMilestone = select.getFirstSelectedOption().getText();
		boolean isSame = currentMilestone.equals(milestone);
		if(!isSame) {
			throw new Error("Different milestone!");
		}
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Verified correct chosen milestone/deliverables");
	}
	
	public void verifyProperDay(String monthYear,int dayOfMonth) {
		waitForElement(currentMonthYear);
		waitForElement(currentDay);
		String dwhMonthYear = currentMonthYear.getText();
		String dwhDay = currentDay.getText();
		boolean isSameMonthYear = dwhMonthYear.equals(dwhMonthYear);
		boolean isSameDay = dwhDay.equals(Integer.toString(dayOfMonth));
		if(!isSameMonthYear) {
			throw new Error("Not same month and/or year!");
		}
		if(!isSameDay) {
			throw new Error("Not same day!");
		}
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Verified same day as clicked");
	}
	
	public void verifyAddedHours(int originalActualHours, int originalLoggedHours,int enteredHours, String task) {
		
		WebElement actual = driver.findElement(By.xpath("//*[@id=\"taskTableDetails\"]/tbody/tr["+task+"]/td[3]"));
		WebElement logged = driver.findElement(By.xpath("//*[@id=\"taskTableDetails\"]/tbody/tr["+task+"]/td[4]"));
		moveAndHighlightElement(actual);
		int actualHours = Integer.valueOf(actual.getText()); //get the text from the element and converts it to integer type
		moveAndHighlightElement(logged);
		int loggedHoursForToday = Integer.valueOf(logged.getText()); //get the text from the element and converts it to integer type
		/*		VERIFICATION STEP
		 * 		if the entered hours were entered correctly into the system,
		 * 		<original hours> + <hours entered> should be equal to <the total number of hours currently displayed> */
		boolean isAddedtoActual = (originalActualHours+enteredHours)==actualHours;
		boolean isAddedtologgedHours = (originalLoggedHours+enteredHours)==loggedHoursForToday;
		//if any of the above conditions are false, throw an error
		if(!isAddedtoActual) {
			throw new Error("Changes not reflected in Actual hours!");
		}else if(!isAddedtologgedHours) {
			throw new Error("Changes not reflected in Logged hours!");
		}
		reportPass(Thread.currentThread().getStackTrace()[1].getMethodName(), "Verified changes reflected in fields");
	}
}
