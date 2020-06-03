package it.gruppopam.app_common.activities.handlers;

import android.view.MotionEvent;
import android.view.View;

import javax.inject.Inject;

public class ScanArticleListener implements View.OnTouchListener {

    private ScanManager scanner;

    @Inject
    public ScanArticleListener(ScanManager scanner) {
        this.scanner = scanner;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            startScan();
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            stopScan();
            return true;
        }
        return false;
    }

    private void stopScan() {
        if (scanner != null) {
            scanner.stopScanning();
        }
    }

    private void startScan() {
        if (scanner != null) {
            scanner.startScanning();
        }
    }

}
