package it.gruppopam.app_common.barcode;

import lombok.Getter;

public enum BarcodeType {

    EAN8("LABEL_TYPE_EAN8"),

    EAN13("LABEL_TYPE_EAN13"),

    UPCA("LABEL_TYPE_UPCA"),

    UPCE("LABEL_TYPE_UPCE0"),

    NONE(null);

    @Getter
    String labelName;

    BarcodeType(String name) {
        this.labelName = name;
    }

}
