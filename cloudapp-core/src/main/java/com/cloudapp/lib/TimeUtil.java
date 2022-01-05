package com.cloudapp.lib;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public abstract class TimeUtil {
	private static SimpleDateFormat dateFormat = new SimpleDateFormat();

	private static Date tempDate = new Date();

	/**
	 * @return 当前时间对象
	 */
	public static Date now() {
		return new Date(Calendar.getInstance().getTimeInMillis());
	}

	/**
	 * @return 当前时间对象
	 */
	public static Timestamp nowTime() {
		return new Timestamp(Calendar.getInstance().getTimeInMillis());
	}
	
	public static Date parseDateFormat(String strDate, String pattern)
			throws ParseException {
		Date date = null;
		dateFormat.applyPattern(pattern);
		date = dateFormat.parse(strDate);
		return date;
	}

	public static Date parseDateFormat(String strDate) throws ParseException {
		String pattern = "yyyy-MM-dd";
		return parseDateFormat(strDate, pattern);
	}

	public static Date parseTimeFormat(String strDate) throws ParseException {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		return parseDateFormat(strDate, pattern);
	}

	/**
	 * @return 当前的日期对象
	 */
	public static Date today() {
		Date t = now();
		getDate(t);
		return t;
	}

	/**
	 * 创建一个时间对象，以传入时间为基准变动其中的某个域
	 * 
	 * @param t
	 *            基准时间
	 * @param f
	 *            变化域
	 * @param ci
	 *            变化量
	 * 
	 * @return 变动后的新建的时间对象
	 */
	public static Date createConversionCal(Date t, int f, int ci) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(t);
		cal.set(f, cal.get(f) + ci);
		return new Date(cal.getTimeInMillis());
	}

	/**
	 * 创建一个时间对象，以传入时间为基准变动数日
	 * 
	 * @param t
	 *            基准时间
	 * @param ci
	 *            日期变动量
	 * 
	 * @return 变动后的新建的时间对象
	 */
	public static Date createConversionDate(Date t, int ci) {
		return createConversionCal(t, Calendar.DATE, ci);
	}

	/**
	 * @param t
	 *            基准时间
	 * @param f
	 *            变化域
	 * @param si
	 *            设置的时间
	 */
	public static void setFiled(Date t, int f, int si) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(t);
		cal.set(f, si);
		t.setTime(cal.getTimeInMillis());
	}

	/**
	 * 将日期对象格式化为日期标准格式的字符串
	 * 
	 * @param date
	 *            待格式化的日期对象
	 * @return
	 */
	public static String formatDate(Date date) {
		dateFormat.applyPattern("yyyy-MM-dd");
		return dateFormat.format(date);
	}

	/**
	 * 将日期对象格式化为日期、时间标准格式的字符串
	 * 
	 * @param date
	 *            待格式化的日期对象
	 * @return 格式化为日期、时间格式的字符串
	 */
	public static String formatDateTime(Date date) {
		dateFormat.applyPattern("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(date);
	}

	/**
	 * 将日期对象格式化为特定格式的字符串
	 * 
	 * @param date
	 *            待格式化的日期对象
	 * @param pattern
	 *            格式化标准
	 * @return
	 */
	public static String formatDate(Date date, String pattern) {
		dateFormat.applyPattern(pattern);
		return dateFormat.format(date);
	}

	/**
	 * 将长整型数据作为日期信息格式化为日期格式的字符串
	 * 
	 * @param d
	 *            待格式化的长整型
	 * @return
	 */
	public static String formatDate(long d) {
		tempDate.setTime(d);
		return formatDate(tempDate);
	}

	/**
	 * 将长整型数据作为日期信息格式化为日期、时间格式的字符串
	 * 
	 * @param t
	 *            待格式化的长整型
	 * @return
	 */
	public static String formatDateTime(long t) {
		tempDate.setTime(t);
		return formatDateTime(tempDate);
	}

	/**
	 * 将长整型数据作为日期信息格式化为特定标准的字符串
	 * 
	 * @param t
	 *            待格式化的长整型
	 * @param pattern
	 *            格式化标准
	 * @return
	 */
	public static String formatDate(long t, String pattern) {
		tempDate.setTime(t);
		return formatDate(tempDate, pattern);
	}

	/**
	 * @return 日期格式化对象
	 */
	public static DateFormat getDateFormat() {
		return dateFormat;
	}

	/**
	 * 将时间戳对象中的时间信息移出，仅保留日期信息
	 * 
	 * @param t
	 *            待变更的时间戳对象
	 */
	public static void getDate(Date t) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(t);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		t.setTime(cal.getTimeInMillis());
	}
}
