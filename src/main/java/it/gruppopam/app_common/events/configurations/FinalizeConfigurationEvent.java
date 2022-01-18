package it.gruppopam.app_common.events.configurations;

import it.gruppopam.app_common.service.configurations.ConfigurationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FinalizeConfigurationEvent {
    private ConfigurationStatus configurationStatus;
}
