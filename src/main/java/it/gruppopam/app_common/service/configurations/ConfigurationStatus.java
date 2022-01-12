package it.gruppopam.app_common.service.configurations;

import it.gruppopam.app_common.R;
import lombok.Getter;

public enum ConfigurationStatus {
    OK(R.string.configuration_ok), KO(R.string.configuration_ko), WAITING(R.string.configuration_waiting);

    @Getter
    private final int configurationStatus;

    ConfigurationStatus(int configuration_status) {
        this.configurationStatus = configuration_status;
    }

    public boolean isStepExecutedCorrectly() {
        return this.equals(OK);
    }

    public boolean isStepInWaiting() {
        return this.equals(WAITING);
    }

    public boolean isStepInError() {
        return this.equals(KO);
    }
}
