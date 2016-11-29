package com.deveddy.clujbike.data.repository;

import com.deveddy.clujbike.data.repository.specifications.Specification;

import rx.Completable;
import rx.Observable;

interface Repository<T> {

    Completable add(Iterable<T> items);

    Completable update(T item, Specification specification);

    Completable remove(Specification specification);

    Observable<T> query(Specification specification);
}
