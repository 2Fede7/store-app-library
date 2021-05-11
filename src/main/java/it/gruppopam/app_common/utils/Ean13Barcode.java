package it.gruppopam.app_common.utils;

import javax.inject.Inject;

import static it.gruppopam.app_common.utils.AppConstants.ARTICLE_ID_BARCODE_LENGTH_13;
import static org.apache.commons.lang3.StringUtils.leftPad;

public class Ean13Barcode extends BaseEan13Barcode {

    @Inject
    public Ean13Barcode() {
    }

    @Override
    public boolean isValid(String barcode) {
        return barcode.length() == ARTICLE_ID_BARCODE_LENGTH_13
            && getCheckDigitNumber(barcode) == calculateCheckDigit(barcode);
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
