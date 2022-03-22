package it.gruppopam.app_common.network.interceptor;

import static org.apache.commons.lang3.StringUtils.isEmpty;
import static it.gruppopam.app_common.utils.AppConstants.SOCKET_TAG;

import android.net.TrafficStats;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import it.gruppopam.app_common.exceptions.ServiceConnectionException;
import it.gruppopam.app_common.network.util.ConnectionChecker;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkInterceptor implements Interceptor {
    private final int SERVICE_UNAVAILABLE_CODE = 503;
    private final ConnectionChecker connectionChecker;
    private final String serviceUrl;

    public NetworkInterceptor(ConnectionChecker connectionChecker, String serviceUrl) {
        this.connectionChecker = connectionChecker;
        this.serviceUrl = serviceUrl;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        TrafficStats.setThreadStatsTag(SOCKET_TAG);
        if (connectionChecker.isConnectedToNetwork()) {
            return executeRequest(chain);
        } else {
            connectionChecker.markServicesUnreachable();
            throw new ServiceConnectionException("No network");
        }
    }

    private Response executeRequest(Chain chain) throws IOException {
        Request request = chain.request();
        try {
            Response response = chain.proceed(request);
            processResponse(request, response);
            return response;
        } catch (SocketTimeoutException exception) {
            markServiceUnreachable(request);
            throw new ServiceConnectionException("Service not reachable", exception);
        } catch (ConnectException exception) {
            markServiceUnreachable(request);
            throw new ServiceConnectionException("Server down", exception);
        } catch (SocketException exception) {
            markServiceUnreachable(request);
            throw new ServiceConnectionException("Socket broken", exception);
        }
    }

    private void processResponse(Request request, Response response) {
        if (response.code() == SERVICE_UNAVAILABLE_CODE) {
            markServiceUnreachable(request);
        } else {
            markServiceReachable(request);
        }
    }

    private void markServiceReachable(Request request) {
        if (isSrsRequest(request)) {
            connectionChecker.markServicesReachable();
        }
    }

    private void markServiceUnreachable(Request request) {
        if (isSrsRequest(request)) {
            connectionChecker.markServicesUnreachable();
        }
    }

    private boolean isSrsRequest(Request request) {
        return !isEmpty(serviceUrl) &&
            request.url().toString().contains(serviceUrl);
    }

}
