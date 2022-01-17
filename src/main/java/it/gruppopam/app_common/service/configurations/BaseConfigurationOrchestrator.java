package it.gruppopam.app_common.service.configurations;

import com.annimon.stream.Stream;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import it.gruppopam.app_common.events.ConfigurationCompletedEvent;
import it.gruppopam.app_common.utils.CommonAppPreferences;
import it.gruppopam.app_common.utils.DeviceUtils;
import lombok.Getter;

import static com.annimon.stream.Collectors.toList;

public class BaseConfigurationOrchestrator {
    private static String appName;
    private final DeviceUtils deviceUtils;
    @Getter
    private final List<ConfigurationStep> steps;
    private final EventBus eventBus;
    private final CommonAppPreferences appPreference;

    public BaseConfigurationOrchestrator(String appName, CommonAppPreferences appPreference, EventBus eventBus, DeviceUtils deviceUtils, List<ConfigurationStep> steps) {
        BaseConfigurationOrchestrator.appName = appName;
        this.appPreference = appPreference;
        this.eventBus = eventBus;
        this.deviceUtils = deviceUtils;
        this.steps = steps;
    }

    public void executeSteps(Long storeId) {
        ConfigurationContext configurationContext = new ConfigurationContext(storeId, deviceUtils.getDeviceId(), appName, deviceUtils.getSerialNumber());
        executeSteps(collectNotExecutedSteps(), configurationContext);
        notifyEndOfConfiguration();
    }

    private void executeSteps(List<ConfigurationStep> configurationSteps, ConfigurationContext configurationContext) {
        for (ConfigurationStep configurationStep : configurationSteps) {
            try {
                configurationStep.execute(configurationContext);
            } catch (Exception e) {
                break;
            }
        }
    }

    private List<ConfigurationStep> collectNotExecutedSteps() {
        return Stream.of(steps).filterNot(ConfigurationStep::isExecuted).collect(toList());
    }

    private void notifyEndOfConfiguration() {
        if (isAppFullyConfigured()) {
            eventBus.post(new ConfigurationCompletedEvent());
        }
    }

    public boolean isAppFullyConfigured() {
        return Stream.of(steps).allMatch(ConfigurationStep::isExecuted);
    }

    public boolean isAppAutoconfigured() {
        return appPreference.isAppAutoconfigured();
    }

    public void notifyExecutedSteps() {
        Stream.of(steps).forEach(ConfigurationStep::notifyStepCompleted);
    }

    public void retryOnFailureOccurs() {
        if (appPreference.isStoreIdPresent() && isConfigurationProcedureStartedButNotCompleted()) {
            executeSteps(appPreference.getStoreId());
        }
    }

    private boolean isConfigurationProcedureStartedButNotCompleted() {
        return isAppAutoconfigured() && !isAppFullyConfigured();
    }

    public boolean isAppNotConfigured() {
        return !appPreference.isStoreIdPresent();
    }

}
