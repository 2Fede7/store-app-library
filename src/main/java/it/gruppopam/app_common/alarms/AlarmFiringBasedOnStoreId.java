package it.gruppopam.app_common.alarms;

import org.joda.time.Duration;

import javax.inject.Inject;

import it.gruppopam.app_common.utils.DayOfWeek;

public class AlarmFiringBasedOnStoreId implements AlarmFiringStrategy {

    public static final int MILLISECONDS_FACTOR = 1000;

    private final Long storeId;
    private final AlarmFiringDefault defaultAlarmFiring = new AlarmFiringDefault();

    @Inject
    public AlarmFiringBasedOnStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public synchronized Long calculateStartDateTimeInMillis(DayOfWeek dayOfWeek, int syncHour, int syncMinutes, boolean delayed, Duration repeatInterval) {
        long startTime = defaultAlarmFiring.calculateStartDateTimeInMillis(dayOfWeek, syncHour, syncMinutes, repeatInterval);

        if (delayed) {
            startTime += computeDelayInMillis();
        }

        return startTime;
    }

    @Override
    public Long calculateStartDateTimeInMillis(DayOfWeek dayOfWeek, int syncHour, int syncMinutes, boolean delayed, Duration repeatInterval, Duration spread) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    private long computeDelayInMillis() {
        return (storeId == null || storeId < 0) ? 0 : storeId * MILLISECONDS_FACTOR;
    }
}
