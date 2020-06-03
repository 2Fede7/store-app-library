package it.gruppopam.app_common.alarms;

import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;

public class AlarmFiringDefault {

    public AlarmFiringDefault() {
    }

    public Long calculateStartDateTimeInMillis(int syncHour, int syncMinutes, long repeatIntervalInMillis) {
        DateTime date = DateTime.now().withTime(syncHour, syncMinutes, 0, 0);

        long startTime = date.getMillis();

        while (startTime < DateTimeUtils.currentTimeMillis()) {
            startTime += repeatIntervalInMillis;
        }

        return startTime;
    }


}
