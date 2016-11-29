package com.deveddy.clujbike.data.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.deveddy.clujbike.data.repository.SqlDataBaseContract.Station;
import com.deveddy.clujbike.data.repository.mapper.DataMapper;
import com.deveddy.clujbike.data.repository.models.StationEntity;
import com.deveddy.clujbike.data.repository.specifications.Specification;
import com.deveddy.clujbike.data.repository.specifications.SqlSpecification;

import rx.Completable;
import rx.Observable;

public class StationSqlRepository implements Repository<StationEntity> {

    static final String STATION_TABLE_NAME = Station.TABLE_NAME;

    private final DatabaseHelper databaseHelper;
    private final DataMapper<StationEntity, ContentValues> contentValueDataMapper;
    private final DataMapper<Cursor, StationEntity> cursorDataMapper;

    public StationSqlRepository(DatabaseHelper databaseHelper,
                                DataMapper<StationEntity, ContentValues> contentValueDataMapper,
                                DataMapper<Cursor, StationEntity> cursorDataMapper) {
        this.databaseHelper = databaseHelper;

        this.contentValueDataMapper = contentValueDataMapper;
        this.cursorDataMapper = cursorDataMapper;
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
    public Completable add(Iterable<StationEntity> items) {
        final SQLiteDatabase db = databaseHelper.getWritableDatabase();
        return Observable.from(items)
                .doOnSubscribe(db::beginTransaction)
                .map(contentValueDataMapper::mapFrom)
                .doOnNext(contentValues -> db.insert(STATION_TABLE_NAME, null, contentValues))
                .doOnError(throwable -> closeTransactionWithoutSuccess(db))
                .doOnCompleted(() -> closeTransactionWithSuccess(db))
                .toCompletable();
    }

    @Override
    public Completable update(StationEntity item, Specification specification) {
        final SQLiteDatabase db = databaseHelper.getWritableDatabase();
        final SqlSpecification sqlSpecification = ((SqlSpecification) specification);
        return Observable.just(item)
                .map(contentValueDataMapper::mapFrom)
                .doOnNext(contentValues
                        -> db.update(STATION_TABLE_NAME,
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
                () -> {
                    db.delete(STATION_TABLE_NAME,
                            sqlSpecification.whereClause(),
                            sqlSpecification.whereArgs());
                    db.close();
                });
    }

    @Override
    public Observable<StationEntity> query(Specification specification) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        SqlSpecification sqlSpecification = ((SqlSpecification) specification);
        return Observable
                .fromCallable(() -> db.rawQuery(sqlSpecification.toString(), sqlSpecification.whereArgs()))
                .map((response) -> cursorDataMapper.mapFrom(response))
                .doOnCompleted(db::close);
    }
}
