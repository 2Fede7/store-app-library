package it.gruppopam.app_common.service.configurations;

import it.gruppopam.app_common.R;
import lombok.Getter;

@Getter
public enum ConfigurationStepName {
    STORE_SETTING(R.string.step_store_setting),
    RETRIEVE_DEVICE_SETTINGS(R.string.step_retrieve_device_settings),
    FIRST_USER_LOGIN(R.string.step_first_user_login),
    START_DATA_PULL(R.string.step_sync_all_data),
    ONBOARD_DEVICE(R.string.step_onboard_device),
    FINALIZE_CONFIGURATION(R.string.step_finalize_configuration);

    private final int stepLabel;

    ConfigurationStepName(int stepLabel) {
        this.stepLabel = stepLabel;
    }
}
