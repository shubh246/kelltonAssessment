package question4;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;


public class DateTime {

	protected static int findDifference(LocalDate start_date, LocalDate end_date) {

		Period diff = Period.between(start_date, end_date);

		System.out.print("Difference " + "between two dates is: ");
		int experience = diff.getYears();

		if (diff.getMonths() >= 5) {
			experience += 1;
		}
		return experience;
	}

	public static void date_setup(String s) throws ParseException {
		String[] dates = s.split("-");
		String[] monthsName= dates[0].split(" ");
		Date date = (Date) new SimpleDateFormat("MMMM", Locale.ENGLISH).parse(monthsName[0]);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		int startMonths = cal.get(Calendar.MONTH);
		String[] endMonthsName= dates[1].split(" ");
		Date endDate = (Date) new SimpleDateFormat("MMMM", Locale.ENGLISH).parse(endMonthsName[0]);
		Calendar endcal = Calendar.getInstance();
		endcal.setTime(endDate);
		
		int endMonths = cal.get(Calendar.MONTH);

		LocalDate start_date = LocalDate.of(2004,02,01);

		// End date
		LocalDate end_date = LocalDate.of(2009, 12, 01);
		findDifference(start_date, end_date);
	}

	public static void main(String[] args) throws IOException, ParseException {
		File file = new File("C:\\Users\\HP\\Documents\\Datetime.txt");
		BufferedReader buffer = new BufferedReader(new FileReader(file));
		String line;
		while ((line = buffer.readLine()) != null) {
			date_setup(line);
		}}}

	

