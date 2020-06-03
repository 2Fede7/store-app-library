package it.gruppopam.app_common.utils;

public interface BarcodeStrategy {

    boolean isValid(String barcode);

    int getCheckDigitNumber(String barcode);

    String normalizeBarcode(String barcode);

    boolean shouldRetrieveFromDb();

}
