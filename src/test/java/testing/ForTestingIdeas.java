package testing;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

import base.BaseClass;
import utilities.DateParser;
import utilities.ExcelReader;

public class ForTestingIdeas extends BaseClass {
	public static void main(String[] args) {
		ExcelReader creds = new ExcelReader("C:\\Users\\glenn.mamaril\\Documents\\GitHub\\selenium-timetracker\\testData_arch\\timetracker_glenn_v2.xlsx", "Time Logs");
		String id = "TC001_TimeTracker_TimeLogs_LogTimeIn";
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("M/d/yyyy");
    	
//		String user = creds.testData(id, "username");
//    	String pass = creds.testData(id, "password");
//    	String project = creds.testData(id, "project");
//    	String milestone = creds.testData(id, "milestone");
//    	String taskName = creds.testData(id, "taskname");
//    	
//    	String actualHours = creds.testData(id, "actualHours");
//    	String typeOfWork = creds.testData(id, "typeOfWork");
//    	String remarks = creds.testData(id, "remarks");
//    	
//    	System.out.println(user);
//    	System.out.println(pass);
//    	System.out.println(project);
//    	System.out.println(milestone);
//    	System.out.println(taskName);
//    	System.out.println(actualHours);
//    	System.out.println(typeOfWork);
//    	System.out.println(remarks);
    	
//    	String date = creds.testData(id, "day");
//    	String timeIn = creds.testData(id, "timein");
//    	String timeOut = creds.testData(id, "timeout");
//    	System.out.println(timeOut);
//    	LocalDate localDate = LocalDate.of(1899, 12, 30).plusDays((long) Integer.parseInt(date));
//    	System.out.println(dtf.format(localDate));
    	
    	DateParser datetime = new DateParser();
    	System.out.println(datetime.month);
    	System.out.println(datetime.day);
    	System.out.println(datetime.year);
    	System.out.println(datetime.fulldate);
    	System.out.println(datetime.period);
    }
}
