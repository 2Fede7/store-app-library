package it.gruppopam.app_common.service.configurations.steps;

import static it.gruppopam.app_common.service.configurations.ConfigurationStatus.KO;
import static it.gruppopam.app_common.service.configurations.ConfigurationStatus.OK;
import static it.gruppopam.app_common.service.configurations.ConfigurationStepName.RETRIEVE_DEVICE_SETTINGS;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import it.gruppopam.app_common.events.configurations.RetrieveDeviceSettingsStepResult;
import it.gruppopam.app_common.service.CommonDeviceSettings;
import it.gruppopam.app_common.service.configurations.BaseConfigurationStep;
import it.gruppopam.app_common.service.configurations.ConfigurationContext;
import it.gruppopam.app_common.utils.CommonAppPreferences;

public class RetrieveSettingsStep extends BaseConfigurationStep {
    private final CommonDeviceSettings deviceSettings;

    @Inject
    public RetrieveSettingsStep(CommonAppPreferences appPreference, EventBus eventBus, CommonDeviceSettings deviceSettings) {
        super(appPreference, eventBus, RETRIEVE_DEVICE_SETTINGS);
        this.deviceSettings = deviceSettings;
    }

    @Override
    public boolean isExecuted() {
        return isStepCompleted();
    }

    @Override
    public void onExecute(ConfigurationContext configurationContext) {
        appPreference.loadServerSettings(deviceSettings.fetchSettings());
    }

    @Override
    public void notifyStepCompleted() {
        if (isExecuted()) {
            eventBus.post(new RetrieveDeviceSettingsStepResult(OK));
        }
    }

    @Override
    public void notifyStepInError() {
        eventBus.post(new RetrieveDeviceSettingsStepResult(KO));
    }

}
