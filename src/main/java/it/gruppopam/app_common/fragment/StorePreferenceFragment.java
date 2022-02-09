package it.gruppopam.app_common.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import it.gruppopam.app_common.R;
import it.gruppopam.app_common.events.ManualConfigurationCompletedEvent;
import it.gruppopam.app_common.events.NetworkChangeEvent;
import it.gruppopam.app_common.events.SettingsFetchCompleteEvent;
import it.gruppopam.app_common.events.SettingsFetchFailedEvent;
import it.gruppopam.app_common.service.CommonDeviceSettings;
import it.gruppopam.app_common.service.DataSync;
import it.gruppopam.app_common.utils.CommonAppPreferences;

public class StorePreferenceFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
    private static final String STORE_ID_KEY = "storeIdPref";

    private final CommonDeviceSettings deviceSettings;
    private final EventBus eventBus;
    private final DataSync dataSync;
    private final CommonAppPreferences commonAppPreferences;

    @Inject
    public StorePreferenceFragment(CommonDeviceSettings deviceSettings,
                                   EventBus eventBus, DataSync dataSync,
                                   CommonAppPreferences commonAppPreferences) {
        this.deviceSettings = deviceSettings;
        this.eventBus = eventBus;
        this.dataSync = dataSync;
        this.commonAppPreferences = commonAppPreferences;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventBus.register(this);
        addPreferencesFromResource(R.xml.fragment_store_preference);
        registerSharedPreferenceChangeListener();
        updatePreferenceInSummary();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toggleStoreSettingsEnabling();
    }

    private void toggleStoreSettingsEnabling() {
        Preference storeIdPreference = findStoreIdPreference();
        storeIdPreference.setEnabled(!deviceSettings.isProperStoreIdSet());
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String key) {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        eventBus.unregister(this);
        unregisterPreferenceChangeListener();
    }

    @Override
    public void onResume() {
        super.onResume();
        registerSharedPreferenceChangeListener();
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterPreferenceChangeListener();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (STORE_ID_KEY.equals(key)) {
            updatePreferenceInSummary();
            toggleStoreSettingsEnabling();
            if (deviceSettings.isProperStoreIdSet()) {
                deviceSettings.triggerSettingsFetch();
            }
        }
    }

    @Subscribe
    public void onSettingsFetchComplete(SettingsFetchCompleteEvent event) {
        dataSync.pullAllData();
        eventBus.post(new ManualConfigurationCompletedEvent());
    }

    @Subscribe
    public void onSettingFetchFailed(SettingsFetchFailedEvent event) {
        if (!deviceSettings.areFetchedSettingsValid()) {
            commonAppPreferences.removeStoreId();
        }
    }

    @Subscribe
    public void onNetworkChangedEvent(NetworkChangeEvent event) {
        if (event.isConnected() && deviceSettings.isProperStoreIdSet()) {
            deviceSettings.triggerSettingsFetch();
        }
    }

    private void updatePreferenceInSummary() {
        Preference preference = findStoreIdPreference();
        if (preference == null) {
            return;
        }

        preference.setSummary(retrieveStoreIdAsString());
    }

    private String retrieveStoreIdAsString() {
        return deviceSettings.isProperStoreIdSet() ? Long.toString(commonAppPreferences.getStoreId()) : "";
    }

    private Preference findStoreIdPreference() {
        return findPreference(STORE_ID_KEY);
    }

    private SharedPreferences getSharedPreferences() {
        return getPreferenceScreen().getSharedPreferences();
    }

    private void registerSharedPreferenceChangeListener() {
        getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    private void unregisterPreferenceChangeListener() {
        getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }
}

