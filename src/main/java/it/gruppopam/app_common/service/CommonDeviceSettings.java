package it.gruppopam.app_common.service;

import static it.gruppopam.app_common.events.SettingsFetchCompleteEvent.Result.COMPLETED;
import static it.gruppopam.app_common.events.SettingsFetchCompleteEvent.Result.FAILED;
import static it.gruppopam.app_common.utils.AppConstants.SOCKET_TAG;

import android.content.Context;
import android.net.TrafficStats;
import android.os.StrictMode;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

import it.gruppopam.app_common.events.SettingsFetchCompleteEvent;
import it.gruppopam.app_common.exceptions.RetrieveDeviceSettingException;
import it.gruppopam.app_common.network.api.DeviceAppManagerApi;
import it.gruppopam.app_common.utils.CommonAppPreferences;
import it.gruppopam.app_common.utils.ExceptionLogger;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@NoArgsConstructor
public abstract class CommonDeviceSettings {

    public static final String TAG = CommonDeviceSettings.class.getCanonicalName();
    private static final String DEVICE_SETTINGS_FETCH_FAILED = "Device Settings fetch failed";
    private CommonAppPreferences appPreferences;
    private DeviceAppManagerApi deviceAppManagerApi;
    private Context context;

    public CommonDeviceSettings(CommonAppPreferences appPreferences, DeviceAppManagerApi deviceAppManagerApi, Context context) {
        this.appPreferences = appPreferences;
        this.deviceAppManagerApi = deviceAppManagerApi;
        this.context = context;
    }

    public Integer getStoreOrdinalNumber() {
        return appPreferences.getStoreOrdinalNumber();
    }

    public Integer getEnabledStoresNumber() {
        return appPreferences.getEnabledStoresNumber();
    }

    public void triggerSettingsFetch() {
        deviceAppManagerApi
                .getSettings(this.appPreferences.getStoreId())
                .enqueue(getSettingsFetchCallback());
        TrafficStats.setThreadStatsTag(SOCKET_TAG);
    }

    @SneakyThrows
    public HashMap<String, String> fetchSettings() {
        TrafficStats.setThreadStatsTag(SOCKET_TAG);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
        try {
            Response<HashMap<String, String>> response = deviceAppManagerApi
                    .getSettings(this.appPreferences.getStoreId())
                    .execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                throw new RetrieveDeviceSettingException();
            }
        } finally {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().build());
        }
    }

    private Callback<HashMap<String, String>> getSettingsFetchCallback() {
        return new Callback<HashMap<String, String>>() {
            @Override
            public void onResponse(Call<HashMap<String, String>> call, Response<HashMap<String, String>> response) {
                if (response.isSuccessful()) {
                    appPreferences.loadServerSettings(response.body());
                    if (areFetchedSettingsValid()) {
                        EventBus.getDefault().post(new SettingsFetchCompleteEvent(COMPLETED));
                    }
                } else {
                    handleFailure(DEVICE_SETTINGS_FETCH_FAILED + " - Status : " + response.code(), null);
                }
            }

            @Override
            public void onFailure(Call<HashMap<String, String>> call, Throwable t) {
                handleFailure(DEVICE_SETTINGS_FETCH_FAILED, t);
            }

            void handleFailure(String message, Throwable t) {
                if (areFetchedSettingsValid()) {
                    EventBus.getDefault().post(new SettingsFetchCompleteEvent(FAILED));
                } else {
                    Toast.makeText(
                            context,
                            DEVICE_SETTINGS_FETCH_FAILED,
                            Toast.LENGTH_SHORT
                    ).show();
                    ExceptionLogger.logError(TAG, message, t);
                }
            }
        };
    }

    public abstract boolean areFetchedSettingsValid();

    public boolean areStoreCountersValid() {
        return !(getStoreOrdinalNumber() == null || getEnabledStoresNumber() == null);
    }
}
