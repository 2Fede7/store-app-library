package it.gruppopam.app_common.network.interceptor;

import static it.gruppopam.app_common.utils.AppConstants.SOCKET_TAG;

import android.content.Context;
import android.net.TrafficStats;
import android.provider.Settings;

import java.io.IOException;
import java.util.UUID;

import okhttp3.Request;
import okhttp3.Response;

public class CustomHeadersInterceptor extends BaseHeadersInterceptor {

    public static final String CLIENT_HEADER_VAL = "store_replenishment_app";
    public static final String VERSION_ID_HEADER = "X-Version-Id";
    public static final String VERSION_TYPE_HEADER = "X-Version-Type";

    private final String username;
    private final Long storeId;
    private final String versionId;
    private final String versionType;
    private final boolean disableUpdateChecker;

    public CustomHeadersInterceptor(Context applicationContext, String username,
                                    Long storeId, String versionId, String versionType,
                                    boolean disableUpdateChecker, String appName) {
        super(applicationContext,
                appName);
        this.username = username;
        this.storeId = storeId;
        this.versionId = versionId;
        this.versionType = versionType;
        this.disableUpdateChecker = disableUpdateChecker;
    }

    public Response intercept(Chain chain) throws IOException {
        TrafficStats.setThreadStatsTag(SOCKET_TAG);
        Request originalRequest = chain.request();

        String uuid = UUID.randomUUID().toString();
        String deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        Request requestWithHeader = buildRequestInterceptor(originalRequest, uuid, deviceId)
                .build();

        return chain.proceed(requestWithHeader);
    }

    protected Request.Builder buildRequestInterceptor(Request originalRequest, String uuid, String deviceId) {
        if (disableUpdateChecker) {
            return super.buildRequestInterceptor(originalRequest, uuid, deviceId, username, storeId);
        }
        return super.buildRequestInterceptor(originalRequest, uuid, deviceId, username, storeId,
                versionId, versionType);
    }
}
