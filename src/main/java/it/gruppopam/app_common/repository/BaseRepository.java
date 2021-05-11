package it.gruppopam.app_common.repository;

import android.database.Cursor;

import androidx.sqlite.db.SimpleSQLiteQuery;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.regex.Pattern;

import it.gruppopam.app_common.dao.IDao;

import static it.gruppopam.app_common.utils.RegexConstants.SNAKE_CASE_STRING_REGEX;

public abstract class BaseRepository<T> {
    private final IDao<T> dao;
    private final TransactionManager transactionManager;

    public BaseRepository(TransactionManager transactionManager, IDao<T> dao) {
        this.transactionManager = transactionManager;
        this.dao = dao;
    }

    public boolean booleanToInt(boolean booleanValue) {
        return booleanValue;
    }

    public SimpleSQLiteQuery buildQuery(String rawQuery) {
        return new SimpleSQLiteQuery(rawQuery);
    }

    public SimpleSQLiteQuery buildQuery(String rawQuery, Object... objects) {
        return new SimpleSQLiteQuery(rawQuery, objects);
    }

    public long getMaxSequenceNumber(String tableName) {
        if (!Pattern.matches(SNAKE_CASE_STRING_REGEX, tableName)) {
            throw new RuntimeException("Invalid table name: " + tableName);
        }
        return dao.selectLong(buildQuery("SELECT MAX(sequence_number) FROM " + tableName));
    }

    public long count(String query, Object... objects) {
        return dao.selectLong(buildQuery(query, objects));
    }

    public boolean existsQuery(String rawQuery, Object... objects) {
        return dao.selectBoolean(buildQuery(rawQuery, objects));
    }

    public int deleteAllQuery(String rawQuery, Object... objects) {
        return dao.deleteAll(buildQuery(rawQuery, objects));
    }

    public T deleteQuery(String rawQuery, Object... objects) {
        return (T) dao.delete(buildQuery(rawQuery, objects));
    }

    public T selectSingle(String rawQuery, Object... objects) {
        return (T) dao.select(buildQuery(rawQuery, objects));
    }

    public List<T> select(String rawQuery, Object... objects) {
        return (List<T>) dao.selectMany(buildQuery(rawQuery, objects));
    }

    public Cursor selectCursor(String rawQuery, Object... objects) {
        return dao.selectCursor(buildQuery(rawQuery, objects));
    }

    public void runInTransaction(VoidCallable callable) {
        transactionManager.runInTransaction(callable::call);
    }

    public <V> V runInTransactionWithResult(Callable<V> body) {
        return transactionManager.runInTransactionWithResult(body::call);
    }
}
