package it.gruppopam.app_common.repository;

import androidx.room.RoomDatabase;

import java.util.concurrent.Callable;

public class BaseTransactionManager implements TransactionManager {

    private final RoomDatabase database;

    public BaseTransactionManager(RoomDatabase database) {
        this.database = database;
    }

    @Override
    public void runInTransaction(VoidCallable callable) {
        database.runInTransaction(() -> {
            callable.call();
            return null;
        });
    }

    @Override
    public <V> V runInTransactionWithResult(Callable<V> body) {
        return database.runInTransaction(body::call);
    }
}
