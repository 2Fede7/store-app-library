package it.gruppopam.app_common.utils;

import javax.inject.Inject;

import static it.gruppopam.app_common.utils.AppConstants.ARTICLE_ID_BARCODE_LENGTH_8;
import static it.gruppopam.app_common.utils.AppConstants.ARTICLE_ID_MAX_LENGTH;

public class PamBarcode extends BaseEan8Barcode {

    @Inject
    public PamBarcode() {
    }

    @Override
    public boolean isValid(String barcode) {
        return barcode.length() == ARTICLE_ID_BARCODE_LENGTH_8
            && getCheckDigitNumber(barcode) == calculateCheckDigit(barcode);
    }

    @Override
    public int getCheckDigitNumber(String barcode) {
        return Integer.parseInt("" + barcode.charAt(barcode.length() - 1));
    }

    @Override
    public String normalizeBarcode(String barcode) {
        return String.valueOf(new ArticleIdUtils(ARTICLE_ID_BARCODE_LENGTH_8,
                ARTICLE_ID_MAX_LENGTH).extractArticleId(barcode));
    }

    @Override
    public boolean shouldRetrieveFromDb() {
        return false;
    }

}
