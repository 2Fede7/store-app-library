package it.gruppopam.app_common.alarms;

import junit.framework.Assert;

import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.junit.Before;
import org.junit.Test;

public class AlarmFiringDefaultTest {

    private AlarmFiringDefault alarmFiringDefault;

    @Before
    public void setup() {
        alarmFiringDefault = new AlarmFiringDefault();

    }

    @Test
    public void calculateStartDateTimeInMillisWhenStartTimeWasInThePast() {
        int hours = 1;
        int minutes = 30;
        long repeatIntervalInMillis = 60000;

        // set current datetime after than alarm
        DateTime date = DateTime.now().withTime(01, 40, 01, 0);
        DateTimeUtils.setCurrentMillisFixed(date.getMillis());

        Long nextFireMilliseconds = alarmFiringDefault.calculateStartDateTimeInMillis(hours, minutes, repeatIntervalInMillis);

        Assert.assertEquals(nextFireMilliseconds.longValue(), DateTime.now().withTime(hours, 41, 0, 0).getMillis());
    }

    @Test
    public void calculateStartDateTimeInMillisWhenStartTimeShouldBeNow() {
        int hours = 1;
        int minutes = 30;
        long repeatIntervalInMillis = 60000;

        // set current datetime equals to alarm datetime
        DateTime date = DateTime.now().withTime(01, 30, 00, 0);
        DateTimeUtils.setCurrentMillisFixed(date.getMillis());

        Long nextFireMilliseconds = alarmFiringDefault.calculateStartDateTimeInMillis(hours, minutes, repeatIntervalInMillis);

        Assert.assertEquals(nextFireMilliseconds.longValue(), DateTime.now().withTime(hours, minutes, 0, 0).getMillis());
    }

    @Test
    public void calculateStartDateTimeInMillisWhenStartTimeWillBeInTheFuture() {
        int hours = 1;
        int minutes = 30;

        long repeatIntervalInMillis = 60000;

        // set current datetime previous than alarm
        DateTime date = DateTime.now().withTime(00, 00, 0, 0);
        DateTimeUtils.setCurrentMillisFixed(date.getMillis());

        Long nextFireMilliseconds = alarmFiringDefault.calculateStartDateTimeInMillis(hours, minutes, repeatIntervalInMillis);

        Assert.assertEquals(nextFireMilliseconds.longValue(), DateTime.now().withTime(hours, minutes, 0, 0).getMillis());
    }

}
