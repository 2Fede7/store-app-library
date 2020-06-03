package it.gruppopam.app_common.alarms;

import javax.inject.Inject;

public class AlarmFiringBasedOnStoreId implements AlarmFiringStrategy {

    public static final int MILLISECONDS_FACTOR = 1000;

    private final Long storeId;
    private final AlarmFiringDefault defaultAlarmFiring = new AlarmFiringDefault();

    @Inject
    public AlarmFiringBasedOnStoreId(Long storeId) {
        this.storeId = storeId;
    }

    @Override
    public synchronized Long calculateStartDateTimeInMillis(int syncHour, int syncMinutes, long repeatIntervalInMillis, boolean delayed) {
        long startTime = defaultAlarmFiring.calculateStartDateTimeInMillis(syncHour, syncMinutes, repeatIntervalInMillis);

        if (delayed) {
            startTime += computeDelayInMillis();
        }

        return startTime;
    }

    @Override
    public Long calculateStartDateTimeInMillis(int syncHour, int syncMinutes, long repeatIntervalInMillis, boolean delayed, Integer spreadInMinutes) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    private long computeDelayInMillis() {
        return (storeId == null || storeId < 0) ? 0 : storeId * MILLISECONDS_FACTOR;
    }
}
