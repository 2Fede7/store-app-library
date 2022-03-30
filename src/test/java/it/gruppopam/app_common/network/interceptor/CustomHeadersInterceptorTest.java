package it.gruppopam.app_common.network.interceptor;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static java.lang.String.valueOf;

import android.content.Context;
import android.provider.Settings;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.io.IOException;
import java.util.UUID;

import it.gruppopam.app_common.network.utils.CustomNetworkSecurityPolicy;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

@RunWith(RobolectricTestRunner.class)
@Config(shadows = CustomNetworkSecurityPolicy.class, sdk = 27)
public class CustomHeadersInterceptorTest {
    @Rule
    public MockWebServer mockWebServer;
    String host;
    HttpUrl url;
    String testDeviceId = "testDeviceId";
    private final String username = "username";
    private Long storeId;
    private final String versionId = "versionId";
    private final String versionType = "versionType";
    private final boolean disableUpdateChecker = true;
    private MockResponse mockResponse;
    private OkHttpClient client;

    @Before
    public void setup() throws Exception {
        mockWebServer = new MockWebServer();
        mockResponse = new MockResponse();
        mockResponse.setBody("test").setResponseCode(200);
        mockWebServer.enqueue(mockResponse);
        mockWebServer.start(9999);
        host = mockWebServer.getHostName() + ":" + mockWebServer.getPort();
        url = mockWebServer.url("/");

        Context applicationContext = RuntimeEnvironment.application.getApplicationContext();
        storeId = 250L;
        Settings.Secure.putString(RuntimeEnvironment.application.getContentResolver(), Settings.Secure.ANDROID_ID, testDeviceId);
        client = new OkHttpClient.Builder()
                .addInterceptor(new CustomHeadersInterceptor(applicationContext, username, storeId, versionId, versionType, disableUpdateChecker))
                .build();
    }

    @After
    public void tearDown() {
        try {
            mockWebServer.shutdown();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void shouldPassDeviceIdAsUnknownIfTheAndroidDeviceIdIsNotAvailable() throws Exception {
        Settings.Secure.putString(RuntimeEnvironment.application.getContentResolver(), Settings.Secure.ANDROID_ID, null);
        Request testRequest = new Request.Builder().url(url).build();
        client.newCall(testRequest).execute();
        RecordedRequest recordedRequest = mockWebServer.takeRequest();
        assertThat(recordedRequest.getHeader(BaseHeadersInterceptor.DEVICE_ID_HEADER), is(CustomHeadersInterceptor.UNKNOWN_DEVICE_ID));
    }

    @Test
    public void testInterceptRequestShouldHaveDeviceIdInHeaders() throws Exception {
        Request testRequest = new Request.Builder().url(url).build();
        client.newCall(testRequest).execute();
        RecordedRequest recordedRequest = mockWebServer.takeRequest();
        assertThat(recordedRequest.getHeader(CustomHeadersInterceptor.DEVICE_ID_HEADER), is(testDeviceId));
    }

    @Test
    public void testInterceptRequestShouldHaveVersionIdVersionTypeAndStoreIdInHeaders() throws Exception {
        Request testRequest = new Request.Builder().url(url).build();
        client.newCall(testRequest).execute();
        RecordedRequest recordedRequest = mockWebServer.takeRequest();

        assertThat(recordedRequest.getHeader(CustomHeadersInterceptor.STORE_ID_HEADER), is(valueOf(storeId)));
        if (disableUpdateChecker) {
            assertNull(recordedRequest.getHeader(CustomHeadersInterceptor.VERSION_ID_HEADER));
            assertNull(recordedRequest.getHeader(CustomHeadersInterceptor.VERSION_TYPE_HEADER));
        } else {
            assertThat(recordedRequest.getHeader(CustomHeadersInterceptor.VERSION_ID_HEADER), is((versionId)));
            assertThat(recordedRequest.getHeader(CustomHeadersInterceptor.VERSION_TYPE_HEADER), is(versionType));
        }
    }

    @Test
    public void testInterceptRequestShouldHaveClientIdInHeaders() throws Exception {
        Request testRequest = new Request.Builder().url(url).build();
        client.newCall(testRequest).execute();
        RecordedRequest recordedRequest = mockWebServer.takeRequest();
        assertThat(recordedRequest.getHeader(CustomHeadersInterceptor.CLIENT_HEADER), is(CustomHeadersInterceptor.CLIENT_HEADER_VAL));
    }

    @Test
    public void testInterceptRequestShouldHaveRequestIdInHeaders() throws Exception {
        Request testRequest = new Request.Builder().url(url).build();
        client.newCall(testRequest).execute();
        RecordedRequest recordedRequest = mockWebServer.takeRequest();
        String requestHeader = recordedRequest.getHeader(CustomHeadersInterceptor.REQUEST_HEADER);
        assertNotNull(requestHeader);
        assertTrue(isUUID(requestHeader));
    }

    @Test
    public void testInterceptRequestShouldHaveUsernameIdInHeaders() throws Exception {
        Request testRequest = new Request.Builder().url(url).build();
        client.newCall(testRequest).execute();
        RecordedRequest recordedRequest = mockWebServer.takeRequest();
        assertThat(recordedRequest.getHeader(CustomHeadersInterceptor.USERNAME_HEADER), is(username));
    }

    public boolean isUUID(String string) {
        try {
            UUID.fromString(string);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

}
