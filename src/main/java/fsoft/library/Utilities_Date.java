package fsoft.library;

import java.text.*;
import java.util.*;

public class Utilities_Date {
	public static String getDate(String format) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public static String getDate() {
		return Utilities_Date.getDate("dd/MM/yyyy");
	}
	
	public static String getDateProfiles() {
		return Utilities_Date.getDate("ddMMyyHHmmss");
	}
}
