package it.gruppopam.app_common.service.configurations.steps;

import static it.gruppopam.app_common.service.configurations.ConfigurationStatus.KO;
import static it.gruppopam.app_common.service.configurations.ConfigurationStatus.OK;
import static it.gruppopam.app_common.service.configurations.ConfigurationStepName.FINALIZE_CONFIGURATION;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import it.gruppopam.app_common.events.configurations.FinalizeConfigurationEvent;
import it.gruppopam.app_common.service.configurations.BaseConfigurationStep;
import it.gruppopam.app_common.service.configurations.ConfigurationContext;
import it.gruppopam.app_common.utils.CommonAppPreferences;

public class FinalizeConfigurationStep extends BaseConfigurationStep {

    @Inject
    public FinalizeConfigurationStep(CommonAppPreferences appPreference, EventBus eventBus) {
        super(appPreference, eventBus, FINALIZE_CONFIGURATION);
    }

    @Override
    public boolean isExecuted() {
        return isStepCompleted();
    }

    @Override
    public void onExecute(ConfigurationContext configurationContext) {
        //DO NOTHING
    }

    @Override
    public void notifyStepCompleted() {
        if (isExecuted()) {
            eventBus.post(new FinalizeConfigurationEvent(OK));
        }
    }

    @Override
    public void notifyStepInError() {
        eventBus.post(new FinalizeConfigurationEvent(KO));
    }
}
