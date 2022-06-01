package it.gruppopam.app_common.alarms;

import static it.gruppopam.app_common.utils.DayOfWeek.TODAY;

import junit.framework.Assert;

import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.joda.time.Duration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AlarmFiringDefaultTest {

    private AlarmFiringDefault alarmFiringDefault;
    private final Duration repeatInterval = Duration.standardMinutes(1);
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

        Long nextFireMilliseconds = alarmFiringDefault.calculateStartDateTimeInMillis(TODAY, hours, minutes, repeatInterval);

        Assert.assertEquals(nextFireMilliseconds.longValue(), DateTime.now().withTime(hours, 41, 0, 0).getMillis());
    }

    @Test
    public void calculateStartDateTimeInMillisWhenStartTimeShouldBeNow() {
        int hours = 1;
        int minutes = 30;

        // set current datetime equals to alarm datetime
        DateTime date = DateTime.now().withTime(1, 30, 0, 0);
        DateTimeUtils.setCurrentMillisFixed(date.getMillis());

        Long nextFireMilliseconds = alarmFiringDefault.calculateStartDateTimeInMillis(TODAY, hours, minutes, repeatInterval);

        Assert.assertEquals(nextFireMilliseconds.longValue(), DateTime.now().withTime(hours, minutes, 0, 0).getMillis());
    }

    @Test
    public void calculateStartDateTimeInMillisWhenStartTimeWillBeInTheFuture() {
        int hours = 1;
        int minutes = 30;

        // set current datetime previous than alarm
        DateTime date = DateTime.now().withTime(0, 0, 0, 0);
        DateTimeUtils.setCurrentMillisFixed(date.getMillis());

        Long nextFireMilliseconds = alarmFiringDefault.calculateStartDateTimeInMillis(TODAY, hours, minutes, repeatInterval);

        Assert.assertEquals(nextFireMilliseconds.longValue(), DateTime.now().withTime(hours, minutes, 0, 0).getMillis());
    }

    @After
    public void tearDown() {
        DateTimeUtils.setCurrentMillisSystem();
    }
}
