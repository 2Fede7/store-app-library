package it.gruppopam.app_common.alarms;

import static org.mockito.Mockito.when;
import static it.gruppopam.app_common.alarms.AlarmFiringBasedOnStoreCounters.MIN_SPREAD_IN_MINUTES;
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
public class AlarmFiringBasedOnStoreCountersTest {

    private AlarmFiringBasedOnStoreCounters alarmFiringStrategyBasedOnStoreCounters;
    private AlarmFiringBasedOnStoreId alarmFiringStrategyBasedOnStore;
    private AlarmFiringDefault alarmFiringDefault;
    private final long storeId = 532L;
    private final int hours = 1;
    private final int minutes = 30;
    private final Duration repeatInterval = Duration.standardMinutes(1);
    private final Duration repeatIntervalHour = Duration.standardHours(1);

    @Mock
    CommonAppPreferences sharedPreferencesUtil;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        when(sharedPreferencesUtil.getStoreId()).thenReturn(storeId);
        when(sharedPreferencesUtil.areStoreCountersValid()).thenReturn(true);
        alarmFiringStrategyBasedOnStoreCounters = new AlarmFiringBasedOnStoreCounters(sharedPreferencesUtil);
        alarmFiringStrategyBasedOnStore = new AlarmFiringBasedOnStoreId(sharedPreferencesUtil.getStoreId());
        alarmFiringDefault = new AlarmFiringDefault();
    }

    private void setStoreOrdinalNumber(int number) {
        when(sharedPreferencesUtil.getStoreOrdinalNumber()).thenReturn(number);
    }

    private void setEnabledStoresNumber(int number) {
        when(sharedPreferencesUtil.getEnabledStoresNumber()).thenReturn(number);
    }


    public void calculateDelayedStartDateTimeInMillisWithoutSpreadInMinutesShouldUseDefaultSpreadInMinutes() {
        Assert.assertEquals(alarmFiringStrategyBasedOnStoreCounters.calculateStartDateTimeInMillis(TODAY, 0, 0, true, repeatInterval),
            alarmFiringStrategyBasedOnStoreCounters.calculateStartDateTimeInMillis(TODAY, 0, 0, true, repeatInterval, MIN_SPREAD_IN_MINUTES));
    }

    public void calculateNotDelayedStartDateTimeInMillisWithoutSpreadInMinutesShouldReturnDefaultAlarmFiring() {
        Assert.assertEquals(alarmFiringStrategyBasedOnStoreCounters.calculateStartDateTimeInMillis(TODAY, 0, 0, false, repeatInterval),
            alarmFiringDefault.calculateStartDateTimeInMillis(TODAY, 0, 0, repeatInterval));
    }

    @Test
    public void shouldUseDefaultStrategyIfNotDelayed() {
        Assert.assertEquals(alarmFiringStrategyBasedOnStoreCounters.calculateStartDateTimeInMillis(TODAY, 0, 0, false, repeatInterval, Duration.standardMinutes(10)),
            alarmFiringDefault.calculateStartDateTimeInMillis(TODAY, 0, 0, repeatInterval));
    }

    @Test
    public void shouldUseStrategyBasedOnStoreIdIfOrdinalNumberIsNotSet() {
        setEnabledStoresNumber(10);
        Assert.assertEquals(alarmFiringStrategyBasedOnStoreCounters.calculateStartDateTimeInMillis(TODAY, 0, 0, false, repeatInterval, Duration.standardMinutes(10)),
            alarmFiringStrategyBasedOnStore.calculateStartDateTimeInMillis(TODAY, 0, 0, false, repeatInterval));
    }

    @Test
    public void shouldUseStrategyBasedOnStoreIdIfStoresNumberIsNotSet() {
        setStoreOrdinalNumber(10);
        Assert.assertEquals(alarmFiringStrategyBasedOnStoreCounters.calculateStartDateTimeInMillis(TODAY, 0, 0, false, repeatInterval, Duration.standardMinutes(10)),
            alarmFiringStrategyBasedOnStore.calculateStartDateTimeInMillis(TODAY, 0, 0, false, repeatInterval));
    }

    @Test
    public void shouldUseSpreadOfDefaultMinutesIfLesserValueIsProvided() {
        Assert.assertEquals(alarmFiringStrategyBasedOnStoreCounters.computeDelayInMillis(Duration.standardMinutes(2), 2, 10),
            alarmFiringStrategyBasedOnStoreCounters.computeDelayInMillis(MIN_SPREAD_IN_MINUTES, 2, 10));
    }

    @Test
    public void shouldUseGivenSpreadMinutes() {
        Assert.assertTrue(alarmFiringStrategyBasedOnStoreCounters.computeDelayInMillis(repeatIntervalHour, 10, 20) !=
            alarmFiringStrategyBasedOnStoreCounters.computeDelayInMillis(MIN_SPREAD_IN_MINUTES, 10, 20));

        Assert.assertEquals(alarmFiringStrategyBasedOnStoreCounters.computeDelayInMillis(repeatIntervalHour, 10, 20),
            1894736
        );
    }

    @Test
    public void delayForTheLastStoreShouldBeEqualsToZero() {
        Assert.assertEquals(alarmFiringStrategyBasedOnStoreCounters.computeDelayInMillis(repeatIntervalHour, 0, 20),
            0L
        );
    }

    @Test
    public void delayForTheLastStoreShouldBeEqualsToTheSpreadMinutes() {
        Assert.assertEquals(alarmFiringStrategyBasedOnStoreCounters.computeDelayInMillis(repeatIntervalHour, 19, 20),
            60 * 60 * 1000L
        );
    }

    @Test
    public void calculateStartDateTimeInMillisWhenStartTimeWillBeInTheFuture() {
        setStoreOrdinalNumber(18);
        setEnabledStoresNumber(20);

        // set current datetime previous than alarm
        DateTime date = DateTime.now().withTime(0, 0, 0, 0);
        DateTimeUtils.setCurrentMillisFixed(date.getMillis());

        Long nextFireMilliseconds = alarmFiringStrategyBasedOnStoreCounters.calculateStartDateTimeInMillis(TODAY, hours, minutes, true, repeatInterval, repeatInterval);

        Assert.assertEquals(nextFireMilliseconds.longValue(), DateTime.now().withTime(hours, minutes, 0, 0).getMillis() + alarmFiringStrategyBasedOnStoreCounters.computeDelayInMillis(Duration.standardMinutes(30), 18, 20));
    }

    @Test
    public void calculateStartDateTimeInMillisWhenStartTimeWasInThePast() {
        setStoreOrdinalNumber(18);
        setEnabledStoresNumber(20);

        // set current datetime after than alarm
        DateTime date = DateTime.now().withTime(1, 40, 1, 0);
        DateTimeUtils.setCurrentMillisFixed(date.getMillis());

        Long nextFireMilliseconds = alarmFiringStrategyBasedOnStoreCounters.calculateStartDateTimeInMillis(TODAY, hours, minutes, true, repeatInterval, Duration.standardMinutes(30));

        Assert.assertEquals(nextFireMilliseconds.longValue(), DateTime.now().withTime(hours, 41, 0, 0).getMillis() + alarmFiringStrategyBasedOnStoreCounters.computeDelayInMillis(Duration.standardMinutes(30), 18, 20));
    }

    @Test
    public void calculateStartDateTimeInMillisWhenStartTimeShouldBeNow() {
        setStoreOrdinalNumber(18);
        setEnabledStoresNumber(20);

        // set current datetime equals to alarm datetime
        DateTime date = DateTime.now().withTime(1, 30, 0, 0);
        DateTimeUtils.setCurrentMillisFixed(date.getMillis());

        Long nextFireMilliseconds = alarmFiringStrategyBasedOnStoreCounters.calculateStartDateTimeInMillis(TODAY, hours, minutes, true, repeatInterval, Duration.standardMinutes(30));

        Assert.assertEquals(nextFireMilliseconds.longValue(), DateTime.now().withTime(hours, minutes, 0, 0).getMillis() + alarmFiringStrategyBasedOnStoreCounters.computeDelayInMillis(Duration.standardMinutes(30), 18, 20));
    }

    @After
    public void tearDown() {
        DateTimeUtils.setCurrentMillisSystem();
    }
}
