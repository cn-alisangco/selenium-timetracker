package utilities;

public class TimeParser {
	private static String hour;
	private static String minute;
	private static String period;
	
	public TimeParser(String value) {
		if(value == null || value == "") {
			throw new Error("Value is either null or blank");
		}
		
		parser(value);
	}
	
	private static void parser(String timeValue) {
		String[] temp1 = timeValue.split(":");
		String[] temp2 = temp1[1].split(" ");
		
		hour = temp1[0];
		minute = temp2[0];
		period = temp2[1].toUpperCase();
		
		System.out.println(hour + minute + period);
	}
	
	public String getHour() {
		return hour;
	}
	
	public String getMinute() {
		return minute;
	}
	
	public String getPeriod() {
		return period;
	}
}
