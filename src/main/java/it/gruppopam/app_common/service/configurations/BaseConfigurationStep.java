package it.gruppopam.app_common.service.configurations;

import org.greenrobot.eventbus.EventBus;

import it.gruppopam.app_common.utils.CommonAppPreferences;
import it.gruppopam.app_common.utils.ExceptionLogger;

public abstract class BaseConfigurationStep implements ConfigurationStep {

    protected CommonAppPreferences appPreference;

    protected EventBus eventBus;

    private final ConfigurationStepName stepName;

    public BaseConfigurationStep(CommonAppPreferences appPreference, EventBus eventBus, ConfigurationStepName stepName) {
        this.appPreference = appPreference;
        this.eventBus = eventBus;
        this.stepName = stepName;
    }

    @Override
    public void execute(ConfigurationContext configurationContext) {
        try {
            onExecute(configurationContext);
            markAsExecutedAndNotify();
        } catch (Exception e) {
            ExceptionLogger.logError(getClass().getSimpleName(), e.getMessage());
            notifyStepInError();
            throw e;
        }
    }

    protected abstract void onExecute(ConfigurationContext configurationContext);

    protected final void markAsExecutedAndNotify() {
        markStepAsCompleted();
        notifyStepCompleted();
    }

    protected boolean isStepCompleted() {
        return appPreference.isStepCompleted(stepName);
    }

    protected void markStepAsCompleted() {
        appPreference.markStepAsCompleted(stepName);
    }

}
