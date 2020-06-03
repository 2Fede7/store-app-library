package it.gruppopam.app_common.utils;

import java.util.List;

public class MailUtils {

    public MailUtils() {
    }

    public String generateReportText(List<String> report) {

        StringBuilder builder = new StringBuilder();
        for (String reportRow : report) {
            builder.append(reportRow).append('\n');
        }

        return builder.append("-------------------\n\n").toString();
    }

}
