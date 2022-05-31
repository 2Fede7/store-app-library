package it.gruppopam.app_common.alarms;

import static it.gruppopam.app_common.utils.DayOfWeek.getDaysBetween;

import androidx.annotation.NonNull;

import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.joda.time.Duration;

import java.util.Calendar;
import java.util.Locale;

import it.gruppopam.app_common.utils.DayOfWeek;

public class AlarmFiringDefault {

    public AlarmFiringDefault() {
    }

    public Long calculateStartDateTimeInMillis(DayOfWeek dayOfWeek, int syncHour, int syncMinutes, Duration repeatInterval) {
        Calendar timeOff = getNextRun(dayOfWeek, syncHour, syncMinutes);
        long startTime = timeOff.getTimeInMillis();

        while (startTime < DateTimeUtils.currentTimeMillis()) {
            startTime += repeatInterval.getMillis();
        }

        return startTime;
    }

    @NonNull
    private Calendar getNextRun(DayOfWeek dayOfWeek, int syncHour, int syncMinutes) {
        int daysBetween = getDaysBetween(dayOfWeek);
        Calendar timeOff = DateTime.now().toCalendar(Locale.getDefault());
        timeOff.add(Calendar.DATE, daysBetween);
        timeOff.set(Calendar.HOUR, syncHour);
        timeOff.set(Calendar.MINUTE, syncMinutes);
        timeOff.set(Calendar.SECOND, 0);
        return timeOff;
    }
}
