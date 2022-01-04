package it.gruppopam.app_common.utils;

import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

final public class DateUtils {
    private static final String COMMON_DATE_FORMAT = "dd-MMM-yy";
    private static final String DB_DATE_FORMAT = "yyyy-MM-dd";
    private static final String SLASH_DATE_FORMAT = "dd/MM/yyyy";
    private static final String SHORT_SLASH_DATE_FORMAT = "dd/MM/yy";
    private static final String DATE_TIME_FORMAT = "dd-MMM-yy hh:mm:ss";
    private static final String ITALIAN_TIME_ZONE = "CET";
    private static final String TIME_FORMAT = "hh:mm a";
    private static final String DATE_FORMAT = "dd/MM";
    private static final String DAY_OF_WEEK_FORMAT = "EEE";
    private static DateUtils singleton;
    private final SimpleDateFormat timeFormat;
    private final SimpleDateFormat dayOfWeekFormat;
    private final SimpleDateFormat dateFormat;

    private DateUtils(Locale locale) {
        dateFormat = new SimpleDateFormat(DATE_FORMAT, locale);
        dayOfWeekFormat = new SimpleDateFormat(DAY_OF_WEEK_FORMAT, locale);
        timeFormat = new SimpleDateFormat(TIME_FORMAT, locale);
    }

    public static DateUtils getInstance() {
        if (singleton == null) {
            setLocale(Locale.getDefault());
        }
        return singleton;
    }

    public static void setLocale(Locale locale) {
        singleton = new DateUtils(locale);
    }

    public static Date todayStartOfDay() {
        return DateTime.now().withTimeAtStartOfDay().toDate();
    }

    public static Date tomorrowStartOfDay() {
        return DateTime.now().plusDays(1).toDate();
    }

    public static Date yesterdayStartOfDay() {
        return DateTime.now().minusDays(1).toDate();
    }

    public static Date truncateTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String dateSansTime = format.format(date);
        try {
            return format.parse(dateSansTime);
        } catch (ParseException e) {
            return date;
        }
    }

    public static SimpleDateFormat getCommonDateFormat() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(COMMON_DATE_FORMAT, Locale.getDefault());
        dateFormat.setTimeZone(TimeZone.getTimeZone(ITALIAN_TIME_ZONE));
        return dateFormat;
    }

    public static SimpleDateFormat getDbDateFormat() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DB_DATE_FORMAT, Locale.getDefault());
        dateFormat.setTimeZone(TimeZone.getTimeZone(ITALIAN_TIME_ZONE));
        return dateFormat;
    }

    public static SimpleDateFormat getShortSlashDateFormat() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(SHORT_SLASH_DATE_FORMAT, Locale.getDefault());
        dateFormat.setTimeZone(TimeZone.getTimeZone(ITALIAN_TIME_ZONE));
        return dateFormat;
    }

    public static SimpleDateFormat getSlashDateFormat() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(SLASH_DATE_FORMAT, Locale.getDefault());
        dateFormat.setTimeZone(TimeZone.getTimeZone(ITALIAN_TIME_ZONE));
        return dateFormat;
    }

    public static SimpleDateFormat getDateTimeFormat() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault());
        dateFormat.setTimeZone(TimeZone.getTimeZone(ITALIAN_TIME_ZONE));
        return dateFormat;
    }

    public static Date getDaysAgoDate(int daysAgo) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, daysAgo * -1);
        return cal.getTime();
    }

    public String formatTime(Date time) {
        return timeFormat.format(time);
    }

    public String formatDayOfWeek(Date dayOfWeek) {
        return dayOfWeekFormat.format(dayOfWeek);
    }

    public String formatDate(Date date) {
        return dateFormat.format(date);
    }

    public static boolean isBeforeOrEquals(Date dateToCompare, Date comparedDate) {
        return dateToCompare.before(comparedDate) || dateToCompare.equals(comparedDate);
    }

    public static boolean isAfterOrEquals(Date dateToCompare, Date comparedDate) {
        return dateToCompare.after(comparedDate) || dateToCompare.equals(comparedDate);
    }
}
