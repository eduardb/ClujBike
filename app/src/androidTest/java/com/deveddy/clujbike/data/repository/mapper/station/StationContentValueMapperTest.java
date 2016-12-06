package com.deveddy.clujbike.data.repository.mapper.station;

import android.content.ContentValues;
import android.support.test.runner.AndroidJUnit4;

import com.deveddy.clujbike.data.repository.models.StationEntity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import static junit.framework.Assert.assertEquals;


@RunWith(AndroidJUnit4.class)
public class StationContentValueMapperTest {
    StationContentValueMapper sut;

    @Before
    public void setUp() {
        sut = new StationContentValueMapper();
    }

    @Test
    public void givenItem_whenMappingStationEntityToContentValuesSuccessfully_thenFieldsAreEquals() {
        StationEntity station = givenStationEntity("Union Square", 12);
        ContentValues cv;

        cv = sut.from(station);

        assertEquals(cv.getAsInteger("_id").intValue(), station.id());
        assertEquals(cv.getAsString("name"), station.name());
        assertEquals(cv.getAsString("address"), station.address());
        assertEquals(cv.getAsInteger("occupiedSpots").intValue(), station.occupiedSpots());
        assertEquals(cv.getAsInteger("emptySpots").intValue(), station.emptySpots());
        assertEquals(cv.getAsInteger("maximumNumberOfBikes").intValue(), station.maximumNumberOfBikes());
        assertEquals(cv.getAsLong("lastSyncDate").longValue(), station.lastSyncDate().getTime());
        assertEquals(cv.getAsInteger("idStatus").intValue(), station.idStatus());
        assertEquals(cv.getAsBoolean("status").booleanValue(), station.statusFunctional());
        assertEquals(cv.getAsBoolean("statusType").booleanValue(), station.statusAvailable());
        assertEquals(cv.getAsFloat("latitude"), station.latitude());
        assertEquals(cv.getAsFloat("longitude"), station.longitude());
        assertEquals(cv.getAsBoolean("isValid").booleanValue(), station.valid());
        assertEquals(cv.getAsBoolean("customIsValid").booleanValue(), station.customValid());
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
                .latitude(35.55f)
                .longitude(34.44f)
                .valid(true)
                .customValid(true)
                .id(id)
                .build();
    }
}