package it.gruppopam.app_common.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import dagger.android.DaggerIntentService;
import it.gruppopam.app_common.alarms.common.JobsHandlers;
import it.gruppopam.app_common.utils.ExceptionLogger;

public abstract class BaseSyncDataIntentService extends DaggerIntentService {

    private final String TAG = this.getClass().getSimpleName();

    private static final String CHANNEL_ID = "SynchronizingDataChannel";

    public BaseSyncDataIntentService(String name) {
        super(name);
        Log.d(TAG, "Created a new instance of " + name);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForeground();
        }

    }

    @Override
    public void onDestroy() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            stopForeground();
        }
        super.onDestroy();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            Log.i(getClass().getName(), "Received intent to handle alarm " + intent.getAction());
            getHandlers().execute(jobName(intent.getAction()));
        } catch (Exception e) {
            ExceptionLogger.logError(TAG, "No Alarm handler found for " + intent.getAction(), e);
        }
    }

    protected abstract JobsHandlers getHandlers();

    protected abstract String jobName(String action);

    @RequiresApi(Build.VERSION_CODES.O)
    protected void buildChannel() {
        NotificationChannel channel = new NotificationChannel(getChannelId(), getChannelName(), NotificationManager.IMPORTANCE_NONE);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(channel);
    }

    protected abstract CharSequence getChannelName();

    protected String getChannelId() {
        return CHANNEL_ID;
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private PendingIntent buildIntent() {
        return PendingIntent.getActivity(this, 0, new Intent(), 0);
    }

    @RequiresApi(Build.VERSION_CODES.O)
    protected abstract void startForeground();

    @RequiresApi(Build.VERSION_CODES.O)
    private void stopForeground() {
        Log.d(TAG, "Stop foreground service.");

        // Stop foreground service and remove the notification.
        stopForeground(true);

        // Stop the foreground service.
        stopSelf();
    }

}
