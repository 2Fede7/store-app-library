package it.gruppopam.app_common.events;

public class SettingsFetchCompleteEvent {

    private Result result;

    public SettingsFetchCompleteEvent(Result result) {
        this.result = result;
    }

    public enum Result {
        COMPLETED,
        FAILED
    }

}
