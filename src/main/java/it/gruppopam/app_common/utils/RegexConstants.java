package it.gruppopam.app_common.utils;

@SuppressWarnings("PMD.ClassNamingConventions")
public final class RegexConstants {

    public final static String REGEX_QUANTITY_DOT = "-?[0-9]{0,4}+((\\.[0-9]{0,2})?)||(\\,)?";
    public final static String REGEX_QUANTITY_COMMA = "-?[0-9]{0,4}+((\\,[0-9]{0,2})?)||(\\,)?";
    public final static String REGEX_BARCODE = "^[0-9]{1,13}+$";
    public static final String SNAKE_CASE_STRING_REGEX = "^([a-z]+_{0,1})*";

    private RegexConstants() {
    }

}
