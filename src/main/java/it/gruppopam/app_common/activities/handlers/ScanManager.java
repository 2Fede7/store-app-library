package it.gruppopam.app_common.activities.handlers;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import javax.inject.Inject;

import it.gruppopam.app_common.di.module.annotation.ForApplication;

public class ScanManager {
    public static final String SOFT_SCAN_TRIGGER = "com.motorolasolutions.emdk.datawedge.api.ACTION_SOFTSCANTRIGGER";
    public static final String EMDK_EXTRA_PARAM = "com.motorolasolutions.emdk.datawedge.api.EXTRA_PARAMETER";
    public static final String START_SCANNING = "START_SCANNING";
    public static final String STOP_SCANNING = "STOP_SCANNING";
    private static final String LOG_TAG = ScanManager.class.getName();
    private Context appContext;

    @Inject
    public ScanManager(@ForApplication Context context) {
        this.appContext = context;
    }

    public void startScanning() {
        Intent intent = new Intent(SOFT_SCAN_TRIGGER);
        intent.putExtra(EMDK_EXTRA_PARAM, START_SCANNING);
        appContext.sendBroadcast(intent);
        Log.d(LOG_TAG, "Broadcasting Request to Scan Article");
    }

    public void stopScanning() {
        Intent intent = new Intent(SOFT_SCAN_TRIGGER);
        intent.putExtra(EMDK_EXTRA_PARAM, STOP_SCANNING);
        appContext.sendBroadcast(intent);
    }
}

