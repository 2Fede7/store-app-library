package it.gruppopam.app_common.alarms;

import static org.mockito.Mockito.when;
import static it.gruppopam.app_common.utils.DayOfWeek.TODAY;

import junit.framework.Assert;

import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.joda.time.Duration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import it.gruppopam.app_common.utils.CommonAppPreferences;

@RunWith(RobolectricTestRunner.class)
public class AlarmFiringBasedOnStoreIdTest  {

    private AlarmFiringStrategy alarmFiringStrategy;
    private long storeId = 532L;
    private final int hours = 1;
    private final int minutes = 30;
    private final Duration repeatInterval = Duration.standardMinutes(1);

    @Mock
    CommonAppPreferences sharedPreferenceUtil;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        when(sharedPreferenceUtil.getStoreId()).thenReturn(storeId);
        when(sharedPreferenceUtil.areStoreCountersValid()).thenReturn(true);
        alarmFiringStrategy = new AlarmFiringBasedOnStoreId(sharedPreferenceUtil.getStoreId());

    }


    @Test
    public void calculateStartDateTimeInMillisWhenStartTimeWillBeInTheFuture() {
        // set current datetime previous than alarm
        DateTime date = DateTime.now().withTime(00, 00, 0, 0);
        DateTimeUtils.setCurrentMillisFixed(date.getMillis());

        Long nextFireMilliseconds = alarmFiringStrategy.calculateStartDateTimeInMillis(TODAY,hours, minutes, true, repeatInterval);

        Assert.assertEquals(nextFireMilliseconds.longValue(), DateTime.now().withTime(hours, minutes, 0, 0).getMillis() + storeId * AlarmFiringBasedOnStoreId.MILLISECONDS_FACTOR);
    }

    @Test
    public void calculateStartDateTimeInMillisWhenStartTimeShouldBeNow() {
        // set current datetime equals to alarm datetime
        DateTime date = DateTime.now().withTime(1, 30, 0, 0);
        DateTimeUtils.setCurrentMillisFixed(date.getMillis());

        Long nextFireMilliseconds = alarmFiringStrategy.calculateStartDateTimeInMillis(TODAY,hours, minutes, true, repeatInterval);

        Assert.assertEquals(nextFireMilliseconds.longValue(), DateTime.now().withTime(hours, minutes, 0, 0).getMillis() + storeId * AlarmFiringBasedOnStoreId.MILLISECONDS_FACTOR);
    }

    @Test
    public void calculateStartDateTimeInMillisWhenStartTimeWasInThePast() {
        // set current datetime after than alarm
        DateTime date = DateTime.now().withTime(1, 30, 1, 0);
        DateTimeUtils.setCurrentMillisFixed(date.getMillis());

        Long nextFireMilliseconds = alarmFiringStrategy.calculateStartDateTimeInMillis(TODAY,hours, minutes, true, repeatInterval);

        Assert.assertEquals(nextFireMilliseconds.longValue(), DateTime.now().withTime(hours, minutes, 0, 0).getMillis() + repeatInterval.getMillis() + storeId * AlarmFiringBasedOnStoreId.MILLISECONDS_FACTOR);
    }

    @Test
    public void checkThatWithNullStoreIdTheAlarmIsNotDelayed() {
        alarmFiringStrategy = new AlarmFiringBasedOnStoreId(null);
        //when(sharedPreferenceUtil.getStoreId()).thenReturn(null);

        // set current datetime after than alarm
        DateTime date = DateTime.now().withTime(1, 30, 1, 0);
        DateTimeUtils.setCurrentMillisFixed(date.getMillis());

        Long nextFireMilliseconds = alarmFiringStrategy.calculateStartDateTimeInMillis(TODAY,hours, minutes, true, repeatInterval);

        Assert.assertEquals(nextFireMilliseconds.longValue(), DateTime.now().withTime(hours, minutes, 0, 0).getMillis() + repeatInterval.getMillis());
    }

    @Test
    public void checkThatWithNegativeStoreIdTheAlarmIsNotDelayed() {
        alarmFiringStrategy = new AlarmFiringBasedOnStoreId(-100L);
        //when(sharedPreferenceUtil.getStoreId()).thenReturn(-100L);

        // set current datetime after than alarm
        DateTime date = DateTime.now().withTime(1, 30, 1, 0);
        DateTimeUtils.setCurrentMillisFixed(date.getMillis());

        Long nextFireMilliseconds = alarmFiringStrategy.calculateStartDateTimeInMillis(TODAY,hours, minutes, true, repeatInterval);

        Assert.assertEquals(nextFireMilliseconds.longValue(), DateTime.now().withTime(hours, minutes, 0, 0).getMillis() + repeatInterval.getMillis());
    }

    @Test
    public void checkThatWithStoreIdZeroTheAlarmIsNotDelayed() {
        alarmFiringStrategy = new AlarmFiringBasedOnStoreId(0L);
        //when(sharedPreferenceUtil.getStoreId()).thenReturn(0L);

        // set current datetime after than alarm
        DateTime date = DateTime.now().withTime(1, 30, 1, 0);
        DateTimeUtils.setCurrentMillisFixed(date.getMillis());

        Long nextFireMilliseconds = alarmFiringStrategy.calculateStartDateTimeInMillis(TODAY,hours, minutes, true, repeatInterval);

        Assert.assertEquals(nextFireMilliseconds.longValue(), DateTime.now().withTime(hours, minutes, 0, 0).getMillis() + repeatInterval.getMillis());
    }

    @After
    public void tearDown() {
        DateTimeUtils.setCurrentMillisSystem();
    }
}
