package it.gruppopam.app_common.model;

import java.util.List;

import it.gruppopam.app_common.repository.VoidCallable;

public interface Persistable<T> {

    Long persist(T object);

    Long persist(List<T> objects);

    void runInTransaction(VoidCallable callable);
}
