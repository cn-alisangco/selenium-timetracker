package utilities;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;

public class DateParser {
	public static int year;
	public static int month;
	public static int day;
	public static String fulldate;
	public static String period;
	
	public DateParser() {
		//Obtain current date
		LocalDateTime currentDate = LocalDateTime.now();
		/*----------setting date today----------
    	 * Pattern is <M/d/yyyy> in order to remove 0's from single-digit instances (1/1/2023 instead of 01/01/2023)
    	 * Pattern <MMMM yyyy> for choosing the period*/
    	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("M/d/yyyy"); 
    	DateTimeFormatter tsFormat = DateTimeFormatter.ofPattern("MMMM yyyy");
		
		//Get the individual components
		this.year = currentDate.getYear();
		this.month = currentDate.getMonthValue();
		this.day = currentDate.getDayOfMonth();
		this.fulldate = dtf.format(currentDate);
		this.period = tsFormat.format(currentDate);
	}
	
	public int getYear() {
		return year;
	}
	
	public int getMonth() {
		return month;
	}
	
	public int getDay() {
		return day;
	}
	
	public String getFullDate() {
		return fulldate;
	}
}
