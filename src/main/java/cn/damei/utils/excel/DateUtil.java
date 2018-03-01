package cn.damei.utils.excel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
public class DateUtil {
    public static final String DATE_JFP_STR="yyyyMM";
    public static final String DATE_FULL_STR = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FULL_STR_ = "yyyyMMddHHmmss";
    public static final String DATE_SMALL_STR = "yyyy-MM-dd";
    public static final String DATE_KEY_STR = "yyMMddHHmmss";
    public static final String DATE_YYYY_MM_STR = "yyyy-MM";
    static final SimpleDateFormat YMD_SDF = new SimpleDateFormat(DATE_SMALL_STR);
    static final SimpleDateFormat YMD_H_M_S_SDF = new SimpleDateFormat(DATE_FULL_STR);
    static final SimpleDateFormat Y_M_SDF = new SimpleDateFormat(DATE_YYYY_MM_STR);
    public static Date parseToDateTime(String strDate) {
        return parse(strDate,DATE_FULL_STR);
    }
    public static Date parseToDate(String strDate) {
        return parse(strDate,DATE_SMALL_STR);
    }
    public static Date parse(String strDate, String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        try {
            return df.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static int compareDateWithNow(Date date1){
        Date date2 = new Date();
        int rnum =date1.compareTo(date2);
        return rnum;
    }
    public static int compareDate(Date date1,Date date2){
    	 int rnum =date1.compareTo(date2);
         return rnum;
    }
    public static Long compareDate(String beginTime,String endTime){
    	SimpleDateFormat dfs = new SimpleDateFormat(DATE_FULL_STR);
        try{
        Date begin = dfs.parse(beginTime);
        Date end = dfs.parse(endTime);
        long between = (end.getTime() - begin.getTime()) / 1000;// 除以1000是为了转换成秒
        return between;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public static int compareDateWithNow(long date1){
        long date2 = dateToUnixTimestamp();
        if(date1>date2){
            return 1;
        }else if(date1<date2){
            return -1;
        }else{
            return 0;
        }
    }
    public static String getNowTime() {
        SimpleDateFormat df = new SimpleDateFormat(DATE_FULL_STR);
        return df.format(new Date());
    }
    public static String getNowTime(String type) {
        SimpleDateFormat df = new SimpleDateFormat(type);
        return df.format(new Date());
    }
    public static Date addDays(Date paramDate, int paramInt){  
        Calendar localCalendar = Calendar.getInstance();  
        localCalendar.setTime(paramDate);  
        int i = localCalendar.get(6);  
        localCalendar.set(6, i + paramInt);  
        return localCalendar.getTime();  
    }  
    public static String getJFPTime() {
        SimpleDateFormat df = new SimpleDateFormat(DATE_JFP_STR);
        return df.format(new Date());
    }
    public static long dateToUnixTimestamp(String date) {
        long timestamp = 0;
        try {
            timestamp = new SimpleDateFormat(DATE_FULL_STR).parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timestamp;
    }
    public static long dateToUnixTimestamp(String date, String dateFormat) {
        long timestamp = 0;
        try {
            timestamp = new SimpleDateFormat(dateFormat).parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timestamp;
    }
    public static String dateToUnixTimestamp(Date date, String dateFormat) {
        String timestamp = "0";
        try {
        	timestamp = new SimpleDateFormat(dateFormat).format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return timestamp;
    }
    public static long dateToUnixTimestamp() {
        long timestamp = new Date().getTime();
        return timestamp;
    }
    public static String unixTimestampToDate(long timestamp) {
        SimpleDateFormat sd = new SimpleDateFormat(DATE_FULL_STR);
        sd.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return sd.format(new Date(timestamp));
    }
    public static Date addNDate(Date date, int n) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date == null ? new Date() : date);
        c1.add(Calendar.DATE, n);
        return c1.getTime();
    }
    public static String formatDate(Date date) {
        if (date == null)
            return null;
        return new SimpleDateFormat(DATE_SMALL_STR).format(date);
    }
}
