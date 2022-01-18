package it.gruppopam.app_common.service.configurations.steps;

import static it.gruppopam.app_common.service.configurations.ConfigurationStatus.KO;
import static it.gruppopam.app_common.service.configurations.ConfigurationStatus.OK;
import static it.gruppopam.app_common.service.configurations.ConfigurationStepName.FIRST_USER_LOGIN;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import it.gruppopam.app_common.events.configurations.FirstUserLoginEvent;
import it.gruppopam.app_common.service.CommonAuthenticationService;
import it.gruppopam.app_common.service.configurations.BaseConfigurationStep;
import it.gruppopam.app_common.service.configurations.ConfigurationContext;
import it.gruppopam.app_common.utils.CommonAppPreferences;

public class FirstUserLoginStep extends BaseConfigurationStep {

    private final CommonAuthenticationService authenticationService;

    @Inject
    public FirstUserLoginStep(CommonAppPreferences appPreference, EventBus eventBus, CommonAuthenticationService authenticationService) {
        super(appPreference, eventBus, FIRST_USER_LOGIN);
        this.authenticationService = authenticationService;
    }

    @Override
    public boolean isExecuted() {
        return isStepCompleted();
    }

    @Override
    public void onExecute(ConfigurationContext configurationContext) {
        authenticationService.loginAsStoreUser();
    }

    @Override
    public void notifyStepCompleted() {
        if (isExecuted()) {
            eventBus.post(new FirstUserLoginEvent(OK));
        }
    }

    @Override
    public void notifyStepInError() {
        eventBus.post(new FirstUserLoginEvent(KO));
    }

}
