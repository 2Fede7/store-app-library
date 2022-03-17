package it.gruppopam.app_common.events;

import lombok.Getter;

@Getter
public class ServiceReachabilityEvent {
    private final boolean connectedToNetwork;
    private final boolean reachable;

    public ServiceReachabilityEvent(boolean connectedToNetwork, boolean reachable) {
        this.connectedToNetwork = connectedToNetwork;
        this.reachable = reachable;
    }
}
