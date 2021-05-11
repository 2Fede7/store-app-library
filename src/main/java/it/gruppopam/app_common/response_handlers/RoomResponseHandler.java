package it.gruppopam.app_common.response_handlers;

import it.gruppopam.app_common.mapper.BaseMapper;
import it.gruppopam.app_common.model.Persistable;
import it.gruppopam.app_common.repository.VoidCallable;
import it.gruppopam.app_common.utils.ExceptionLogger;
import retrofit2.Response;

// TODO: Use DependencyInjection to simplify
// U -> ResponseBody
// V -> Entity
// T -> Dto
public abstract class RoomResponseHandler<U, V, T> {
    protected final String LOG_TAG = this.getClass().getSimpleName();

    public void handleResponse(Response<U> response, Persistable<V> repository, BaseMapper<T, V> baseMapper) {
        if (response.isSuccessful()) {
            processSuccessfulResponse(response, repository, baseMapper);
        } else {
            manageError(response, repository);
        }
    }

    public void manageError(Response<U> response, Persistable<V> repository) {
        String message = "Response return code was not successful (status code out of range 200-300)";
        String exceptionMessage = ExceptionLogger.logAcraReport(LOG_TAG, message, response);
        throw new RuntimeException(exceptionMessage);
    }

    private void processSuccessfulResponse(Response<U> response, Persistable<V> repository, BaseMapper<T, V> baseMapper) {
        try {
            preTransaction(response);
            repository.runInTransaction(executeInTransaction(response, repository, baseMapper));
        } catch (Exception throwable) {
            String error_message = "Error while persisting response";
            ExceptionLogger.logError(LOG_TAG, error_message, throwable);
            throw new RuntimeException(error_message, throwable);
        } finally {
            postTransaction();
        }
    }

    public VoidCallable executeInTransaction(Response<U> response, Persistable<V> repository, BaseMapper<T, V> baseMapper) {
        return () -> inTransactionHandle(response, repository, baseMapper);
    }

    protected void preTransaction(Response<U> response) {
    }

    protected abstract void inTransactionHandle(Response<U> response, Persistable<V> repository, BaseMapper<T, V> baseMapper);

    protected abstract void postTransaction();
}
