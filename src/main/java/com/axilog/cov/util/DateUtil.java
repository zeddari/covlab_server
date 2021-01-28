package com.axilog.cov.util;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.atomic.AtomicLong;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 10061004
 *
 */
@Slf4j
public class DateUtil {

	public static final int DAYS_IN_YEAR = 365;
	public static final String FULL_DATE_PATTEN = "yyyy-MM-dd HH:mm:ss";
	public static final String FULL_DAY_TIME_ZONE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
	public static final String DATE_TIMESTAMP_PATTERN = "yyyyMMddHHmmssSSS";
	// MOI date
	public static final String MOI_DATE_TIME_ENCODING = "yyyy-MM-dd hh:mm:ss";
	public static final String MOI_DATE_ENCODING = "yyyy-MM-dd";

	// default date pattern, using in mysql
	public static final String SIMPLE_DATE_PATTERN = "yyyy-MM-dd";

	// DataDictionary
	public static final String DATE_ENCODING = "yyyyMMdd";
	public static final String DATE_TIME_ENCODING = "yyyyMMdd HHmmss";
	// birth date pattern validation: birth date must be in
	// {YYYYMMDD,YYYYMMXX,YYYYXXXX,XXXXXXXX}
	public static final String BIRTH_DATE_UNKNOWN_PART = "X";
	public static final String VALIDATION_BIRTH_DATE_PATTERN = "\\d{4}X{4}|\\d{6}X{2}|X{8}|\\d{8}";
	public static final String VALIDATION_BIRTH_DATE_PATTERN_8D = "(\\d){8}";

	private static final AtomicLong LAST_TIME_MS = new AtomicLong();
	
	private DateUtil() {
	}

	/**
	 * Get date Now
	 * 
	 * @return
	 */
	public static Date now() {
		return new Date();
	}

	public static LocalDate nowLocalDate() {
		return convertToLocalDateViaInstant(new Date());
	}
	/**
	 * Get dateTime Now
	 * 
	 * @return
	 * @throws ParseException
	 */
	public static Date dateTimeNow(String pattern) {
		if (pattern != null) {
			SimpleDateFormat formatter = new SimpleDateFormat(pattern);
			Calendar calendar = Calendar.getInstance();
			try {
				return formatter.parse(formatter.format(calendar.getTime()));
			} catch (ParseException e) {
				log.error("error in parsing date for encoding {}", pattern);
				return new Date();
			}
		}
		return new Date();

	}

	/**
	 * Get dateTime Now
	 * 
	 * @return
	 * @throws ParseException
	 */
	public static Date formatDate(Date d, String pattern) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		return formatter.parse(formatter.format(d));

	}

	public static String format(Date date, String pattern) {
		return new SimpleDateFormat(pattern).format(date);

	}

	/**
	 * Convert a date to String
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String toString(final Date date, final String pattern) {
		if (date == null) {
			return null;
		}
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(pattern);
			return formatter.format(date);

		} catch (final Exception e) {
			log.error(String.format("Impossible to convert Date [%s] to String by pattern [%s]", date, pattern));
			return null;
		}
	}

	/**
	 * Calculate Duration difference between two java.util.Date
	 * 
	 * @param startDateTime
	 * @param endDateTime
	 * @return
	 */
	public static Duration diffDates(final Date startDateTime, final Date endDateTime) {
		return Duration.between(convertTo(startDateTime), convertTo(endDateTime));
	}

	/**
	 * Compare two date without the time part
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isLocalDatesEquals(Date date1, Date date2) {
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(date1);

		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(date2);
		return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)
				&& calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH)
				&& calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * Convert a java.util.Date to LocalDateTime
	 * 
	 * @param date
	 * @return
	 */
	public static LocalDateTime convertTo(final Date date) {
		final Calendar startCal = new GregorianCalendar();
		startCal.setTime(date);
		return LocalDateTime.of(startCal.get(Calendar.YEAR), startCal.get(Calendar.MONTH) + 1, // month Arrays start by
																								// 0 !!
				startCal.get(Calendar.DATE), startCal.get(Calendar.HOUR), startCal.get(Calendar.MINUTE),
				startCal.get(Calendar.SECOND), startCal.get(Calendar.MILLISECOND));
	}

	public static Date minusDays(final Date date, int minusDay) {
		if (date == null) {
			return date;
		}
		return Date.from(convertTo(date).minusDays(minusDay).atZone(ZoneId.systemDefault()).toInstant());
	}

	/**
	 * Compute date round 15 minutes
	 * 
	 * @param cal inpute date (Calendar)
	 * @return computed date
	 */
	public static Calendar round15Minuts(Calendar cal) {
		int round = cal.get(Calendar.MINUTE) % 15;
		cal.add(Calendar.MINUTE, 15 - round);
		cal.set(Calendar.SECOND, 0);
		return cal;
	}

	/**
	 * Compute date round Business Day
	 * 
	 * @param cal inpute date
	 * @return computed date
	 */
	public static Calendar roundBusinessDay2(Calendar cal) {
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		if (Calendar.SATURDAY == dayOfWeek || Calendar.SUNDAY == dayOfWeek) {
			cal.add(Calendar.DAY_OF_WEEK, 2);
		}
		return cal;
	}

	/**
	 * Compute date round Business Day
	 * 
	 * @param cal inpute date
	 * @return computed date
	 */
	public static Calendar roundBusinessDay(Calendar cal) {
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		if (Calendar.SATURDAY == dayOfWeek) {
			cal.add(Calendar.DAY_OF_WEEK, 2);
		} else if (Calendar.SUNDAY == dayOfWeek) {
			cal.add(Calendar.DAY_OF_WEEK, 1);
		}
		return cal;
	}

	/**
	 * Add years to the given date
	 * 
	 * @param date
	 * @param years
	 * @return
	 */
	public static Date addYears(Date date, int years) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, years);
		return calendar.getTime();

	}

	/**
	 * @param date
	 * @param month
	 * @return
	 */
	public static Date addMonth(Date date, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, month);
		return calendar.getTime();

	}

	/**
	 * @param date
	 * @param day
	 * @return
	 */
	@Deprecated
	public static Date addDay(Date date, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, day);
		return calendar.getTime();

	}

	/**
	 * @param date
	 * @param day
	 * @return
	 */

	public static Date addHours(Date date, int hours) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR_OF_DAY, hours);
		return calendar.getTime();

	}
	
	/**
	 * @param date
	 * @param day
	 * @return
	 */

	public static Date addMinutes(Date date, int minute) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, minute);
		return calendar.getTime();

	}
	
	
	public static Date addSeconds(Date date, int minute) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.SECOND, minute);
		return calendar.getTime();

	}

	/**
	 * jira IBSTHP-9291 Start
	 */
	
	/**
	 * @param date
	 * @param day
	 * @return
	 */

	public static Date addHoursAndSkipWeekEnd(Date date, int hours) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR_OF_DAY, hours);
		return roundBusinessDay(calendar).getTime();

	}
	
	/**
	 * jira IBSTHP-9291 End
	 */
	
	
	
	/**
	 * convert string to date given the specified pattern
	 * 
	 * @param dateInString
	 * @param datePattern
	 * @return Date
	 * @throws ParseException
	 */
	public static Date parseDate(String dateInString, String datePattern) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat(datePattern);
		formatter.setLenient(false);
		return formatter.parse(dateInString);

	}

	public static Date parseDateLenient(String dateInString, String datePattern) throws ParseException {
		return parseDate(dateInString, datePattern);
	}
	
	/**
	 * @return
	 */
	public static String getTimeStamp() {
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_TIMESTAMP_PATTERN);
		formatter.setLenient(false);
		return formatter.format(now());
	}
	
	public static LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
	    return dateToConvert.toInstant()
	      .atZone(ZoneId.systemDefault())
	      .toLocalDate();
	}
	
	public static Date convertToDateViaInstant(LocalDate dateToConvert) {
	    return java.util.Date.from(dateToConvert.atStartOfDay()
	      .atZone(ZoneId.systemDefault())
	      .toInstant());
	}
	
	public static Date convertToDateViaSqlDate(LocalDate dateToConvert) {
	    return java.sql.Date.valueOf(dateToConvert);
	}
	
	public static Date convertToDateViaUtilDate(LocalDate dateToConvert) {
	    return java.sql.Date.valueOf(dateToConvert);
	}
	
	
	public static long uniqueCurrentTimeMS() {
	    long now = System.currentTimeMillis();
	    while(true) {
	        long lastTime = LAST_TIME_MS.get();
	        if (lastTime >= now)
	            now = lastTime+1;
	        if (LAST_TIME_MS.compareAndSet(lastTime, now))
	            return now;
	    }
	}
}