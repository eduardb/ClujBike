package com.deveddy.clujbike.data.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.deveddy.clujbike.data.repository.SqlDatabaseContract.Station;
import com.deveddy.clujbike.data.repository.mapper.Mapper;
import com.deveddy.clujbike.data.repository.models.StationEntity;
import com.deveddy.clujbike.data.repository.specifications.Specification;
import com.deveddy.clujbike.data.repository.specifications.SqlSpecification;

import rx.Completable;
import rx.Observable;

public class StationSqlRepository implements Repository<StationEntity> {

    static final String STATION_TABLE_NAME = Station.TABLE_NAME;

    private final DatabaseHelper databaseHelper;
    private final Mapper<StationEntity, ContentValues> contentValueMapper;
    private final Mapper<Cursor, StationEntity> cursorDataMapper;

    public StationSqlRepository(DatabaseHelper databaseHelper,
                                Mapper<StationEntity, ContentValues> contentValueMapper,
                                Mapper<Cursor, StationEntity> cursorDataMapper) {
        this.databaseHelper = databaseHelper;
        this.contentValueMapper = contentValueMapper;
        this.cursorDataMapper = cursorDataMapper;
    }

    @Override
    public Completable add(Iterable<StationEntity> items) {
        final SQLiteDatabase db = databaseHelper.getWritableDatabase();
        return Observable.from(items)
                .doOnSubscribe(db::beginTransaction)
                .map(contentValueMapper::from)
                .doOnNext(contentValues -> db.insert(STATION_TABLE_NAME, null, contentValues))
                .doOnError(throwable -> closeTransactionWithoutSuccess(db))
                .doOnCompleted(() -> closeTransactionWithSuccess(db))
                .toCompletable();
    }

    private void closeTransactionWithoutSuccess(SQLiteDatabase db) {
        db.endTransaction();
        db.close();
    }

    private void closeTransactionWithSuccess(SQLiteDatabase db) {
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }

    @Override
    public Completable update(StationEntity item, Specification specification) {
        final SQLiteDatabase db = databaseHelper.getWritableDatabase();
        final SqlSpecification sqlSpecification = ((SqlSpecification) specification);
        return Observable.just(item)
                .map(contentValueMapper::from)
                .doOnNext(contentValues -> db.update(STATION_TABLE_NAME,
                        contentValues,
                        sqlSpecification.whereClause(),
                        sqlSpecification.whereArgs()))
                .doOnCompleted(db::close)
                .toCompletable();
    }

    @Override
    public Completable remove(Specification specification) {
        final SQLiteDatabase db = databaseHelper.getWritableDatabase();
        final SqlSpecification sqlSpecification = ((SqlSpecification) specification);
        return Completable.fromAction(
                () -> db.delete(STATION_TABLE_NAME,
                        sqlSpecification.whereClause(),
                        sqlSpecification.whereArgs()))
                .doOnCompleted(db::close);
    }

    @Override
    public Observable<StationEntity> query(Specification specification) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        SqlSpecification sqlSpecification = ((SqlSpecification) specification);
        return Observable
                .fromCallable(() -> db.query(STATION_TABLE_NAME,
                        sqlSpecification.whereArgs(),
                        sqlSpecification.whereClause(),
                        null, null, null, null))
                .map((response) -> cursorDataMapper.from(response))
                .doOnCompleted(db::close);
    }
}
