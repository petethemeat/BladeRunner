package UserInterface;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CurrentDate {
	
	public static String getCurrentDate(){
		DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dateclass = new Date();
		String date = dateformat.format(dateclass);
		return date;
	}
}
