package it.gruppopam.app_common.dao;

import android.database.Cursor;

import androidx.room.RawQuery;
import androidx.sqlite.db.SupportSQLiteQuery;

import java.util.List;

@SuppressWarnings("PMD")
public interface IDao<Entity> {
    @RawQuery
    boolean selectBoolean(SupportSQLiteQuery query);

    @RawQuery
    long selectLong(SupportSQLiteQuery query);

    @RawQuery
    Entity select(SupportSQLiteQuery query);

    @RawQuery
    List<Entity> selectMany(SupportSQLiteQuery query);

    @RawQuery
    Cursor selectCursor(SupportSQLiteQuery query);

    @RawQuery
    int seed(SupportSQLiteQuery query);

    //TODO rename in execute query
    @RawQuery
    Entity delete(SupportSQLiteQuery query);

    @RawQuery
    int deleteAll(SupportSQLiteQuery query);

    @RawQuery
    int update(SupportSQLiteQuery query);

}
