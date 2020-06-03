package it.gruppopam.app_common.utils;

abstract class BaseEan13Barcode implements BarcodeStrategy {

    @Override
    public int getCheckDigitNumber(String barcode) {
        return Integer.parseInt("" + barcode.charAt(barcode.length() - 1));
    }

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

        odds = odds * 3; // multiply odds by three

        int total = odds + evens;

        return total % 10 == 0 ? checkSum : 10 - (total % 10);

    }

}
