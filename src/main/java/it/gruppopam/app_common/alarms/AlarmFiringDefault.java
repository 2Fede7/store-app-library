package it.gruppopam.app_common.alarms;

import static it.gruppopam.app_common.utils.DayOfWeek.getDaysBetween;

import androidx.annotation.NonNull;

import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.joda.time.Duration;

import it.gruppopam.app_common.utils.DayOfWeek;

public class AlarmFiringDefault {

    public AlarmFiringDefault() {
    }

    public Long calculateStartDateTimeInMillis(DayOfWeek dayOfWeek, int syncHour, int syncMinutes, Duration repeatInterval) {
        DateTime timeOff = getNextRun(dayOfWeek, syncHour, syncMinutes);
        long startTime = timeOff.getMillis();

        while (startTime < DateTimeUtils.currentTimeMillis()) {
            startTime += repeatInterval.getMillis();
        }

        return startTime;
    }

    @NonNull
    private DateTime getNextRun(DayOfWeek dayOfWeek, int syncHour, int syncMinutes) {
        int daysBetween = getDaysBetween(dayOfWeek);
        DateTime timeOff = DateTime.now();
        return timeOff
                .plusDays(daysBetween)
                .withHourOfDay(syncHour)
                .withMinuteOfHour(syncMinutes)
                .withSecondOfMinute(0)
                .withMillisOfSecond(0);
    }
}
