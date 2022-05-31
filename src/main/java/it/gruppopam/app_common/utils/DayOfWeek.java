package it.gruppopam.app_common.utils;

import org.joda.time.DateTime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public enum DayOfWeek {
    MONDAY(1),
    TUESDAY(2),
    WEDNESDAY(3),
    THURSDAY(4),
    FRIDAY(5),
    SATURDAY(6),
    SUNDAY(7),
    TODAY(today());

    private int dayOfWeekLong;

    public static int getDaysBetween(DayOfWeek dayOfWeek) {
        int todayDayOfWeek = DateTime.now().getDayOfWeek();
        if (dayOfWeek.dayOfWeekLong >= todayDayOfWeek) {
            return dayOfWeek.dayOfWeekLong - todayDayOfWeek;
        }
        return 7 + dayOfWeek.dayOfWeekLong - todayDayOfWeek;
    }

    private static int today() {
        return DateTime.now().getDayOfWeek();
    }
}
