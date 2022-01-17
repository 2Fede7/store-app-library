package it.gruppopam.app_common.events;

import it.gruppopam.app_common.service.configurations.ConfigurationStepName;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ConfigurationStepCompletedEvent {
    private ConfigurationStepName stepName;
}
