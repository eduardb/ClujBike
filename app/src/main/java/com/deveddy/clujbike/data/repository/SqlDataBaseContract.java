package com.deveddy.clujbike.data.repository;

import android.provider.BaseColumns;

public final class SqlDataBaseContract {

    public static final int DATA_BASE_VERSION = 1;
    public static final String DATA_BASE_NAME = "clujbike.db";

    public static final String TEXT_TYPE = "TEXT";
    public static final String INTEGER_TYPE = "INTEGER";
    public static final String REAL_TYPE = "REAL";
    public static final String BOOL_TYPE = "INTEGER DEFAULT 0";
    public static final String COMMA = ",";

    private SqlDataBaseContract(){}

    public static abstract class Station implements BaseColumns {

        public static final String TABLE_NAME = "stations";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_ADDRESS = "address";
        public static final String COLUMN_OCCUPIED_SPOTS = "occupiedSpots";
        public static final String COLUMN_EMPTY_SPOTS = "emptySpots";
        public static final String COLUMN_MAXIMUM_NO_BIKES = "maximumNumberOfBikes";
        public static final String COLUMN_LAST_SYNC_DATE = "lastSyncDate";
        public static final String COLUMN_ID_STATUS = "idStatus";
        public static final String COLUMN_STATUS = "status";
        public static final String COLUMN_STATUS_TYPE = "statusType";
        public static final String COLUMN_LATITUDE = "latitude";
        public static final String COLUMN_LONGITUDE = "";
        public static final String COLUMN_IS_VALID = "";
        public static final String COLUMN_CUSTOM_IS_VALID = "customIsValid";

        public static final String CREATE_TABLE = "CREATE_TABLE " + TABLE_NAME + "(" +
                _ID + " INTEGER PRIMARY KEY" + COMMA +
                COLUMN_NAME + TEXT_TYPE + COMMA +
                COLUMN_ADDRESS + TEXT_TYPE + COMMA +
                COLUMN_OCCUPIED_SPOTS + INTEGER_TYPE + COMMA +
                COLUMN_EMPTY_SPOTS + INTEGER_TYPE + COMMA +
                COLUMN_MAXIMUM_NO_BIKES + INTEGER_TYPE + COMMA +
                COLUMN_LAST_SYNC_DATE + " DATETIME" +  COMMA +
                COLUMN_ID_STATUS + INTEGER_TYPE + COMMA +
                COLUMN_STATUS + BOOL_TYPE + COMMA +
                COLUMN_STATUS_TYPE + BOOL_TYPE + COMMA +
                COLUMN_LATITUDE + REAL_TYPE + COMMA +
                COLUMN_LONGITUDE + REAL_TYPE + COMMA +
                COLUMN_IS_VALID + BOOL_TYPE + COMMA +
                COLUMN_CUSTOM_IS_VALID + BOOL_TYPE + ")";

        public static final String DROP_TABLE = "DROP TABLE IF EXISTS" + TABLE_NAME;

        private Station() {}

    }
}
