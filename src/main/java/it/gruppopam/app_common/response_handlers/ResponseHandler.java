package it.gruppopam.app_common.response_handlers;

import it.gruppopam.app_common.model.Persistable;
import it.gruppopam.app_common.utils.ExceptionLogger;
import retrofit2.Response;

// TODO: Use DependencyInjection to simplify
public abstract class ResponseHandler<U, V> {
    protected final String LOG_TAG = this.getClass().getSimpleName();

    public void handleResponse(Response<U> response, Persistable<V> repository) {
        if (response.isSuccessful()) {
            processSuccessfulResponse(response, repository);
        } else {
            manageError(response);
        }

    }

    public void manageError(Response<U> response) {
        String message = "Response return code was not successful (status code out of range 200-300)";
        String exceptionMessage = ExceptionLogger.logAcraReport(LOG_TAG, message, response);
        throw new RuntimeException(exceptionMessage);
    }

    private void processSuccessfulResponse(Response<U> response, Persistable<V> repository) {
        try {
            preTransaction(response);
            repository.runInTransaction(() -> inTransactionHandle(response, repository));
        } catch (Exception throwable) {
            String error_message = "Error while persisting response";
            ExceptionLogger.logError(LOG_TAG, error_message, throwable);
            throw new RuntimeException(error_message, throwable);
        }
    }

    protected void preTransaction(Response<U> response) {
    }

    protected abstract void inTransactionHandle(Response<U> response, Persistable<V> repository);
}
