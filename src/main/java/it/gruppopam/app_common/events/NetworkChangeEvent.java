package it.gruppopam.app_common.events;

import lombok.Getter;

public class NetworkChangeEvent {

    @Getter
    private boolean connected;

    public NetworkChangeEvent(boolean connected) {
        this.connected = connected;
    }
}
