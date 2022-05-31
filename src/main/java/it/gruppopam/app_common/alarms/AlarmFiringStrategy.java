package it.gruppopam.app_common.alarms;

import org.joda.time.Duration;

import it.gruppopam.app_common.utils.DayOfWeek;

public interface AlarmFiringStrategy {
    Long calculateStartDateTimeInMillis(DayOfWeek dayOfWeek, int syncHour, int syncMinutes, boolean delayed, Duration repeatInterval);

    Long calculateStartDateTimeInMillis(DayOfWeek dayOfWeek, int syncHour, int syncMinutes, boolean delayed, Duration repeatInterval, Duration spread);

}
