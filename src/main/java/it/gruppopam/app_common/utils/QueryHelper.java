package it.gruppopam.app_common.utils;

public final class QueryHelper {

    private QueryHelper() {
    }

    public static String getSafeString(String paramValue) {
        return paramValue.replace("'", "''");
    }

    public static String getSafeBoolean(Object paramValue) {
        return String.valueOf(Boolean.parseBoolean(paramValue.toString()));
    }
}
