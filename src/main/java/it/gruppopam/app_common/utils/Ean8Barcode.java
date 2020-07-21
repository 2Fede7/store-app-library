package it.gruppopam.app_common.utils;

import javax.inject.Inject;

import static it.gruppopam.app_common.utils.AppConstants.ARTICLE_ID_BARCODE_LENGTH_8;
import static org.apache.commons.lang3.StringUtils.leftPad;

public class Ean8Barcode extends BaseEan8Barcode {

    @Inject
    public Ean8Barcode() {
    }

    @Override
    public boolean isValid(String barcode) {
        return barcode.length() == ARTICLE_ID_BARCODE_LENGTH_8
            && getCheckDigitNumber(barcode) == calculateCheckDigit(barcode)
            && barcode.charAt(0) != '2';
    }

    @Override
    public int getCheckDigitNumber(String barcode) {
        return Integer.parseInt("" + barcode.charAt(barcode.length() - 1));
    }

    @Override
    public String normalizeBarcode(String barcode) {
        return leftPad(barcode, 13, "0");
    }

    @Override
    public boolean shouldRetrieveFromDb() {
        return true;
    }

}
