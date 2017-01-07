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
    private final Mapper<StationEntity, StationRealm> toRealmMapper;
    private final Mapper<StationRealm, StationEntity> toEntityMapper;

    public StationRealmRepository(
            RealmProvider realmProvider,
            Mapper<StationEntity, StationRealm> toRealmMapper,
            Mapper<StationRealm, StationEntity> toEntityMapper) {

        this.realmProvider = realmProvider;
        this.toRealmMapper = toRealmMapper;
        this.toEntityMapper = toEntityMapper;
    }

    @Override
    public Completable add(Iterable<StationEntity> items) {
        final RealmWrapper realmWrapper = new RealmWrapper();
        return Observable.from(items)
                .doOnSubscribe(() -> {
                    realmWrapper.realm = realmProvider.provide();
                    realmWrapper.realm.beginTransaction();
                })
                .map(toRealmMapper::from)
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
        final RealmWrapper realmWrapper = new RealmWrapper();
        final RealmSpecification realmSpecification = ((RealmSpecification) specification);
        return Observable.just(item)
                .doOnSubscribe(() -> {
                    realmWrapper.realm = realmProvider.provide();
                    realmWrapper.realm.beginTransaction();
                })
                .map(toRealmMapper::from)
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
                .map(toEntityMapper::from)
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
