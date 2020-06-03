package it.gruppopam.app_common.utils;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

public class NetworkUtils {

    private final static String NULL_MAC_ADDRESS = "02:00:00:00:00:00";

    public static String getMacAddress() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) {
                    continue;
                }

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(Integer.toHexString(b & 0xFF) + ":");
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ignored) {
            return NULL_MAC_ADDRESS;
        }
        return NULL_MAC_ADDRESS;
    }
}
