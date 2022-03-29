package it.gruppopam.app_common.network.interceptor;

import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import it.gruppopam.app_common.exceptions.ServiceConnectionException;
import it.gruppopam.app_common.network.util.ConnectionChecker;
import it.gruppopam.app_common.network.utils.CustomNetworkSecurityPolicy;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

@RunWith(RobolectricTestRunner.class)
@Config(shadows = CustomNetworkSecurityPolicy.class, sdk = 27)
public class NetworkInterceptorTest {
    @Rule
    public MockWebServer mockWebServer;
    String host;
    private OkHttpClient client;
    @Mock
    private ConnectionChecker connectionChecker;

    private String serviceUrl = "example.com";

    @Before
    public void setup() throws IOException {
        MockitoAnnotations.openMocks(this);
        mockWebServer = new MockWebServer();
        mockWebServer.start(9998);
        host = mockWebServer.getHostName() + ":" + mockWebServer.getPort();
        when(connectionChecker.isConnectedToNetwork()).thenReturn(true);
        client = new OkHttpClient.Builder()
                .addInterceptor(new NetworkInterceptor(connectionChecker, serviceUrl))
                .readTimeout(1L, TimeUnit.MILLISECONDS)
                .writeTimeout(1L, TimeUnit.MILLISECONDS)
                .build();
    }

    @After
    public void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test(expected = ServiceConnectionException.class)
    public void shouldShowFooterWhenReadServiceDown() throws IOException {
        URL url = new URL("http://www.google.it");
        Request testRequest = new Request.Builder().url(url).build();
        client.newCall(testRequest).execute();
    }

    @Test(expected = ServiceConnectionException.class)
    public void shouldShowFooterWhenWriteServiceDown() throws IOException {
        RequestBody body = RequestBody.create(MediaType.parse("JSON"), "add_article_71901_response.json");
        Request request = new Request.Builder()
            .url("http://" + host + "/drafts/stores/400/articles")
            .put(body)
            .build();
        client.newCall(request).execute();
    }
}
