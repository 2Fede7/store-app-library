package it.gruppopam.app_common.network.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import it.gruppopam.app_common.events.ServiceReachabilityEvent;
import lombok.Getter;
import lombok.Setter;

@Getter
public class ConnectionChecker {
    @Setter
    private int maxConsecutiveFailures = 5;
    private final Context context;
    private final EventBus eventBus;
    private boolean servicesReachable = true;
    private boolean connectedToNetwork = true;
    private int consecutiveFailures = 0;


    @Inject
    public ConnectionChecker(Context context, EventBus eventBus) {
        this.context = context;
        this.eventBus = eventBus;
    }

    public Boolean isConnectedToNetwork() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        boolean connected = activeNetworkInfo != null && activeNetworkInfo.isConnected();
        manageNetworkIssue(connected, servicesReachable);
        return connected;
    }

    private void manageNetworkIssue(boolean connected, boolean servicesReachable) {
        if (connected != connectedToNetwork) {
            postReachabilityEvent(connected, servicesReachable);
        }
        connectedToNetwork = connected;
    }

    public void markServicesUnreachable() {
        if (++consecutiveFailures >= maxConsecutiveFailures) {
            servicesReachable = false;
            postReachabilityEvent(connectedToNetwork, servicesReachable);
        }
    }

    public void markServicesReachable() {
        servicesReachable = true;
        consecutiveFailures = 0;
        postReachabilityEvent(connectedToNetwork, servicesReachable);
    }

    private void postReachabilityEvent(boolean connectedToNetwork, boolean servicesReachable) {
        eventBus.post(new ServiceReachabilityEvent(connectedToNetwork, servicesReachable));
    }

}
