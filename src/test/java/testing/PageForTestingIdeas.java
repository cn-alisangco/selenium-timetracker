package testing;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

import base.BaseClass;
import utilities.ExcelReader;

public class PageForTestingIdeas extends BaseClass {
	public static void main(String[] args) {
		ExcelReader creds = new ExcelReader("C:\\Users\\glenn.mamaril\\Documents\\GitHub\\selenium-timetracker\\testData_arch\\timetracker_glenn_v2.xlsx", "Time Logs");
		String id = "TC001_TimeTracker_TimeLogs_LogTimeIn";
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("M/d/yyyy");
    	
		String user = creds.testData(id, "username");
    	String pass = creds.testData(id, "password");
    	String date = creds.testData(id, "day");
    	String timeIn = creds.testData(id, "timein");
    	String timeOut = creds.testData(id, "timeout");
    	
    	System.out.println(timeIn);
    	System.out.println(timeOut);
    	LocalDate localDate = LocalDate.of(1899, 12, 30).plusDays((long) Integer.parseInt(date));
    	System.out.println(dtf.format(localDate));
    }
}
