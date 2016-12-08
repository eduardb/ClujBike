package com.deveddy.clujbike.data.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.deveddy.clujbike.data.repository.mapper.Mapper;
import com.deveddy.clujbike.data.repository.models.StationEntity;
import com.deveddy.clujbike.data.repository.specifications.Specification;
import com.deveddy.clujbike.data.repository.specifications.SqlSpecification;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StationSqlRepositoryTest {
    private StationSqlRepository sut;

    @Mock
    Mapper<StationEntity, ContentValues> contentValuesDataMapper;
    @Mock
    Mapper<Cursor, StationEntity> cursorDataMapper;
    @Mock
    DatabaseHelper dataBaseHelper;
    @Mock
    SQLiteDatabase db;
    @Mock
    ContentValues contentValuesItem;
    @Mock
    SpecificationSql specificationSql;

    private String TABLE = StationSqlRepository.TABLE;
    private List<ContentValues> contentValues = Arrays.asList(
            contentValuesItem,
            contentValuesItem);

    private interface SpecificationSql extends SqlSpecification, Specification {
    }

    @Before
    public void setUp() throws Exception {
        sut = new StationSqlRepository(dataBaseHelper, contentValuesDataMapper, cursorDataMapper);
        when(dataBaseHelper.getWritableDatabase()).thenReturn(db);
        when(dataBaseHelper.getReadableDatabase()).thenReturn(db);
    }

    @Test
    public void givenItems_whenAddingToRepositorySuccessfully_thenExpectedMethodsAreCalled() {
        List<StationEntity> stationEntities = givenStationEntities();
        setupMapperFor(stationEntities, contentValues);

        sut.add(stationEntities)
                .subscribeOn(Schedulers.immediate())
                .observeOn(Schedulers.immediate())
                .subscribe(new TestSubscriber<StationEntity>());

        verify(contentValuesDataMapper, times(2)).from(any(StationEntity.class));
        InOrder inOrder = inOrder(db);
        inOrder.verify(db).beginTransaction();
        inOrder.verify(db, times(2)).insert(
                eq(TABLE),
                eq(null),
                any(ContentValues.class));
        inOrder.verify(db).setTransactionSuccessful();
        inOrder.verify(db).endTransaction();
        inOrder.verify(db).close();
    }

    @Test
    public void givenItems_whenAddingToRepositoryWithError_thenTransactionIsRolledBack() {
        List<StationEntity> stationEntities = givenStationEntities();
        setupMapperFor(stationEntities, contentValues);
        Error error = new Error("A wild error has occurred!");
        when(db.insert(anyString(), anyString(), any(ContentValues.class))).thenThrow(error);

        TestSubscriber<StationEntity> testSubscriber = new TestSubscriber<>();
        sut.add(stationEntities)
                .subscribeOn(Schedulers.immediate())
                .observeOn(Schedulers.immediate())
                .subscribe(testSubscriber);

        InOrder inOrder = inOrder(db);
        inOrder.verify(db).endTransaction();
        inOrder.verify(db).close();
        testSubscriber.assertError(error);
    }

    @Test
    public void givenItemAndSpecification_whenUpdatingItemSuccessfully_thenExpectedMethodsAreCalled() {
        StationEntity item = givenStationEntity("Columbus Circle", 3);
        when(contentValuesDataMapper.from(item)).thenReturn(contentValuesItem);

        sut.update(item, specificationSql)
                .subscribeOn(Schedulers.immediate())
                .observeOn(Schedulers.immediate())
                .subscribe(new TestSubscriber<StationEntity>());

        verify(contentValuesDataMapper).from(any(StationEntity.class));
        InOrder inOrder = inOrder(db);
        inOrder.verify(db).update(
                TABLE,
                contentValuesItem,
                specificationSql.whereClause(),
                specificationSql.whereArgs());
        inOrder.verify(db).close();
    }

    @Test
    public void givenItemAndSpecification_whenUpdatingItemUnsuccessfully_thenThrowError() {
        StationEntity item = givenStationEntity("Columbus Circle", 3);
        TestSubscriber testSubscriber = new TestSubscriber<>();
        Error error = new Error("A wild error has occurred!");
        when(contentValuesDataMapper.from(item)).thenReturn(contentValuesItem);
        when(db.update(
                TABLE,
                contentValuesItem,
                specificationSql.whereClause(),
                specificationSql.whereArgs())
        ).thenThrow(error);

        sut.update(item, specificationSql)
                .subscribeOn(Schedulers.immediate())
                .observeOn(Schedulers.immediate())
                .subscribe(testSubscriber);

        verify(contentValuesDataMapper).from(any(StationEntity.class));
        testSubscriber.assertError(error);
        verify(db).close();
    }

    @Test
    public void givenSpecification_whenRemovingItemsSuccessfully_thenExpectedMethodsAreCalled() {
        sut.remove(specificationSql)
                .subscribeOn(Schedulers.immediate())
                .observeOn(Schedulers.immediate())
                .subscribe(new TestSubscriber<>());

        InOrder inOrder = inOrder(db);
        inOrder.verify(db).delete(
                TABLE,
                specificationSql.whereClause(),
                specificationSql.whereArgs());
        inOrder.verify(db).close();
    }

    @Test
    public void givenSpecification_whenRemovingItemsUnsuccessfully_thenThrowError() {
        Error error = new Error("A wild error has occurred!");
        TestSubscriber testSubscriber = new TestSubscriber<>();
        when(db.delete(
                TABLE,
                specificationSql.whereClause(),
                specificationSql.whereArgs()))
                .thenThrow(error);

        sut.remove(specificationSql)
                .subscribeOn(Schedulers.immediate())
                .observeOn(Schedulers.immediate())
                .subscribe(testSubscriber);

        testSubscriber.assertError(error);
        verify(db).close();
    }

    @Test
    public void givenSpecification_whenQueryingRepositorySuccessfully_thenExpectedMethodsAreCalled() {
        sut.query(specificationSql)
                .subscribeOn(Schedulers.immediate())
                .observeOn(Schedulers.immediate())
                .subscribe(new TestSubscriber<>());

        InOrder inOrder = inOrder(db);
        inOrder.verify(db).query(
                TABLE,
                specificationSql.whereArgs(),
                specificationSql.whereClause(),
                null, null, null, null);
        verify(cursorDataMapper).from(any(Cursor.class));
        inOrder.verify(db).close();
    }

    @Test
    public void givenSpecification_whenQueryingRepositoryUnsuccessfully_thenThrowError() {
        Error error = new Error("A wild error has occurred!");
        TestSubscriber testSubscriber = new TestSubscriber<>();
        when(db.query(
                TABLE,
                specificationSql.whereArgs(),
                specificationSql.whereClause(),
                null, null, null, null)).thenThrow(error);

        sut.query(specificationSql)
                .subscribeOn(Schedulers.immediate())
                .observeOn(Schedulers.immediate())
                .subscribe(testSubscriber);

        testSubscriber.assertError(error);
        verify(db).close();
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

    private void setupMapperFor(List<StationEntity> stationEntities, List<ContentValues> contentValues) {
        for (int i = 0; i < stationEntities.size(); i++) {
            when(contentValuesDataMapper.from(stationEntities.get(i)))
                    .thenReturn(contentValues.get(i));
        }
    }
}
