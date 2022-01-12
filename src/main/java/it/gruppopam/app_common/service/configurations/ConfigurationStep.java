package it.gruppopam.app_common.service.configurations;

public interface ConfigurationStep {
    boolean isExecuted();

    void execute(ConfigurationContext configurationContext);

    void notifyStepCompleted();

    void notifyStepInError();

}
