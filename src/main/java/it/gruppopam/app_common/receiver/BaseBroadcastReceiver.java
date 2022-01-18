package it.gruppopam.app_common.receiver;

import static java.lang.String.format;

import android.content.BroadcastReceiver;
import android.content.Intent;

import androidx.annotation.NonNull;

public abstract class BaseBroadcastReceiver extends BroadcastReceiver {
    private final static String ACTION_DATA_INTENT_LOG = "Action: ";
    private final static String URI_DATA_INTENT_LOG = "URI: ";
    private final static String PAYLOAD_DATA_INTENT_LOG = "data: ";
    private final static String DATA_INTENT = "data";
    private final static String DATA_INTENT_LOG = ACTION_DATA_INTENT_LOG + "%s " + URI_DATA_INTENT_LOG + "%s " + PAYLOAD_DATA_INTENT_LOG + "%s ";

    @NonNull
    public String formatLogIntentData(Intent intentReceived) {
        return format(DATA_INTENT_LOG, intentReceived.getAction(),
            intentReceived.toUri(Intent.URI_INTENT_SCHEME),
            intentReceived.getStringExtra(DATA_INTENT));
    }
}
