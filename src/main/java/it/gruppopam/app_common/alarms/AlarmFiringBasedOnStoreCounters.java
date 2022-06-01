package it.gruppopam.app_common.alarms;

import org.joda.time.Duration;

import javax.inject.Inject;

import it.gruppopam.app_common.utils.CommonAppPreferences;
import it.gruppopam.app_common.utils.DayOfWeek;

public class AlarmFiringBasedOnStoreCounters implements AlarmFiringStrategy {
    public static final Duration MIN_SPREAD_IN_MINUTES = Duration.standardMinutes(30);

    private final CommonAppPreferences sharedPreferenceUtil;
    private final AlarmFiringDefault defaultAlarmFiring = new AlarmFiringDefault();

    protected final AlarmFiringBasedOnStoreId alarmFiringBasedOnStore;

    @Inject
    public AlarmFiringBasedOnStoreCounters(CommonAppPreferences sharedPreferencesUtil) {
        this.alarmFiringBasedOnStore = new AlarmFiringBasedOnStoreId(sharedPreferencesUtil.getStoreId());
        this.sharedPreferenceUtil = sharedPreferencesUtil;
    }

    @Override
    public synchronized Long calculateStartDateTimeInMillis(DayOfWeek dayOfWeek, int syncHour, int syncMinutes, boolean delayed, Duration repeatInterval) {
        return calculateStartDateTimeInMillis(dayOfWeek, syncHour, syncMinutes, delayed, repeatInterval, MIN_SPREAD_IN_MINUTES);
    }

    @Override
    public Long calculateStartDateTimeInMillis(DayOfWeek dayOfWeek, int syncHour, int syncMinutes, boolean delayed, Duration repeatInterval, Duration spread) {
        if (!sharedPreferenceUtil.areStoreCountersValid()) {
            return alarmFiringBasedOnStore.calculateStartDateTimeInMillis(dayOfWeek, syncHour, syncMinutes, delayed, repeatInterval);
        }
        long startTime = defaultAlarmFiring.calculateStartDateTimeInMillis(dayOfWeek, syncHour, syncMinutes, repeatInterval);

        if (delayed) {
            startTime += computeDelayInMillis(spread, sharedPreferenceUtil.getStoreOrdinalNumber(), sharedPreferenceUtil.getEnabledStoresNumber());
        }

        return startTime;
    }

    public long computeDelayInMillis(Duration spread, Integer storeOrdinalNumber, Integer enabledStoresNumber) {
        long validSpreadInMillis = computeValidSpreadInMillis(spread);
        return validSpreadInMillis * storeOrdinalNumber / (enabledStoresNumber - 1);
    }

    private long computeValidSpreadInMillis(Duration spread) {
        return spread == null ? MIN_SPREAD_IN_MINUTES.getMillis() : Math.max(spread.getMillis(), MIN_SPREAD_IN_MINUTES.getMillis());
    }
}
