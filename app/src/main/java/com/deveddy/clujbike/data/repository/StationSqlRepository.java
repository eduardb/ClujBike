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

    static final String TABLE = Station.TABLE_NAME;

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
                .doOnNext(contentValues -> db.insert(TABLE, null, contentValues))
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
                .doOnNext(contentValues -> db.update(TABLE,
                        contentValues,
                        sqlSpecification.whereClause(),
                        sqlSpecification.whereArgs()))
                .doOnError((throwable) -> db.close())
                .doOnCompleted(db::close)
                .toCompletable();
    }

    @Override
    public Completable remove(Specification specification) {
        final SQLiteDatabase db = databaseHelper.getWritableDatabase();
        final SqlSpecification sqlSpecification = ((SqlSpecification) specification);
        return Completable.fromAction(
                () -> db.delete(TABLE,
                        sqlSpecification.whereClause(),
                        sqlSpecification.whereArgs()))
                .doOnError((throwable) -> db.close())
                .doOnCompleted(db::close);
    }

    @Override
    public Observable<StationEntity> query(Specification specification) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        SqlSpecification sqlSpecification = ((SqlSpecification) specification);
        return Observable
                .fromCallable(() -> db.query(TABLE,
                        null,
                        sqlSpecification.whereClause(),
                        sqlSpecification.whereArgs(),
                        null, null, null))
                .map(cursorDataMapper::from)
                .doOnError((throwable) -> db.close())
                .doOnCompleted(db::close);
    }
}
