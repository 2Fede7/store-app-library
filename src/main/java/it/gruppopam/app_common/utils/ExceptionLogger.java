package it.gruppopam.app_common.utils;

import android.util.Log;

import org.acra.ACRA;
import org.acra.ErrorReporter;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Response;

public final class ExceptionLogger {
    private ExceptionLogger() {
    }

    public static void logError(String tag, String message) {
        Log.e(tag, message);
        ErrorReporter errorReporter = ACRA.getErrorReporter();
        errorReporter.clearCustomData();
        errorReporter.putCustomData("REASON", message);
        errorReporter.handleException(null);
    }

    public static void logError(String tag, String message, Throwable t) {
        Log.e(tag, message, t);
        ACRA.getErrorReporter().clearCustomData();
        ACRA.getErrorReporter().handleException(t);
    }

    public static String logAcraReport(String tag, String message, Response response) {
        String uuid = response.raw().request().header("X-Request-Id");
        Log.e(tag, message);
        try {
            String responseAsString = getBodyAsString(response);
            return String.format("MESSAGE: %s UUID : %s RESPONSE : ", message, uuid) + responseAsString.substring(0, Math.min(responseAsString.length(), 1000));
        } catch (IOException e) {
            Log.e(tag, e.getLocalizedMessage());
            return String.format("MESSAGE: %s UUID : %s ", message, uuid);
        }
    }

    public static void logError(String tag, String message, Response response) {
        //TODO this will throw a null pointer within ExceptionLogger when response is null. Be careful while mocking
        String uuid = response.raw().request().header("X-Request-Id");
        Log.e(tag, message);
        try {
            ErrorReporter errorReporter = ACRA.getErrorReporter();
            errorReporter.clearCustomData();
            errorReporter.putCustomData("UUID", uuid);
            String responseAsString = getBodyAsString(response);
            errorReporter.putCustomData("ERROR_TRACE", responseAsString);
            errorReporter.handleException(null);
        } catch (IOException e) {
            ErrorReporter errorReporter = ACRA.getErrorReporter();
            errorReporter.clearCustomData();
            errorReporter.putCustomData("UUID", uuid);
            errorReporter.handleException(null);
            Log.e(tag, e.getLocalizedMessage());
        } catch (Exception e) {
            Log.e(tag, e.getLocalizedMessage());
        }
    }

    private static String getBodyAsString(Response response) throws IOException {
        String responseAsString = "NO RESPONSE BODY";
        ResponseBody errorBody = response.errorBody();
        if (errorBody != null) {
            responseAsString = errorBody.string();
        } else if (response.body() != null) {
            responseAsString = response.body().toString();
        }

        return responseAsString.replaceAll("\\n", " ");
    }
}
