package it.gruppopam.app_common.events;

import lombok.Getter;

@Getter
public class ShowScanErrorFragmentEvent {

    private String scannedBarcode;

    public ShowScanErrorFragmentEvent(String scannedBarcode) {
        this.scannedBarcode = scannedBarcode;
    }

    public ShowScanErrorFragmentEvent() {
    }

}
