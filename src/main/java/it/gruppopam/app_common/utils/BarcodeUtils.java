package it.gruppopam.app_common.utils;

import it.gruppopam.app_common.barcode.BarcodeType;

import static it.gruppopam.app_common.utils.AppConstants.ARTICLE_ID_BARCODE_LENGTH_12;
import static it.gruppopam.app_common.utils.AppConstants.ARTICLE_ID_BARCODE_LENGTH_13;
import static it.gruppopam.app_common.utils.AppConstants.ARTICLE_ID_BARCODE_LENGTH_6;
import static it.gruppopam.app_common.utils.AppConstants.ARTICLE_ID_BARCODE_LENGTH_8;
import static it.gruppopam.app_common.utils.AppConstants.ARTICLE_ID_BARCODE_LENGTH_9;
import static org.apache.commons.lang3.StringUtils.leftPad;

public final class BarcodeUtils {

    private BarcodeUtils() {
    }

    public static int getCheckDigitNumber(String barcode) {
        return Integer.parseInt("" + barcode.charAt(barcode.length() - 1));
    }

    public static boolean is6Length(String barcode) {
        return barcode.length() == ARTICLE_ID_BARCODE_LENGTH_6;
    }

    public static boolean isEan8Length(String barcode) {
        return barcode.length() == ARTICLE_ID_BARCODE_LENGTH_8;
    }

    public static boolean is9Length(String barcode) {
        return barcode.length() == ARTICLE_ID_BARCODE_LENGTH_9;
    }

    public static boolean isUpc12Length(String barcode) {
        return barcode.length() == ARTICLE_ID_BARCODE_LENGTH_12;
    }

    public static boolean isEan13Length(String barcode) {
        return barcode.length() == ARTICLE_ID_BARCODE_LENGTH_13;
    }

    public static boolean isEan13Weight(String barcode) {
        return barcode.charAt(0) == '2';
    }

    public static boolean isEan8(String barcode) {
        return !isEan8Pam(barcode);
    }

    public static boolean isEan8Pam(String searchString) {
        Long barcode = Long.valueOf(searchString);
        return barcode >= 20000000 && barcode < 30000000;
    }

    public static boolean isNormalizeEan8Pam(String barcode) {
        return barcode.substring(0, 6).equals("000002")
                && barcode.length() == ARTICLE_ID_BARCODE_LENGTH_13;
    }

    public static String leftPad13(String barcode) {
        return leftPad(barcode, ARTICLE_ID_BARCODE_LENGTH_13, "0");
    }

    public static boolean isRightBarcodeType(String inputType, BarcodeType type) {
        if (inputType != null && !"".equals(inputType) && type != null) {
            return inputType.equals(type.getLabelName());
        } else {
            return false;
        }
    }

}
