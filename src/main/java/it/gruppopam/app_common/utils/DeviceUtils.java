package it.gruppopam.app_common.utils;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;

import javax.inject.Inject;
import javax.inject.Singleton;

import it.gruppopam.app_common.di.module.annotation.ForApplication;

import static android.provider.Settings.Secure.ANDROID_ID;
import static org.apache.commons.lang3.StringUtils.EMPTY;

@Singleton
public class DeviceUtils {

    private final Context context;

    @Inject
    public DeviceUtils(@ForApplication Context context) {
        this.context = context;
    }

    @SuppressLint("HardwareIds")
    public String getDeviceId() {
        return Settings.Secure.getString(context.getContentResolver(), ANDROID_ID);
    }

    public String getSerialNumber() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                return Build.getSerial();
            } else {
                return Build.SERIAL;
            }
        } catch (Exception e) {
            return EMPTY;
        }
    }
}
