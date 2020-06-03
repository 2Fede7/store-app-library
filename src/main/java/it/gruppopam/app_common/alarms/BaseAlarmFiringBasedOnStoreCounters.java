package it.gruppopam.app_common.alarms;

import javax.inject.Inject;

public abstract class BaseAlarmFiringBasedOnStoreCounters implements AlarmFiringStrategy {
    public static final int MIN_SPREAD_IN_MINUTES = 30;

    protected final AlarmFiringDefault defaultAlarmFiring = new AlarmFiringDefault();
    protected final AlarmFiringBasedOnStoreId alarmFiringBasedOnStore;

    @Inject
    public BaseAlarmFiringBasedOnStoreCounters(Long storeId) {
        this.alarmFiringBasedOnStore = new AlarmFiringBasedOnStoreId(storeId);
    }

    @Override
    public synchronized Long calculateStartDateTimeInMillis(int syncHour, int syncMinutes, long repeatIntervalInMillis, boolean delayed) {
        return calculateStartDateTimeInMillis(syncHour, syncMinutes, repeatIntervalInMillis, delayed, MIN_SPREAD_IN_MINUTES);
    }

    public long computeDelayInMillis(Integer spreadInMinutes, Integer storeOrdinalNumber, Integer enabledStoresNumber) {
        int validSpreadInMinutes = computeValidSpreadInMinutes(spreadInMinutes);
        return (validSpreadInMinutes * 60 * 1000) * storeOrdinalNumber / (enabledStoresNumber - 1);
    }

    private int computeValidSpreadInMinutes(Integer spreadInMinutes) {
        return spreadInMinutes == null ? MIN_SPREAD_IN_MINUTES : Math.max(spreadInMinutes, MIN_SPREAD_IN_MINUTES);
    }

}
