package it.gruppopam.app_common.repository;

import java.util.concurrent.Callable;

public interface TransactionManager {

    void runInTransaction(VoidCallable callable);

    <V> V runInTransactionWithResult(Callable<V> body);
}
