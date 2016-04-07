package com.deveddy.clujbike.data.repository;

import java.util.List;

import rx.Completable;
import rx.Observable;

public interface Repositoy<T> {

    Completable add(T item);

    Completable add(Iterable<T> items);

    Completable update(T item);

    Completable remove(T item);

    Completable remove(Specification specification);

    Observable<List<T>> query(Specification specification);
}
