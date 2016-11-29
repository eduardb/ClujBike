package com.deveddy.clujbike.data.repository.mapper.station;

import android.content.ContentValues;

import com.deveddy.clujbike.data.repository.SqlDataBaseContract.Station;
import com.deveddy.clujbike.data.repository.models.StationEntity;
import com.deveddy.clujbike.data.repository.mapper.DataMapper;

public class StationContentValueDataMapper implements DataMapper<StationEntity, ContentValues> {

    @Override
    public ContentValues mapFrom(StationEntity station) {
        ContentValues cv = new ContentValues();
        cv.put(Station.COLUMN_NAME, station.name());
        cv.put(Station.COLUMN_ADDRESS, station.address());
        cv.put(Station.COLUMN_OCCUPIED_SPOTS, station.occupiedSpots());
        cv.put(Station.COLUMN_EMPTY_SPOTS, station.emptySpots());
        cv.put(Station.COLUMN_MAXIMUM_NO_BIKES, station.maximumNumberOfBikes());
        cv.put(Station.COLUMN_LAST_SYNC_DATE, station.lastSyncDate().getTime());
        cv.put(Station.COLUMN_ID_STATUS, station.idStatus());
        cv.put(Station.COLUMN_STATUS, station.statusFunctional());
        cv.put(Station.COLUMN_STATUS_TYPE, station.statusAvailable());
        cv.put(Station.COLUMN_LATITUDE, station.latitude());
        cv.put(Station.COLUMN_LONGITUDE, station.longitude());
        cv.put(Station.COLUMN_IS_VALID, station.valid());
        cv.put(Station.COLUMN_CUSTOM_IS_VALID, station.customValid());
        return cv;
    }
}
