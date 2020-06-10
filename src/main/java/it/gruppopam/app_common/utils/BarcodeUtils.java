package it.gruppopam.app_common.utils;

import static org.apache.commons.lang3.StringUtils.leftPad;

public final class BarcodeUtils {

    private BarcodeUtils() {
    }

    public static int getCheckDigitNumber(String barcode) {
        return Integer.parseInt("" + barcode.charAt(barcode.length() - 1));
    }

    public static boolean isEan13Length(String searchString) {
        return searchString.length() == AppConstants.ARTICLE_ID_BARCODE_LENGTH_13;
    }

    public static boolean isEan8Length(String searchString) {
        return searchString.length() == AppConstants.ARTICLE_ID_BARCODE_LENGTH;
    }

    public static boolean isEan8(String searchString) {
        return !isEan8Pam(searchString);
    }

    public static boolean isEan8Pam(String searchString) {
        Long barcode = Long.valueOf(searchString);
        return barcode >= 22000000 && barcode < 30000000;
    }

    public static String leftPad13(String barcode) {
        return leftPad(barcode, AppConstants.ARTICLE_ID_BARCODE_LENGTH_13, "0");
    }

}
