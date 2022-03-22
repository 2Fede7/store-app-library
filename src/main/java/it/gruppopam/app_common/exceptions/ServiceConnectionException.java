package it.gruppopam.app_common.exceptions;

import java.io.IOException;

public class ServiceConnectionException extends IOException {

    public ServiceConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceConnectionException(String message) {
        super(message);
    }
}
