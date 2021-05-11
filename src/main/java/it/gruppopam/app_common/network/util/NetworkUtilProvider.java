package it.gruppopam.app_common.network.util;

@SuppressWarnings("PMD.ClassNamingConventions")
public final class NetworkUtilProvider {

    private static NetworkUtil instance;

    private NetworkUtilProvider() {
    }

    public static NetworkUtil get() {
        if (instance == null) {
            instance = new NetworkUtil();
        }
        return instance;
    }

    public static void set(NetworkUtil util) {
        instance = util;
    }
}
