package it.gruppopam.app_common.alarms;

import static org.junit.Assert.assertEquals;
import static it.gruppopam.app_common.utils.DayOfWeek.SATURDAY;
import static it.gruppopam.app_common.utils.DayOfWeek.TODAY;
import static it.gruppopam.app_common.utils.DayOfWeek.WEDNESDAY;

import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.joda.time.Duration;
import org.junit.Before;
import org.junit.Test;

public class AlarmFiringDefaultTest {

    private AlarmFiringDefault alarmFiringDefault;
    private final Duration repeatIntervalOneMinute = Duration.standardSeconds(60);
    private final Duration repeatIntervalWeekly = Duration.standardDays(7);

    @Before
    public void setup() {
        alarmFiringDefault = new AlarmFiringDefault();
    }

    @Test
    public void calculateStartDateTimeInMillisWhenStartTimeWasInThePast() {
        int hours = 1;
        int minutes = 30;

        // set current datetime after than alarm
        DateTime date = DateTime.now().withTime(1, 40, 1, 0);
        DateTimeUtils.setCurrentMillisFixed(date.getMillis());

        Long nextFireMilliseconds = alarmFiringDefault.calculateStartDateTimeInMillis(TODAY, hours, minutes, repeatIntervalOneMinute);

        assertEquals(nextFireMilliseconds.longValue(), DateTime.now().withTime(hours, 41, 0, 0).getMillis());
    }

    @Test
    public void calculateStartDateTimeInMillisWhenStartTimeShouldBeNow() {
        int hours = 1;
        int minutes = 30;

        // set current datetime equals to alarm datetime
        DateTime date = DateTime.now().withTime(1, 30, 0, 0);
        DateTimeUtils.setCurrentMillisFixed(date.getMillis());

        Long nextFireMilliseconds = alarmFiringDefault.calculateStartDateTimeInMillis(TODAY, hours, minutes, repeatIntervalOneMinute);

        assertEquals(nextFireMilliseconds.longValue(), DateTime.now().withTime(hours, minutes, 0, 0).getMillis());
    }

    @Test
    public void calculateStartDateTimeInMillisWhenStartTimeWillBeInTheFuture() {
        int hours = 1;
        int minutes = 30;

        // set current datetime previous than alarm
        DateTime date = DateTime.now().withTime(0, 0, 0, 0);
        DateTimeUtils.setCurrentMillisFixed(date.getMillis());

        Long nextFireMilliseconds = alarmFiringDefault.calculateStartDateTimeInMillis(TODAY, hours, minutes, repeatIntervalOneMinute);

        assertEquals(nextFireMilliseconds.longValue(), DateTime.now().withTime(hours, minutes, 0, 0).getMillis());
    }

    @Test
    public void calculateStartDateTimeInMillisWhenStartTimeWillBeNextDayOfWeek() {
        int hours = 1;
        int minutes = 30;

        DateTime date = new DateTime(2022, 6, 1, 0, 0, 0);
        DateTime nextSchedule = new DateTime(2022, 6, 4, 1, 30, 0);

        DateTimeUtils.setCurrentMillisFixed(date.getMillis());

        Long nextFireMilliseconds = alarmFiringDefault.calculateStartDateTimeInMillis(SATURDAY, hours, minutes, repeatIntervalWeekly);

        assertEquals(nextFireMilliseconds.longValue(), nextSchedule.getMillis());
    }

    @Test
    public void calculateStartDateTimeInMillisWhenStartTimeWillBeSameDayOfWeek() {
        int hours = 1;
        int minutes = 30;

        DateTime date = new DateTime(2022, 6, 1, 0, 0, 0);
        DateTime nextSchedule = new DateTime(2022, 6, 1, 1, 30, 0);

        DateTimeUtils.setCurrentMillisFixed(date.getMillis());

        Long nextFireMilliseconds = alarmFiringDefault.calculateStartDateTimeInMillis(WEDNESDAY, hours, minutes, repeatIntervalWeekly);

        assertEquals(nextFireMilliseconds.longValue(), nextSchedule.getMillis());
    }

    @Test
    public void calculateStartDateTimeInMillisWhenStartTimeIsToday() {
        int hours = 1;
        int minutes = 30;

        DateTime date = new DateTime(2022, 6, 1, 0, 0, 0);
        DateTime nextSchedule = new DateTime(2022, 6, 1, 1, 30, 0);

        DateTimeUtils.setCurrentMillisFixed(date.getMillis());

        Long nextFireMilliseconds = alarmFiringDefault.calculateStartDateTimeInMillis(TODAY, hours, minutes, repeatIntervalWeekly);

        assertEquals(nextFireMilliseconds.longValue(), nextSchedule.getMillis());
    }

}
