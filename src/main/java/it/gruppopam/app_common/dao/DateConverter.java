package it.gruppopam.app_common.dao;

import androidx.room.TypeConverter;

import org.joda.time.DateTime;

import java.util.Date;

@SuppressWarnings("PMD.ClassNamingConventions")
public final class DateConverter {

    private DateConverter() {
    }

    @TypeConverter
    public static Date toDate(Long dateLong) {
        return dateLong == null ? null : new Date(dateLong);
    }

    @TypeConverter
    public static Long fromDate(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static DateTime toDateTime(Long dateLong) {
        return dateLong == null ? null : new DateTime(dateLong);
    }

    @TypeConverter
    public static Long fromDateTime(DateTime date) {
        return date == null ? null : date.getMillis();
    }
}
