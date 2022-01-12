package it.gruppopam.app_common.utils;

import android.view.View;

public final class StringUtils {

    public static final String EMPTY_STRING = "";
    private final static String COLOR_HTML_FORMAT = "<font color=#%s>%s</font>";
    private final static String BOLD_COLOR_HTML_FORMAT = "<b>" + COLOR_HTML_FORMAT + "</b>";

    private StringUtils() {
    }

    public static boolean isNullOrEmpty(String val) {
        return val == null || val.equals(EMPTY_STRING);
    }

    public static String getOrEmpty(Object val) {
        return val == null ? EMPTY_STRING : String.valueOf(val).trim();
    }

    public static String getColoredText(String textToBeColored, int color, View convertView) {
        return getColoredText(textToBeColored, color, convertView, true);
    }

    public static String getColoredText(String textToBeColored, int color, View convertView, boolean bold) {
        String colorHex = Integer.toHexString(convertView.getResources().getColor(color) & 0x00ffffff);
        if (bold) {
            return String.format(BOLD_COLOR_HTML_FORMAT, colorHex, textToBeColored);
        } else {
            return String.format(COLOR_HTML_FORMAT, colorHex, textToBeColored);
        }
    }

}
