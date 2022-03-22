package it.gruppopam.app_common.network.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;

import org.greenrobot.eventbus.EventBus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import it.gruppopam.app_common.network.util.ConnectionChecker;

@RunWith(RobolectricTestRunner.class)
@Config(shadows = CustomNetworkSecurityPolicy.class, sdk = 27)
public class ConnectionCheckerTest {

    Application mockApplication;
    @Mock
    ConnectivityManager mockConnectivityManager;
    @Mock
    EventBus eventBus;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void isConnectedToNetworkTest() {
        mockApplication = mock(Application.class);

        when(mockApplication.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(mockConnectivityManager);
        when(mockConnectivityManager.getActiveNetworkInfo()).thenReturn(null);

        ConnectionChecker connectionChecker = new ConnectionChecker(mockApplication, eventBus);
        assertFalse(connectionChecker.isConnectedToNetwork());
    }

    @Test
    public void markServicesUnReachableTest() {
        mockApplication = mock(Application.class);
        ConnectionChecker connectionChecker = new ConnectionChecker(mockApplication, eventBus);

        int[] fiveConsecutiveFailures = new int[4];
        for (int i = 0; i <= fiveConsecutiveFailures.length; i++) {
            connectionChecker.markServicesUnreachable();
        }

        assertFalse(connectionChecker.isServicesReachable());
    }

    @Test
    public void markServicesReachableTest() {
        mockApplication = mock(Application.class);
        ConnectionChecker connectionChecker = new ConnectionChecker(mockApplication, eventBus);

        int[] fiveConsecutiveFailures = new int[4];
        for (int i = 0; i <= fiveConsecutiveFailures.length; i++) {
            connectionChecker.markServicesUnreachable();
        }

        connectionChecker.markServicesReachable();

        assertTrue(connectionChecker.isServicesReachable());
    }
}
