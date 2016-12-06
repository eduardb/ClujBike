package com.deveddy.clujbike.data.repository.mapper.station;

import android.database.Cursor;
import android.database.MatrixCursor;
import android.support.test.runner.AndroidJUnit4;

import com.deveddy.clujbike.data.repository.models.StationEntity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import static junit.framework.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class StationCursorMapperTest {
    StationCursorMapper sut;

    @Before
    public void setUp() {
        sut = new StationCursorMapper();
    }

    @Test
    public void givenCursor_whenMappingToStationEntitySuccessfully_thenFieldsAreEqual() {
        Cursor cursor = givenCursor();
        StationEntity station;

        station = sut.from(cursor);

        assertEquals(station.id(), cursor.getInt(getIndex(cursor, "_id")));
        assertEquals(station.name(), cursor.getString(getIndex(cursor, "name")));
        assertEquals(station.address(), cursor.getString(getIndex(cursor, "address")));
        assertEquals(station.occupiedSpots(), cursor.getInt(getIndex(cursor, "occupiedSpots")));
        assertEquals(station.emptySpots(), cursor.getInt(getIndex(cursor, "emptySpots")));
        assertEquals(station.maximumNumberOfBikes(), cursor.getInt(getIndex(cursor, "maximumNumberOfBikes")));
        assertEquals(station.lastSyncDate(), new Date(cursor.getLong(getIndex(cursor, "lastSyncDate"))));
        assertEquals(station.idStatus(), cursor.getInt(getIndex(cursor, "idStatus")));
        assertEquals(station.statusFunctional(), getBooleanValue(cursor, "status"));
        assertEquals(station.statusAvailable(), getBooleanValue(cursor, "statusType"));
        assertEquals(station.latitude(), cursor.getFloat(getIndex(cursor, "latitude")));
        assertEquals(station.longitude(), cursor.getFloat(getIndex(cursor, "longitude")));
        assertEquals(station.valid(), getBooleanValue(cursor, "isValid"));
        assertEquals(station.customValid(), getBooleanValue(cursor, "customIsValid"));
    }

    private Cursor givenCursor() {
        String[] columns = new String[]{"_id", "name", "address", "occupiedSpots",
                "emptySpots", "maximumNumberOfBikes", "lastSyncDate", "idStatus", "status",
                "statusType", "latitude", "longitude", "isValid", "customIsValid"};
        MatrixCursor cursor = new MatrixCursor(columns);
        cursor.addRow(new Object[]{12, "Union Square", "Manhattan", 2, 4, 6,
                System.currentTimeMillis(), 0, 0, 1, 35.55f, 34.44f, 0, 1});
        cursor.moveToFirst();
        return cursor;
    }

    private int getIndex(Cursor cursor, String key) {
        return cursor.getColumnIndex(key);
    }

    private boolean getBooleanValue(Cursor cursor, String key) {
        return cursor.getInt(getIndex(cursor, key)) > 0;
    }
}