package com.deveddy.clujbike.data.repository.mapper.station;

import android.database.Cursor;

import com.deveddy.clujbike.data.repository.mapper.Mapper;
import com.deveddy.clujbike.data.repository.models.StationEntity;

import java.util.Date;

import static android.provider.BaseColumns._ID;
import static com.deveddy.clujbike.data.repository.SqlDatabaseContract.Station.COLUMN_ADDRESS;
import static com.deveddy.clujbike.data.repository.SqlDatabaseContract.Station.COLUMN_CUSTOM_IS_VALID;
import static com.deveddy.clujbike.data.repository.SqlDatabaseContract.Station.COLUMN_EMPTY_SPOTS;
import static com.deveddy.clujbike.data.repository.SqlDatabaseContract.Station.COLUMN_ID_STATUS;
import static com.deveddy.clujbike.data.repository.SqlDatabaseContract.Station.COLUMN_IS_VALID;
import static com.deveddy.clujbike.data.repository.SqlDatabaseContract.Station.COLUMN_LAST_SYNC_DATE;
import static com.deveddy.clujbike.data.repository.SqlDatabaseContract.Station.COLUMN_LATITUDE;
import static com.deveddy.clujbike.data.repository.SqlDatabaseContract.Station.COLUMN_LONGITUDE;
import static com.deveddy.clujbike.data.repository.SqlDatabaseContract.Station.COLUMN_MAXIMUM_NO_BIKES;
import static com.deveddy.clujbike.data.repository.SqlDatabaseContract.Station.COLUMN_NAME;
import static com.deveddy.clujbike.data.repository.SqlDatabaseContract.Station.COLUMN_OCCUPIED_SPOTS;
import static com.deveddy.clujbike.data.repository.SqlDatabaseContract.Station.COLUMN_STATUS;
import static com.deveddy.clujbike.data.repository.SqlDatabaseContract.Station.COLUMN_STATUS_TYPE;

public class StationCursorMapper implements Mapper<Cursor, StationEntity> {

    private Cursor cursor;

    @Override
    public StationEntity from(Cursor cursor) {
        this.cursor = cursor;
        return StationEntity.builder()
                .id(getIntValue(_ID))
                .name(getStringValue(COLUMN_NAME))
                .address(getStringValue(COLUMN_ADDRESS))
                .occupiedSpots(getIntValue(COLUMN_OCCUPIED_SPOTS))
                .emptySpots(getIntValue(COLUMN_EMPTY_SPOTS))
                .maximumNumberOfBikes(getIntValue(COLUMN_MAXIMUM_NO_BIKES))
                .lastSyncDate(getDateValue(COLUMN_LAST_SYNC_DATE))
                .idStatus(getIntValue(COLUMN_ID_STATUS))
                .statusFunctional(getBooleanValue(COLUMN_STATUS))
                .statusAvailable(getBooleanValue(COLUMN_STATUS_TYPE))
                .latitude(getFloatValue(COLUMN_LATITUDE))
                .longitude(getFloatValue(COLUMN_LONGITUDE))
                .valid(getBooleanValue(COLUMN_IS_VALID))
                .customValid(getBooleanValue(COLUMN_CUSTOM_IS_VALID))
                .build();
    }

    private String getStringValue(String key) {
        return cursor.getString(cursor.getColumnIndex(key));
    }

    private int getIntValue(String key) {
        return cursor.getInt(cursor.getColumnIndex(key));
    }

    private Date getDateValue(String key) {
        return new Date(cursor.getLong(cursor.getColumnIndex(key)));
    }

    private boolean getBooleanValue(String key) {
        return getIntValue(key) > 0;
    }

    private float getFloatValue(String key) {
        return cursor.getFloat(cursor.getColumnIndex(key));
    }
}
