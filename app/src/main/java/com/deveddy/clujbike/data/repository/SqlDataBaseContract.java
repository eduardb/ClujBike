package com.deveddy.clujbike.data.repository;

import android.provider.BaseColumns;

public interface SqlDatabaseContract {

    int DATA_BASE_VERSION = 1;
    String DATA_BASE_NAME = "clujbike.db";

    String TEXT_TYPE = " TEXT";
    String INTEGER_TYPE = " INTEGER";
    String REAL_TYPE = " REAL";
    String BOOL_TYPE = " INTEGER DEFAULT 0";
    String COMMA = ",";

    interface Station extends BaseColumns {

        String TABLE_NAME = "stations";
        String COLUMN_NAME = "name";
        String COLUMN_ADDRESS = "address";
        String COLUMN_OCCUPIED_SPOTS = "occupiedSpots";
        String COLUMN_EMPTY_SPOTS = "emptySpots";
        String COLUMN_MAXIMUM_NO_BIKES = "maximumNumberOfBikes";
        String COLUMN_LAST_SYNC_DATE = "lastSyncDate";
        String COLUMN_ID_STATUS = "idStatus";
        String COLUMN_STATUS = "status";
        String COLUMN_STATUS_TYPE = "statusType";
        String COLUMN_LATITUDE = "latitude";
        String COLUMN_LONGITUDE = "longitude";
        String COLUMN_IS_VALID = "isValid";
        String COLUMN_CUSTOM_IS_VALID = "customIsValid";

        String CREATE_TABLE = "CREATE_TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY" + COMMA +
                COLUMN_NAME + TEXT_TYPE + COMMA +
                COLUMN_ADDRESS + TEXT_TYPE + COMMA +
                COLUMN_OCCUPIED_SPOTS + INTEGER_TYPE + COMMA +
                COLUMN_EMPTY_SPOTS + INTEGER_TYPE + COMMA +
                COLUMN_MAXIMUM_NO_BIKES + INTEGER_TYPE + COMMA +
                COLUMN_LAST_SYNC_DATE + " DATETIME" + COMMA +
                COLUMN_ID_STATUS + INTEGER_TYPE + COMMA +
                COLUMN_STATUS + BOOL_TYPE + COMMA +
                COLUMN_STATUS_TYPE + BOOL_TYPE + COMMA +
                COLUMN_LATITUDE + REAL_TYPE + COMMA +
                COLUMN_LONGITUDE + REAL_TYPE + COMMA +
                COLUMN_IS_VALID + BOOL_TYPE + COMMA +
                COLUMN_CUSTOM_IS_VALID + BOOL_TYPE + " )";
    }
}
