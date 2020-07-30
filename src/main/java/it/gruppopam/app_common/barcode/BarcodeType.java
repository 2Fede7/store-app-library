package it.gruppopam.app_common.barcode;

import lombok.Getter;

public enum BarcodeType {

    EAN8("LABEL-TYPE-EAN8"),

    EAN13("LABEL-TYPE-EAN13"),

    UPCA("LABEL-TYPE-UPCA"),

    UPCE("LABEL-TYPE-UPCE0"),

    NONE(null);

    @Getter
    String labelName;

    BarcodeType(String name) {
        this.labelName = name;
    }

}
