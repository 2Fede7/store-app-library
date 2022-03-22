package it.gruppopam.app_common.network.utils;

import android.security.NetworkSecurityPolicy;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

@Implements(NetworkSecurityPolicy.class)
public class CustomNetworkSecurityPolicy {

    @Implementation
    public static NetworkSecurityPolicy getInstance() {
        try {
            Class<?> shadow = CustomNetworkSecurityPolicy.class.forName("android.security.NetworkSecurityPolicy");
            return (NetworkSecurityPolicy) shadow.newInstance();
        } catch (Exception e) {
            throw new AssertionError();
        }
    }

    @Implementation
    public boolean isCleartextTrafficPermitted(String hostname) {
        return true;
    }

    @Implementation
    public boolean isCleartextTrafficPermitted() {
        return true;
    }
}
