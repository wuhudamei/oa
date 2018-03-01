package cn.damei.utils;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
public final class DateUtil {
	public static final String YYYYMM_PATTERN = "yyyyMM";
	public static final String YYYY_MM_PATTERN = "yyyy-MM";
	public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_PATTERN = "yyyy-MM-dd";
	public static final String YYMMDDHHMMSS_PATTERN = "yyMMddHHmmss";
	private static final Object lockObj = new Object();
	private static Map<String, ThreadLocal<SimpleDateFormat>> sdfMap = new HashMap<String, ThreadLocal<SimpleDateFormat>>();
	public static Date parseToDateTime(String dateTime) {
		return parse(dateTime, DATE_TIME_PATTERN);
	}
	public static Date parseToDate(String date) {
		return parse(date, DATE_PATTERN);
	}
	public static Date parse(String strDate, String pattern) {
		SimpleDateFormat df = getSdf(pattern);
		try {
			return df.parse(strDate);
		} catch (ParseException e) {
			return null;
		}
	}
	public static int getIntervalDays(Date startDate, Date endDate) {
		long intervalMills = Math.abs(endDate.getTime() - startDate.getTime());
		return (int) Math.round(intervalMills * 1.0 / DateUtils.MILLIS_PER_DAY);
	}
	public static String formatDate(Date date) {
		if (date == null)
			return null;
		return getSdf(DATE_PATTERN).format(date);
	}
	public static String formatToYearMonth(Date date) {
		if (date == null)
			return null;
		return getSdf(YYYY_MM_PATTERN).format(date);
	}
	public static String formatDate(Date date, String pattern) {
		return getSdf(pattern).format(date);
	}
	//获得本周 周五的日期 yyyy-MM-dd
	public static String getFridayOfThisWeek(Date valueDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(valueDate);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek() + 4);
		return formatDate(cal.getTime());
	}
	//获得上周五 日期 格式： yyyy-MM-dd
	public static String getLastFridayDate(Date valueDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(valueDate);
		cal.add(Calendar.WEEK_OF_MONTH, -1);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
		return formatDate(cal.getTime());
	}
	//获得日期valueDate是星期几
	public static int getDayOfWeek(Date valueDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(valueDate);
		//星期几
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		return dayOfWeek;
	}
	public static String reduceOneYear(String yearMonth) {
		if (yearMonth.length() < 6)
			return StringUtils.EMPTY;
		String result = null;
		String yearString = yearMonth.substring(0, 4);
		int year = NumberUtils.toInt(yearString);
		year--;
		String month = yearMonth.substring(yearString.length());
		result = year + month;
		return result;
	}
	//计算两个日期间隔  月的个数 四舍五入
	public static int getMonthSpace(Date startDate, Date endDate) throws ParseException {
		if (endDate.before(startDate))
			return 0;
		long intervalMills = endDate.getTime() - startDate.getTime();
		long oneMonth = DateUtils.MILLIS_PER_DAY * 30;
		return (int) Math.round(intervalMills * 1.0 / oneMonth);
	}
	public static String getLastYearMonth() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, -1);
		return formatDate(cal.getTime(), YYYY_MM_PATTERN);
	}
	public static String getFirstDayOfLastMonth() {
		return getLastYearMonth() + "-01";
	}
	public static String getLastDayOfLastMonth() {
		Calendar cal = Calendar.getInstance();
		//设置月份
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1);
		//获取某月最大天数
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		//设置日历中月份的最大天数
		cal.set(Calendar.DAY_OF_MONTH, lastDay);
		//格式化日期
		return formatDate(cal.getTime());
	}
	public static Date nearYearDate(Date date, int negValue) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date == null ? new Date() : date);
		cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) + negValue);
		return cal.getTime();
	}
	public static long getYearSpace(Date start, Date end) {
		if (end.before(start))
			return 0;
		long interval = end.getTime() - start.getTime();
		return (interval) / (365 * DateUtils.MILLIS_PER_DAY);
	}
	public static Date addNDate(Date date, int n) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date == null ? new Date() : date);
		c1.add(Calendar.DATE, n);
		return c1.getTime();
	}
	public static String getLastYearMonth(String yearMonth) {
		Date date = parse(yearMonth, YYYY_MM_PATTERN);
		return getLastYearMonth(date);
	}
	public static String getLastYearMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, -1);
		return formatToYearMonth(cal.getTime());
	}
	public static String getNextYearMonth(Date date) {
		if (date == null)
			return null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, 1);
		return formatToYearMonth(cal.getTime());
	}
	public static String getLastDayOfMonth(String yearMonth) {
		Date date = parse(yearMonth, YYYY_MM_PATTERN);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, 1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		Date lastDayOfMonth = cal.getTime();
		return formatDate(lastDayOfMonth);
	}
	public static boolean isLastDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DATE, (calendar.get(Calendar.DATE) + 1));
		if (calendar.get(Calendar.DAY_OF_MONTH) == 1) {
			return true;
		}
		return false;
	}
	public static boolean isFriday(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY;
	}
	public static Date getNearFriday(Date now) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);//获取最近的周五
		Date nearFriday = cal.getTime();
		//如果最近的周五比现在还大  先减去
		if (nearFriday.getTime() > now.getTime()) {
			nearFriday = DateUtil.addNDate(nearFriday, -7);
		}
		return nearFriday;
	}
	public static List<Date> getFridayOrLastDayOfMonthListBetween(Date startDate, Date endDate) {
		List<Date> resultDateList = new ArrayList<Date>();
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		while (!(cal.getTime().after(endDate))) {
			//如果 startDate<=endDate  都返回
			Date date = cal.getTime();
			if (isFriday(date) || isLastDayOfMonth(date)) {
				resultDateList.add(date);
			}
			cal.add(Calendar.DAY_OF_YEAR, 1);
		}
		return resultDateList;
	}
	public static int getYear(Date date) {
		if (date == null)
			return 0;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}
	public static List<String> getTwelveMonthString() {
		List<String> result = Lists.newArrayList();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		for (int i = 0; i < 12; i++) {
			result.add(DateUtil.formatToYearMonth(calendar.getTime()));
			calendar.add(Calendar.MONTH, +1);
		}
		return result;
	}
	private static SimpleDateFormat getSdf(final String pattern) {
		ThreadLocal<SimpleDateFormat> sdfHolder = sdfMap.get(pattern);
		// 此处的双重判断和同步是为了防止sdfMap这个单例被多次put重复的sdf
		if (sdfHolder == null) {
			synchronized (lockObj) {
				sdfHolder = sdfMap.get(pattern);
				if (sdfHolder == null) {
					// 只有Map中还没有这个pattern的sdf才会生成新的sdf并放入map
					// 这里是关键,使用ThreadLocal<SimpleDateFormat>替代原来直接new SimpleDateFormat
					sdfHolder = new ThreadLocal<SimpleDateFormat>() {
						@Override
						protected SimpleDateFormat initialValue() {
							return new SimpleDateFormat(pattern);
						}
					};
					sdfMap.put(pattern, sdfHolder);
				}
			}
		}
		return sdfHolder.get();
	}
	public static void main(String[] args) {
		Date startDate = parseToDate("2016-06-26");
		System.out.println(getFridayOfThisWeek(startDate));
	}
}
