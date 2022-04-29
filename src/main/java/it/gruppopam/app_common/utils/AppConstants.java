package it.gruppopam.app_common.utils;

@SuppressWarnings("PMD.ClassNamingConventions")
public final class AppConstants {

    public static final String SELECT_FROM = "SELECT * FROM ";
    public static final String DELETE_FROM = "DELETE FROM ";
    public static final String COUNT_FROM = "SELECT COUNT(*) FROM ";

    public static final String EMPTY_STRING = "";
    public static final int PROMOTIONS_LOOK_AHEAD_DAYS = 10;
    public static final String DEFAULT_WEIGHT_UNIT = "KG";
    public static final Long DEFAULT_QUANTITY = 1L;

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

    public static final int SOCKET_TAG = 10000;

    public static final String STORE_ID = "store_id";
    public static final String DEVICE_ID = "device_id";
    public static final String DEVICE_IP = "device_ip";

    public static final class IconTexts {
        public static final String STOCKED = "ST";
        public static final String XD = "XD";
        public static final String DIRECT = "DIR";
    }

    private AppConstants() {
    }

}
