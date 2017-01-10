package com.deveddy.clujbike.data.repository;

import com.deveddy.clujbike.data.repository.mapper.Mapper;
import com.deveddy.clujbike.data.repository.models.StationEntity;
import com.deveddy.clujbike.data.repository.models.StationRealm;
import com.deveddy.clujbike.data.repository.specifications.RealmSpecification;
import com.deveddy.clujbike.data.repository.specifications.Specification;

import io.realm.Realm;
import rx.Completable;
import rx.Observable;

public class StationRealmRepository implements Repository<StationEntity> {

    private final RealmProvider realmProvider;
    private final Mapper<StationEntity, StationRealm> entityToRealmMapper;
    private final Mapper<StationRealm, StationEntity> realmToEntityMapper;

    public StationRealmRepository(
            RealmProvider realmProvider,
            Mapper<StationEntity, StationRealm> entityToRealmMapper,
            Mapper<StationRealm, StationEntity> realmToEntityMapper) {

        this.realmProvider = realmProvider;
        this.entityToRealmMapper = entityToRealmMapper;
        this.realmToEntityMapper = realmToEntityMapper;
    }

    @Override
    public Completable add(Iterable<StationEntity> items) {
        final RealmWrapper realmWrapper = new RealmWrapper();
        return Observable.from(items)
                .doOnSubscribe(() -> {
                    realmWrapper.realm = realmProvider.provide();
                    realmWrapper.realm.beginTransaction();
                })
                .map(entityToRealmMapper::from)
                .doOnNext(stationRealm -> realmWrapper.realm.insert(stationRealm))
                .doOnError(throwable -> {
                    realmWrapper.realm.cancelTransaction();
                    realmWrapper.realm.close();
                })
                .doOnCompleted(() -> {
                    realmWrapper.realm.commitTransaction();
                    realmWrapper.realm.close();
                })
                .toCompletable();
    }

    @Override
    public Completable update(StationEntity item, Specification specification) {
        RealmWrapper realmWrapper = new RealmWrapper();
        RealmSpecification realmSpecification = ((RealmSpecification) specification);
        return Observable.just(item)
                .doOnSubscribe(() -> {
                    realmWrapper.realm = realmProvider.provide();
                    realmWrapper.realm.beginTransaction();
                })
                .map(entityToRealmMapper::from)
                .doOnNext(newStation -> {
                    StationRealm stationToUpdate =
                            realmSpecification.toRealmQuery(realmWrapper.realm).findFirst();
                    newStation.setId(stationToUpdate.getId());
                    realmWrapper.realm.copyToRealmOrUpdate(newStation);
                })
                .doOnError(throwable -> {
                    realmWrapper.realm.cancelTransaction();
                    realmWrapper.realm.close();
                })
                .doOnCompleted(() -> {
                    realmWrapper.realm.commitTransaction();
                    realmWrapper.realm.close();
                })
                .toCompletable();
    }

    @Override
    public Completable remove(Specification specification) {
        RealmWrapper realmWrapper = new RealmWrapper();
        RealmSpecification realmSpecification = ((RealmSpecification) specification);
        return Completable.fromAction(() -> {
            StationRealm stationToBeRemoved =
                    realmSpecification.toRealmQuery(realmWrapper.realm).findFirst();
            stationToBeRemoved.deleteFromRealm();
        })
                .doOnSubscribe(subscription -> {
                    realmWrapper.realm = realmProvider.provide();
                    realmWrapper.realm.beginTransaction();
                })
                .doOnError(throwable -> {
                    realmWrapper.realm.cancelTransaction();
                    realmWrapper.realm.close();
                })
                .doOnCompleted(() -> {
                    realmWrapper.realm.commitTransaction();
                    realmWrapper.realm.close();
                });
    }

    @Override
    public Observable<StationEntity> query(Specification specification) {
        RealmWrapper realmWrapper = new RealmWrapper();
        RealmSpecification realmSpecification = ((RealmSpecification) specification);
        return Observable
                .fromCallable(() -> realmSpecification.toRealmQuery(realmWrapper.realm).findFirst())
                .map(realmToEntityMapper::from)
                .doOnSubscribe(() -> {
                    realmWrapper.realm = realmProvider.provide();
                    realmWrapper.realm.beginTransaction();
                })
                .doOnError(throwable -> {
                    realmWrapper.realm.cancelTransaction();
                    realmWrapper.realm.close();
                })
                .doOnCompleted(() -> {
                    realmWrapper.realm.commitTransaction();
                    realmWrapper.realm.close();
                });
    }

    private static class RealmWrapper {

        Realm realm;

        RealmWrapper() {
        }
    }
}
