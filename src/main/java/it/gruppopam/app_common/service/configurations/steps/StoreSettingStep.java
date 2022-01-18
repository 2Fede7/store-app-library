package it.gruppopam.app_common.service.configurations.steps;

import static it.gruppopam.app_common.service.configurations.ConfigurationStatus.KO;
import static it.gruppopam.app_common.service.configurations.ConfigurationStatus.OK;
import static it.gruppopam.app_common.service.configurations.ConfigurationStepName.STORE_SETTING;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import it.gruppopam.app_common.events.configurations.StoreSettingEvent;
import it.gruppopam.app_common.exceptions.MissingStoreIdException;
import it.gruppopam.app_common.service.configurations.BaseConfigurationStep;
import it.gruppopam.app_common.service.configurations.ConfigurationContext;
import it.gruppopam.app_common.utils.CommonAppPreferences;
import lombok.SneakyThrows;

public class StoreSettingStep extends BaseConfigurationStep {

    @Inject
    public StoreSettingStep(CommonAppPreferences appPreference, EventBus eventBus) {
        super(appPreference, eventBus, STORE_SETTING);
    }

    @Override
    public boolean isExecuted() {
        return isStepCompleted();
    }

    @SneakyThrows
    @Override
    public void onExecute(ConfigurationContext configurationContext) {
        if (configurationContext.getStoreId() != null) {
            appPreference.setStoreId(configurationContext.getStoreId().toString());
            appPreference.setAppAutoconfigured();
        } else {
            throw new MissingStoreIdException();
        }
    }

    @Override
    public void notifyStepCompleted() {
        if (isExecuted()) {
            eventBus.post(new StoreSettingEvent(OK));
        }
    }

    @Override
    public void notifyStepInError() {
        eventBus.post(new StoreSettingEvent(KO));
    }
}
