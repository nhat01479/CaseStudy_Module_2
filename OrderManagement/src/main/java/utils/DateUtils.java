package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    static SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
    static SimpleDateFormat formatterIgnoreTime = new SimpleDateFormat("dd-MM-yyyy");
    public static String format(Date date){
        return formatter.format(date);
    }
    public static Date parse(String strDate){
        try {
            return formatter.parse(strDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    public static String formatIgnoreTime(Date date){
        return formatter.format(date);
    }

    public static Date parseIgnoreTime(String strDate){
        try {
            return formatter.parse(strDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    public static boolean isDateInRange(Date targetDate, Date startDate, Date endDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(targetDate);
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(startDate);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(endDate);
        return calendar.after(startCalendar) && calendar.before(endCalendar)
                || calendar.equals(startCalendar) || calendar.equals(endCalendar);
    }
    public static boolean isDateInRangeIgnoreTime(Date targetDate, Date startDate, Date endDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(targetDate);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(startDate);
        startCalendar.set(Calendar.HOUR_OF_DAY, 0);
        startCalendar.set(Calendar.MINUTE, 0);
        startCalendar.set(Calendar.SECOND, 0);
        startCalendar.set(Calendar.MILLISECOND, 0);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(endDate);
        endCalendar.set(Calendar.HOUR_OF_DAY, 0);
        endCalendar.set(Calendar.MINUTE, 0);
        endCalendar.set(Calendar.SECOND, 0);
        endCalendar.set(Calendar.MILLISECOND, 0);
        return calendar.after(startCalendar) && calendar.before(endCalendar)
                || calendar.equals(startCalendar) || calendar.equals(endCalendar);
    }
}
