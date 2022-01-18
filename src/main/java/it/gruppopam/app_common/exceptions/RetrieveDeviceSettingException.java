package it.gruppopam.app_common.exceptions;

public class RetrieveDeviceSettingException extends Exception {
    public RetrieveDeviceSettingException() {
        super("Unable to retrieve device Settings");
    }
}
