package it.gruppopam.app_common.network.interceptor;

import android.content.Context;

import javax.inject.Inject;

import it.gruppopam.app_common.di.module.annotation.ForApplication;
import okhttp3.Interceptor;
import okhttp3.Request;

public abstract class BaseHeadersInterceptor implements Interceptor {
    public static final String DEVICE_ID_HEADER = "X-Device-Id";
    public static final String USERNAME_HEADER = "X-User-Id";
    public static final String CLIENT_HEADER = "X-Client-Id";
    public static final String VERSION_ID_HEADER = "X-Version-Id";
    public static final String VERSION_TYPE_HEADER = "X-Version-Type";
    public static final String REQUEST_HEADER = "X-Request-Id";
    public static final String STORE_ID_HEADER = "X-Store-Id";
    public static final String UNKNOWN_DEVICE_ID = "UNKNOWN";

    protected final Context context;
    private String cliendHeaderVal;

    @Inject
    public BaseHeadersInterceptor(@ForApplication Context applicationContext,
                                    String cliendHeaderVal) {
        this.context = applicationContext;
        this.cliendHeaderVal = cliendHeaderVal;
    }

    protected Request.Builder buildRequestInterceptor(Request originalRequest, String uuid, String deviceId, String username, Long storeId, String versionId, String versionType) {
        Request.Builder builder = originalRequest.newBuilder()
            .header(REQUEST_HEADER, uuid)
            .header(USERNAME_HEADER, username)
            .header(CLIENT_HEADER, cliendHeaderVal)
            .header(DEVICE_ID_HEADER, deviceId != null ? deviceId : UNKNOWN_DEVICE_ID)
            .header(STORE_ID_HEADER, String.valueOf(storeId))
            .header(VERSION_ID_HEADER, versionId)
            .header(VERSION_TYPE_HEADER, versionType)
            .method(originalRequest.method(), originalRequest.body());

        return builder;
    }
}
