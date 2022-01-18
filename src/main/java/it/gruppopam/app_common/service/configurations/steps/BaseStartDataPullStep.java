package it.gruppopam.app_common.service.configurations.steps;

import static it.gruppopam.app_common.service.configurations.ConfigurationStatus.KO;
import static it.gruppopam.app_common.service.configurations.ConfigurationStatus.OK;
import static it.gruppopam.app_common.service.configurations.ConfigurationStepName.START_DATA_PULL;

import org.greenrobot.eventbus.EventBus;

import it.gruppopam.app_common.events.configurations.StartDataPullEvent;
import it.gruppopam.app_common.service.configurations.BaseConfigurationStep;
import it.gruppopam.app_common.service.configurations.ConfigurationContext;
import it.gruppopam.app_common.utils.CommonAppPreferences;

public abstract class BaseStartDataPullStep extends BaseConfigurationStep {

    public BaseStartDataPullStep(CommonAppPreferences appPreference, EventBus eventBus) {
        super(appPreference, eventBus, START_DATA_PULL);
    }

    @Override
    public boolean isExecuted() {
        return isStepCompleted();
    }

    @Override
    public void onExecute(ConfigurationContext configurationContext) {
        triggerPullDataSync();
    }

    public abstract void triggerPullDataSync();

    @Override
    public void notifyStepCompleted() {
        if (isExecuted()) {
            eventBus.post(new StartDataPullEvent(OK));
        }
    }

    @Override
    public void notifyStepInError() {
        eventBus.post(new StartDataPullEvent(KO));
    }
}
