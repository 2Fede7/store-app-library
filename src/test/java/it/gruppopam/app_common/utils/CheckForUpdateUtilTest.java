package it.gruppopam.app_common.utils;

import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static it.gruppopam.app_common.utils.AppVersionUtil.DESIRED_VERSION_ID;
import static it.gruppopam.app_common.utils.AppVersionUtil.DESIRED_VERSION_TYPE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static retrofit2.Response.error;
import static retrofit2.Response.success;

import it.gruppopam.app_common.network.api.DeviceAppManagerApi;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

@RunWith(MockitoJUnitRunner.class)
public class CheckForUpdateUtilTest {

    @Mock
    private DeviceAppManagerApi deviceAppManagerApi;

    private static final String LOCALHOST_URL = "http://localhost";

    @Before
    public void setup() {

    }

    @Test
    public void testCheckVersionStoreReplenishmentOk() throws Exception {
        String app = "STORE_REPLENISHMENT_APP";
        Long version = 1L;
        Long store = 532L;
        String versionType = "EDGE";

        Request request = new Request.Builder().url(LOCALHOST_URL).build();
        ResponseBody mockBody = mock(ResponseBody.class);
        okhttp3.Response rawResponse = buildOkHttpResponse(request, 200, null, String.valueOf(version), versionType);
        Response<ResponseBody> response = success(mockBody, rawResponse);

        Call mockcall = mock(Call.class);

        when(deviceAppManagerApi.validateApkVersion(store, versionType, version)).thenReturn(mockcall);
        when(mockcall.execute()).thenReturn(response);
        CheckForUpdateUtil checkForUpdateUtil = new CheckForUpdateUtil(app, store, version, versionType, deviceAppManagerApi);

        Boolean result = checkForUpdateUtil.checkVersion();

        AppVersionDetail appVersionDetail = checkForUpdateUtil.getAppVersionDetail();
        assertTrue(result);
        assertEquals(version.longValue(), appVersionDetail.getVersionId());
    }

    @Test
    public void testCheckVersionStoreUtilitiesOk() throws Exception {
        String app = "STORE_UTILITIES_APP";
        Long version = 1L;
        Long store = 532L;
        String versionType = null;

        Request request = new Request.Builder().url(LOCALHOST_URL).build();
        ResponseBody mockBody = mock(ResponseBody.class);
        okhttp3.Response rawResponse = buildOkHttpResponse(request, 200, null, String.valueOf(version));
        Response<ResponseBody> response = success(mockBody, rawResponse);

        Call mockcall = mock(Call.class);

        when(deviceAppManagerApi.validateApkVersion(version)).thenReturn(mockcall);
        when(mockcall.execute()).thenReturn(response);
        CheckForUpdateUtil checkForUpdateUtil = new CheckForUpdateUtil(app, store, version, versionType, deviceAppManagerApi);

        Boolean result = checkForUpdateUtil.checkVersion();

        AppVersionDetail appVersionDetail = checkForUpdateUtil.getAppVersionDetail();
        assertTrue(result);
        assertEquals(version.longValue(), appVersionDetail.getVersionId());
    }

    private okhttp3.Response buildOkHttpResponse(Request priorRequest, int status, okhttp3.Response priorResponse, String apkBuildNumber, String apkBuildType) {
        return new okhttp3.Response.Builder()
                .protocol(Protocol.HTTP_1_1)
                .header(DESIRED_VERSION_ID, apkBuildNumber)
                .header(DESIRED_VERSION_TYPE, apkBuildType)
                .request(priorRequest)
                .code(status)
                .priorResponse(priorResponse)
                .build();
    }

    private okhttp3.Response buildOkHttpResponse(Request priorRequest, int status, okhttp3.Response priorResponse, String apkBuildNumber) {
        return new okhttp3.Response.Builder()
                .protocol(Protocol.HTTP_1_1)
                .header(DESIRED_VERSION_ID, apkBuildNumber)
                .request(priorRequest)
                .code(status)
                .priorResponse(priorResponse)
                .build();
    }
}