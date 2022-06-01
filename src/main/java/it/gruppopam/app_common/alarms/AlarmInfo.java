package it.gruppopam.app_common.alarms;

public interface AlarmInfo {

    int getSyncHour();

    int getSyncMinutes();

    long getRepeatIntervalInMillis();

    boolean isDelayed();

    Integer getSpreadInMinutes();

    IJobAction getJobAction();

    String name();

    int ordinal();
}
