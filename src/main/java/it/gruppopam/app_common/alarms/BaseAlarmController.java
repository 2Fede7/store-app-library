package it.gruppopam.app_common.alarms;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.joda.time.Duration;
import org.joda.time.LocalDateTime;

import it.gruppopam.app_common.di.module.annotation.ForApplication;

public abstract class BaseAlarmController {

    private final String TAG = this.getClass().getSimpleName();

    protected final AlarmManager alarmManager;
    protected final AlarmFiringStrategy alarmFiringStrategy;
    protected final Context context;

    public BaseAlarmController(AlarmManager alarmManager, AlarmFiringStrategy alarmFiringStrategy, @ForApplication Context context) {
        this.alarmManager = alarmManager;
        this.alarmFiringStrategy = alarmFiringStrategy;
        this.context = context;
    }

    public abstract void initialize();

    protected void setAlarm(Context context, AlarmInfo whichAlarm, Class classs) {
        PendingIntent pendingIntent = getPendingIntent(context, whichAlarm, classs);
        scheduleAlarm(whichAlarm.name(), getNextStartTime(whichAlarm), whichAlarm.getRepeatInterval(), pendingIntent);
    }

    private void scheduleAlarm(String alarmName, long startTime, Duration repeatInterval, PendingIntent pendingIntent) {
        Log.i(TAG, "Starting alarm");

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
            startTime, repeatInterval.getMillis(), pendingIntent);

        Log.i(TAG, "Alarm (" + alarmName + ") scheduled at "
            + new LocalDateTime(startTime) + " and current time is "
            + new LocalDateTime(System.currentTimeMillis()));
    }

    private long getNextStartTime(AlarmInfo whichAlarm) {
        return alarmFiringStrategy.calculateStartDateTimeInMillis(whichAlarm.getDayOfWeek(), whichAlarm.getSyncHour(), whichAlarm.getSyncMinutes(),
                whichAlarm.isDelayed(), whichAlarm.getRepeatInterval(), whichAlarm.getSpread());
    }

    protected PendingIntent getPendingIntent(Context context, AlarmInfo alarm, Class classs) {
        Intent broadCastIntent = new Intent(context, classs);
        broadCastIntent.setAction(alarm.name());
        return PendingIntent.getBroadcast(context, alarm.ordinal(), broadCastIntent, 0);
    }
}
