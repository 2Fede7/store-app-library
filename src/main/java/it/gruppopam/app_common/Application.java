package it.gruppopam.app_common;

import static it.gruppopam.app_common.utils.AppConstants.DEVICE_ID;
import static it.gruppopam.app_common.utils.AppConstants.DEVICE_IP;
import static it.gruppopam.app_common.utils.AppConstants.STORE_ID;

import androidx.annotation.NonNull;

import org.acra.ACRA;
import org.acra.ReportField;
import org.acra.config.CoreConfigurationBuilder;
import org.acra.config.HttpSenderConfiguration;
import org.acra.config.HttpSenderConfigurationBuilder;
import org.acra.data.StringFormat;
import org.acra.sender.HttpSender;

import it.gruppopam.app_common.utils.DeviceUtils;
import it.gruppopam.app_common.utils.NetworkUtils;

public abstract class Application extends android.app.Application {

    protected void setupCustomData(String storeId) {
        ACRA.getErrorReporter().putCustomData(DEVICE_ID, new DeviceUtils(this).getDeviceId());
        ACRA.getErrorReporter().putCustomData(STORE_ID, storeId);
        ACRA.getErrorReporter().putCustomData(DEVICE_IP, NetworkUtils.getIpAddress(this));
    }

    protected CoreConfigurationBuilder getCoreConfigurationBuilder(boolean sendReportsInDevMode,
                                                                 String krakenUrl) {
        // Configuration example from ACRA v.5.9.0.rc1: https://github.com/ACRA/acra/releases/tag/acra-5.9.0-rc1
        return new CoreConfigurationBuilder()
                .withReportFormat(StringFormat.JSON)
                .withLogcatArguments("-t", "200", "-s", "DWAPI")
                .withReportContent(
                        ReportField.CUSTOM_DATA,
                        ReportField.ANDROID_VERSION,
                        ReportField.APP_VERSION_CODE,
                        ReportField.APP_VERSION_NAME,
                        ReportField.BUILD_CONFIG,
                        ReportField.STACK_TRACE,
                        ReportField.USER_APP_START_DATE,
                        ReportField.USER_CRASH_DATE
                )
                .withSendReportsInDevMode(sendReportsInDevMode)
                .withPluginConfigurations(getHttpSenderConfiguration(krakenUrl));
    }

    @NonNull
    private HttpSenderConfiguration getHttpSenderConfiguration(String krakenUrl) {
        return new HttpSenderConfigurationBuilder()
                .withUri(krakenUrl)
                .withHttpMethod(HttpSender.Method.POST)
                .withCompress(false)
                .withEnabled(true)
                .withConnectionTimeout(60000)
                .withSocketTimeout(60000)
                .build();
    }
}
