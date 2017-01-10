package com.deveddy.clujbike.data.repository;

import com.deveddy.clujbike.data.repository.mapper.station.StationEntityRealmMapper;
import com.deveddy.clujbike.data.repository.mapper.station.StationRealmEntityMapper;
import com.deveddy.clujbike.data.repository.models.StationEntity;
import com.deveddy.clujbike.data.repository.models.StationRealm;
import com.deveddy.clujbike.data.repository.specifications.RealmSpecification;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StationRealmRepositoryTest {

    private StationRealmRepository sut;

    @Mock
    StationEntityRealmMapper entityToRealmMapper;
    @Mock
    StationRealmEntityMapper realmToEntityMapper;
    @Mock
    RealmProvider realmProvider;
    @Mock
    Realm realm;
    @Mock
    RealmSpecification realmSpecification;
    @Mock
    StationRealm stationRealmItem;
    @Mock
    RealmQuery<StationRealm> query;
    @Mock
    StationRealm newStationRealm;
    @Mock
    StationRealm oldStationRealm;
    @Mock
    StationEntity stationEntity;

    private List<StationRealm> stationRealmList = Arrays.asList(
            stationRealmItem,
            stationRealmItem);

    @Before
    public void setUp() throws Exception {
        sut = new StationRealmRepository(realmProvider, entityToRealmMapper, realmToEntityMapper);
        when(realmProvider.provide()).thenReturn(realm);
    }

    @Test
    public void givenItems_whenAddingToRepositorySuccessfully_thenExpectedMethodsAreCalled() {
        List<StationEntity> stationEntityList = givenStationEntities();
        setUpMapperFor(stationEntityList, stationRealmList);

        sut.add(stationEntityList)
                .subscribeOn(Schedulers.immediate())
                .observeOn(Schedulers.immediate())
                .subscribe(new TestSubscriber<StationEntity>());

        InOrder inOrder = inOrder(realm, entityToRealmMapper);
        inOrder.verify(realm).beginTransaction();
        inOrder.verify(entityToRealmMapper).from(stationEntityList.get(0));
        inOrder.verify(realm).insert(stationRealmList.get(0));
        inOrder.verify(entityToRealmMapper).from(stationEntityList.get(1));
        inOrder.verify(realm).insert(stationRealmList.get(1));
        inOrder.verify(realm).commitTransaction();
        inOrder.verify(realm).close();
    }

    @Test
    public void givenItems_whenAddingToRepositoryUnsuccessfully_thenThrowError() {
        List<StationEntity> stationEntityList = givenStationEntities();
        TestSubscriber testSubscriber = new TestSubscriber();
        Error error = new Error("A wild error has occurred!");
        setUpMapperFor(stationEntityList, stationRealmList);
        doThrow(error).when(realm).insert(stationRealmList.get(0));

        sut.add(stationEntityList)
                .subscribeOn(Schedulers.immediate())
                .observeOn(Schedulers.immediate())
                .subscribe(testSubscriber);

        InOrder inOrder = inOrder(realm);
        inOrder.verify(realm).beginTransaction();
        inOrder.verify(realm).cancelTransaction();
        inOrder.verify(realm).close();
        testSubscriber.assertError(error);
    }

    @Test
    public void givenItemAndSpecification_whenUpdatingItemSuccessfully_thenExpectedMethodsAreCalled() {
        StationEntity stationEntity = givenStationEntity("Times Square", 15);
        setUpMapperForItem(stationEntity, newStationRealm);
        provideSpecificationQueryMockResult();

        sut.update(stationEntity, realmSpecification)
                .subscribeOn(Schedulers.immediate())
                .observeOn(Schedulers.immediate())
                .subscribe(new TestSubscriber<StationEntity>());

        InOrder inOrder = inOrder(realm, entityToRealmMapper, realmSpecification);
        inOrder.verify(realm).beginTransaction();
        inOrder.verify(entityToRealmMapper).from(stationEntity);
        inOrder.verify(realm).copyToRealmOrUpdate(newStationRealm);
        inOrder.verify(realm).commitTransaction();
        inOrder.verify(realm).close();
    }

    @Test
    public void givenItemAndSpecification_whenUpdatingItemUnsuccessfully_thenThrowError() {
        StationEntity stationEntity = givenStationEntity("Times Square", 15);
        Error error = new Error("A wild error has occurred!");
        TestSubscriber testSubscriber = new TestSubscriber();
        setUpMapperForItem(stationEntity, newStationRealm);
        provideSpecificationQueryMockResult();
        doThrow(error).when(realm).copyToRealmOrUpdate(newStationRealm);

        sut.update(stationEntity, realmSpecification)
                .subscribeOn(Schedulers.immediate())
                .observeOn(Schedulers.immediate())
                .subscribe(testSubscriber);

        InOrder inOrder = inOrder(realm, entityToRealmMapper);
        inOrder.verify(realm).beginTransaction();
        inOrder.verify(entityToRealmMapper).from(stationEntity);
        inOrder.verify(realm).cancelTransaction();
        inOrder.verify(realm).close();
        testSubscriber.assertError(error);
    }

    @Test
    public void givenSpecification_whenQueryingSuccessfully_thenExpectedMethodsAreCalled() {
        provideSpecificationQueryMockResult();

        sut.query(realmSpecification)
                .subscribeOn(Schedulers.immediate())
                .observeOn(Schedulers.immediate())
                .subscribe(new TestSubscriber<>());

        InOrder inOrder = inOrder(realm, realmToEntityMapper);
        inOrder.verify(realm).beginTransaction();
        inOrder.verify(realmToEntityMapper).from(oldStationRealm);
        inOrder.verify(realm).commitTransaction();
        inOrder.verify(realm).close();
    }

    @Test
    public void givenSpecification_whenQueryingUnsuccessfully_thenThrowError() {
        provideSpecificationQueryMockResult();
        TestSubscriber testSubscriber = new TestSubscriber();
        Error error = new Error("A wild error has occurred!");
        doThrow(error).when(query).findFirst();

        sut.query(realmSpecification)
                .subscribeOn(Schedulers.immediate())
                .observeOn(Schedulers.immediate())
                .subscribe(testSubscriber);

        InOrder inOrder = inOrder(realm);
        inOrder.verify(realm).beginTransaction();
        inOrder.verify(realm).cancelTransaction();
        inOrder.verify(realm).close();
        testSubscriber.assertError(error);
    }

    private StationEntity givenStationEntity(String name, int id) {
        return StationEntity.builder()
                .name(name)
                .address("Manhattan")
                .occupiedSpots(2)
                .emptySpots(4)
                .maximumNumberOfBikes(6)
                .lastSyncDate(new Date(System.currentTimeMillis()))
                .idStatus(0)
                .statusFunctional(true)
                .statusAvailable(true)
                .latitude(34.33f)
                .longitude(34.44f)
                .valid(true)
                .customValid(true)
                .id(id)
                .build();
    }

    private List<StationEntity> givenStationEntities() {
        List<StationEntity> items = new ArrayList<>();
        items.add(givenStationEntity("Union Square", 1));
        items.add(givenStationEntity("Herald Square", 2));
        return items;
    }

    private void setUpMapperForItem(StationEntity stationEntity, StationRealm stationRealm) {
        when(entityToRealmMapper.from(stationEntity)).thenReturn(stationRealm);
    }

    private void setUpMapperFor(List<StationEntity> stationEntityList,
                                List<StationRealm> stationRealmList) {
        for (int i = 0; i < stationEntityList.size(); i++) {
            setUpMapperForItem(stationEntityList.get(i), stationRealmList.get(i));
        }
    }

    private void provideSpecificationQueryMockResult() {
        when(realmSpecification.toRealmQuery(realm)).thenReturn(query);
        when(query.findFirst()).thenReturn(oldStationRealm);
    }

}