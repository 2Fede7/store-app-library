package it.gruppopam.app_common.response_handlers;

import it.gruppopam.app_common.model.Persistable;
import it.gruppopam.app_common.utils.ExceptionLogger;
import retrofit2.Response;

// TODO: Use DependencyInjection to simplify
public abstract class ResponseHandler<U, V> {
    protected final String LOG_TAG = this.getClass().getSimpleName();

    public void handleResponse(Response<U> response, Persistable<V> repository) throws Throwable {
        if (response.isSuccessful()) {
            processSuccessfulResponse(response, repository);
        } else {
            manageError(response, repository);
        }

    }

    public void manageError(Response<U> response, Persistable<V> repository) {
        String message = "Response return code was not successful (status code out of range 200-300)";
        String exceptionMessage = ExceptionLogger.logAcraReport(LOG_TAG, message, response);
        throw new RuntimeException(exceptionMessage);
    }

    private void processSuccessfulResponse(Response<U> response, Persistable<V> repository) throws Throwable {
        try {
            preTransaction(response);
            repository.beginTransaction();
            inTransactionHandle(response, repository);
            repository.setTransactionSuccessful();
        } catch (Exception throwable) {
            ExceptionLogger.logError(LOG_TAG, "Error while parsing json response", throwable);
        } finally {
            endTransaction(repository);
        }
    }

    protected void preTransaction(Response<U> response) {
    }

    protected void endTransaction(Persistable<V> repository) {
        repository.endTransaction();
    }

    protected abstract void inTransactionHandle(Response<U> response, Persistable<V> repository) throws Throwable;
}
