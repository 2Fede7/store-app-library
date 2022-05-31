package it.gruppopam.app_common.alarms;

import org.joda.time.Duration;

import javax.inject.Inject;

import it.gruppopam.app_common.utils.DayOfWeek;

public abstract class BaseAlarmFiringBasedOnStoreCounters implements AlarmFiringStrategy {
    public static final Duration MIN_SPREAD_IN_MINUTES = Duration.standardMinutes(30);

    protected final AlarmFiringDefault defaultAlarmFiring = new AlarmFiringDefault();
    protected final AlarmFiringBasedOnStoreId alarmFiringBasedOnStore;

    @Inject
    public BaseAlarmFiringBasedOnStoreCounters(Long storeId) {
        this.alarmFiringBasedOnStore = new AlarmFiringBasedOnStoreId(storeId);
    }

    @Override
    public synchronized Long calculateStartDateTimeInMillis(DayOfWeek dayOfWeek, int syncHour, int syncMinutes, boolean delayed, Duration repeatInterval) {
        return calculateStartDateTimeInMillis(dayOfWeek, syncHour, syncMinutes, delayed, repeatInterval, MIN_SPREAD_IN_MINUTES);
    }

    public long computeDelayInMillis(Duration spread, Integer storeOrdinalNumber, Integer enabledStoresNumber) {
        long validSpreadInMillis = computeValidSpreadInMillis(spread);
        return validSpreadInMillis * storeOrdinalNumber / (enabledStoresNumber - 1);
    }

    private long computeValidSpreadInMillis(Duration spread) {
        return spread == null ? MIN_SPREAD_IN_MINUTES.getMillis() : Math.max(spread.getMillis(), MIN_SPREAD_IN_MINUTES.getMillis());
    }

}
