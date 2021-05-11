package it.gruppopam.app_common.utils;

@SuppressWarnings("PMD.ClassNamingConventions")
public final class AppConstants {

    public static final int ARTICLE_ID_BARCODE_LENGTH_6 = 6;
    public static final int ARTICLE_ID_BARCODE_LENGTH_8 = 8;
    public static final int ARTICLE_ID_BARCODE_LENGTH_9 = 9;
    public static final int ARTICLE_ID_BARCODE_LENGTH_12 = 12;
    public static final int ARTICLE_ID_BARCODE_LENGTH_13 = 13;
    public static final int BARCODE_MAX_LENGTH = 13;
    public static final int ARTICLE_ID_MAX_LENGTH = 7;
    public static final int ARTICLE_ID_MIN_LENGTH = 5;
    public static final long INVALID_ARTICLE_ID = -1;
    public static final int MASK_VALUE_FOR_ARTICLE_ID = 20000000;
    public static final char PREFIX_CHAR_OF_VALID_BARCODE = '2';

    private  AppConstants() {
    }

}
