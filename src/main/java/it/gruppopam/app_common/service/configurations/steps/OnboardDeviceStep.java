package it.gruppopam.app_common.service.configurations.steps;

import static it.gruppopam.app_common.service.configurations.ConfigurationStatus.KO;
import static it.gruppopam.app_common.service.configurations.ConfigurationStatus.OK;
import static it.gruppopam.app_common.service.configurations.ConfigurationStepName.ONBOARD_DEVICE;
import static it.gruppopam.app_common.utils.AppConstants.SOCKET_TAG;

import android.net.TrafficStats;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import it.gruppopam.app_common.dto.OnBoardDeviceDto;
import it.gruppopam.app_common.events.configurations.OnBoardDeviceEvent;
import it.gruppopam.app_common.exceptions.OnBoardDeviceFailedException;
import it.gruppopam.app_common.network.api.DeviceAppManagerApi;
import it.gruppopam.app_common.service.configurations.BaseConfigurationStep;
import it.gruppopam.app_common.service.configurations.ConfigurationContext;
import it.gruppopam.app_common.utils.CommonAppPreferences;

import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class OnboardDeviceStep extends BaseConfigurationStep {
    private final DeviceAppManagerApi deviceAppManagerApi;

    @Inject
    public OnboardDeviceStep(CommonAppPreferences appPreference, EventBus eventBus, DeviceAppManagerApi deviceAppManagerApi) {
        super(appPreference, eventBus, ONBOARD_DEVICE);
        this.deviceAppManagerApi = deviceAppManagerApi;
    }

    @Override
    public boolean isExecuted() {
        return isStepCompleted();
    }

    @SneakyThrows
    @Override
    protected void onExecute(ConfigurationContext configurationContext) {
        TrafficStats.setThreadStatsTag(SOCKET_TAG);
        Response<ResponseBody> response = deviceAppManagerApi
                .onBoardDeviceSynchronous(configurationContext.getDeviceId(), buildOnBoardDeviceDto(configurationContext))
                .execute();
        if (!response.isSuccessful()) {
            throw new OnBoardDeviceFailedException();
        }
    }

    private OnBoardDeviceDto buildOnBoardDeviceDto(ConfigurationContext configurationContext) {
        return new OnBoardDeviceDto(configurationContext.getDeviceId(), configurationContext.getStoreId(), configurationContext.getAppName(), configurationContext.getSerialNumber());
    }

    @Override
    public void notifyStepCompleted() {
        if (isExecuted()) {
            eventBus.post(new OnBoardDeviceEvent(OK));
        }
    }

    @Override
    public void notifyStepInError() {
        eventBus.post(new OnBoardDeviceEvent(KO));
    }
}
