package it.gruppopam.app_common.barcode;

import com.annimon.stream.Optional;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public abstract class BaseBarcodeCalculationTemplate {

    protected BaseBarcodeCalculationTemplate() {

    }

    private Boolean isEmpty(String barcode) {
        return StringUtils.isEmpty(barcode);
    }

    public abstract boolean isValid(String barcode) throws InvalidBarcodeException;

    public Integer getCheckDigit(String barcode) throws InvalidBarcodeException {
        if (!isEmpty(barcode)) {
            return Integer.parseInt("" + barcode.charAt(barcode.length() - 1));
        }
        throw new InvalidBarcodeException();
    }

    public abstract String normalizeBarcode(String barcode);

    protected abstract Long extractCode(String barcode);

    public final Optional<Long> computeCode(String barcode) throws InvalidBarcodeException {
        if (isEmpty(barcode) || !isValid(barcode)) {
            return Optional.empty();
        }

        return Optional.of(extractCode(normalizeBarcode(barcode)));
    }

    public abstract boolean isValidSearch(String searchString);

    public abstract List findArticles(String searchString, int MAX_SEARCH_RESULTS);

    protected int calculateCheckDigit(String barcode) {

        int evens = 0;
        int odds = 0;
        int checkSum = 0;

        for (int i = 0; i < barcode.length() - 1; i++) {
            int digit = Integer.parseInt("" + barcode.charAt(i));

            //check if number is odd or even
            if (i % 2 == 0) {
                evens += digit;
            } else {
                odds += digit;
            }
        }

        int total = calculateTotal(barcode, evens, odds);
        return total % 10 == 0 ? checkSum : 10 - (total % 10);

    }

    private int calculateTotal(String barcode, int evens, int odds) {
        int a = evens;
        int b = odds;

        if (barcode.length() % 2 == 0) {
            a *= 3; // multiply evens by three
        } else {
            b *= 3; // multiply odds by three
        }
        return a + b;
    }

}
