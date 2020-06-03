package it.gruppopam.app_common.model;

public interface Persistable<T> {

    Long persist(T object);

    void beginTransaction();

    void setTransactionSuccessful();

    void endTransaction();
}
