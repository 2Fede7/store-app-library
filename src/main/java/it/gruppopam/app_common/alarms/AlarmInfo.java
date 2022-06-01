package it.gruppopam.app_common.alarms;

import org.joda.time.Duration;

import it.gruppopam.app_common.utils.DayOfWeek;

public interface AlarmInfo {

    int getSyncHour();

    int getSyncMinutes();

    Duration getRepeatInterval();

    boolean isDelayed();

    Duration getSpread();

    IJobAction getJobAction();

    DayOfWeek getDayOfWeek();

    String name();

    int ordinal();
}
