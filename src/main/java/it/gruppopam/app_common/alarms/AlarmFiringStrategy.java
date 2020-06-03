package it.gruppopam.app_common.alarms;

public interface AlarmFiringStrategy {
    Long calculateStartDateTimeInMillis(int syncHour, int syncMinutes, long repeatIntervalInMillis, boolean delayed);

    Long calculateStartDateTimeInMillis(int syncHour, int syncMinutes, long repeatIntervalInMillis, boolean delayed, Integer spreadInMinutes);
}
