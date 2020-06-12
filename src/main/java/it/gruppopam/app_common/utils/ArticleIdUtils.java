package it.gruppopam.app_common.utils;

import static it.gruppopam.app_common.utils.AppConstants.ARTICLE_ID_MIN_LENGTH;
import static it.gruppopam.app_common.utils.AppConstants.INVALID_ARTICLE_ID;
import static it.gruppopam.app_common.utils.AppConstants.MASK_VALUE_FOR_ARTICLE_ID;
import static it.gruppopam.app_common.utils.AppConstants.PREFIX_CHAR_OF_VALID_BARCODE;
import static it.gruppopam.app_common.utils.BarcodeUtils.isNormalizeEan8Pam;

public final class ArticleIdUtils {


    private int articleIdBarcodeLength;
    private int articleIdMaxLength;

    public ArticleIdUtils(int articleIdBarcodeLength, int articleIdMaxLength) {
        this.articleIdBarcodeLength = articleIdBarcodeLength;
        this.articleIdMaxLength = articleIdMaxLength;
    }

    public long extractArticleId(String maskedArticleBarCode) {
        String processedMaskedArticleBarCode = maskedArticleBarCode.trim();
        if (!isValidBarCode(processedMaskedArticleBarCode)) {
            return INVALID_ARTICLE_ID;
        }
        return Long.parseLong(processedMaskedArticleBarCode) - MASK_VALUE_FOR_ARTICLE_ID;
    }

    public boolean isValidBarCode(String maskedArticleBarCode) {
        if (isNormalizeEan8Pam(maskedArticleBarCode)) {
            return true;
        } else {
            return maskedArticleBarCode.length() == articleIdBarcodeLength
                    && maskedArticleBarCode.charAt(0) == PREFIX_CHAR_OF_VALID_BARCODE;
        }
    }

    public boolean isValidArticleId(String articleId) {
        int length = articleId.length();
        boolean lengthInRange = length >= ARTICLE_ID_MIN_LENGTH &&
                length <= articleIdMaxLength;
        return lengthInRange && isDigitsOnly(articleId);
    }

    public boolean isDigitsOnly(CharSequence str) {
        final int len = str.length();
        int cp;
        for (int i = 0; i < len; i += Character.charCount(cp)) {
            cp = Character.codePointAt(str, i);
            if (!Character.isDigit(cp)) {
                return false;
            }
        }
        return true;
    }

}
