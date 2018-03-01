package cn.damei.utils;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.joda.time.LocalDate;
public class DateUtils {
    private DateUtils() {
        super();
    }
    public static final String CHINESE_YYYY_MM_DD_HH_MM_SS = "yyyy年MM月dd日 hh:mm:ss";
    public static final String CHINESE_YYYY_MM = "yyyy年MM月";
    public static final String DATE_SMALL_STR = "yyyy-MM-dd";
    public static final String YYYY_MM = "yyyy-MM";
//    static final SimpleDateFormat YMD_SDF = new SimpleDateFormat(DATE_SMALL_STR);
//    static final SimpleDateFormat YMD_H_M_S_SDF = new SimpleDateFormat(DATE_FULL_STR);
//    static final SimpleDateFormat Y_M_SDF = new SimpleDateFormat(DATE_YYYY_MM_STR);
    public static Date parse(String date, String... patterns) {
        try {
            return org.apache.commons.lang3.time.DateUtils.parseDate(date, patterns);
        } catch (ParseException e) {
            return null;
        }
    }
    public static Date parse(Timestamp timestamp) {
        if (timestamp == null)
            return null;
        java.time.LocalDateTime localDateTime = timestamp.toLocalDateTime();
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return Date.from(instant);
    }
    public static Timestamp parse(Date date) {
        return new Timestamp((date).getTime());
    }
    public static String format(Date date, String patterns) {
        return DateFormatUtils.format(date, patterns);
    }
    public static Date getMondayByDate(Date date) {
        if (date == null)
            return null;
        LocalDate localDate = new LocalDate(date);
        int week = localDate.getDayOfWeek();
        return localDate.minusDays(week - 1).toDate();
    }
    public static Date getDateBeforOrAfterDate(Date date, int dateNum) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, dateNum);
        return calendar.getTime();
    }
    public static Date getDateBeforOrAfterDateStart(Date date, int dateNum) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, dateNum);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        return calendar.getTime();
    }
    public static Date getDateBeforOrAfterDateEnd(Date date, int dateNum) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, dateNum);
        calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND,59);
        return calendar.getTime();
    }
    private static String simpleDateFormat(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }
    public static String simpleDateForString(String date) {
        return date.indexOf("T") != -1 ? date.substring(0, date.indexOf("T")) + " " + date.substring(date.indexOf("T") + 1, date.indexOf(".")) : date;
    }
    public static Date getSundayByDate(Date date) {
        if (date == null)
            return null;
        LocalDate localDate = new LocalDate(date);
        int week = localDate.getDayOfWeek();
        return localDate.plusDays(7 - week).toDate();
    }
    public static int getWeek(Date date) {
        if (date != null) {
            LocalDate localDate = LocalDate.fromDateFields(date);
            return localDate.getDayOfWeek();
        }
        return 0;
    }
    public static List<Date> getIntervalDate(Date startDate, Date endDate, boolean containStart, boolean containEnd) {
        List<Date> list = new ArrayList<>();
        if (startDate != null && endDate != null) {
            LocalDate start = new LocalDate(startDate);
            LocalDate end = new LocalDate(endDate);
            if (start.compareTo(end) > 1)
                return list;
            if (containStart)
                list.add(startDate);
            if (start.compareTo(end) == 0)
                return list;
            boolean b = true;
            LocalDate d = start.plusDays(1);
            while (b) {
                if (d.compareTo(end) < 0) {
                    list.add(d.toDate());
                    d = d.plusDays(1);
                } else {
                    b = false;
                }
            }
            if (containEnd)
                list.add(endDate);
        }
        return list;
    }
    public static int daysOfTwo(Date fDate, Date oDate) {
        return Integer.parseInt(format(oDate, "yyyyMMdd")) - Integer.parseInt(format(fDate, "yyyyMMdd"));
    }
    public static Date lastMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }
    public static Date lastMonth(String dateString) {
        Date date = parse(dateString);
        if (date == null)
            return null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }
    public static String lastMonthString(String dateString, String pattern) {
        Date date = parse(dateString, pattern);
        if (date == null)
            return null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, -1);
        return format(cal.getTime(), pattern);
    }
    public static String formatDate(Date date) {
        if (date == null)
            return null;
        return new SimpleDateFormat(DATE_SMALL_STR).format(date);
    }
    public static boolean isFirstHalfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, Calendar.JULY);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.getTime().getTime();
        return date.getTime() < calendar.getTime().getTime();
    }
    public static Date getAssignMonthAndDay(Date date, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }
    public static Date getNextAssignMonthAndDay(Date date, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, 1);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }
}
