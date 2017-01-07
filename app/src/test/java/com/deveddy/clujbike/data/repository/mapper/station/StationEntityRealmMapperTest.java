package com.deveddy.clujbike.data.repository.mapper.station;

import com.deveddy.clujbike.data.repository.models.StationEntity;
import com.deveddy.clujbike.data.repository.models.StationRealm;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Date;

import static junit.framework.Assert.assertEquals;

@RunWith(JUnit4.class)
public class StationEntityRealmMapperTest {
    StationEntityRealmMapper sut;

    @Before
    public void setUp() {
        sut = new StationEntityRealmMapper();
    }

    @Test
    public void givenStationEntity_whenMappingToStationRealmSuccessfully_thenAllFieldsAreEqual() {
        StationEntity stationEntity = givenStationEntity("Union Square", 12);
        StationRealm stationRealm;

        stationRealm = sut.from(stationEntity);

        assertEquals(stationEntity.id(), stationRealm.getId());
        assertEquals(stationEntity.name(), stationRealm.getName());
        assertEquals(stationEntity.address(), stationRealm.getAddress());
        assertEquals(stationEntity.occupiedSpots(), stationRealm.getOccupiedSpots());
        assertEquals(stationEntity.emptySpots(), stationRealm.getEmptySpots());
        assertEquals(stationEntity.maximumNumberOfBikes(), stationRealm.getMaximumNumberOfBikes());
        assertEquals(stationEntity.lastSyncDate(), stationRealm.getLastSyncDate());
        assertEquals(stationEntity.idStatus(), stationRealm.getIdStatus());
        assertEquals(stationEntity.statusFunctional(),
                stationRealm.isStatusFunctional());
        assertEquals(stationEntity.statusAvailable(),
                stationRealm.isStatusAvailable());
        assertEquals(stationEntity.latitude(), stationRealm.getLatitude());
        assertEquals(stationEntity.longitude(), stationRealm.getLongitude());
        assertEquals(stationEntity.valid(), stationRealm.isStationValid());
        assertEquals(stationEntity.customValid(), stationRealm.isCustomIsValid());
    }

    @Test(expected = NullPointerException.class)
    public void givenEmptyStationEntity_whenMappingToStationRealmUnsuccessfully_thenErrorIsThrown() {
        sut.from(null);
    }

    private StationEntity givenStationEntity(String name, int id) {
        return StationEntity.builder()
                .id(id)
                .name(name)
                .address("Manhattan")
                .occupiedSpots(2)
                .emptySpots(4)
                .maximumNumberOfBikes(6)
                .lastSyncDate(new Date(System.currentTimeMillis()))
                .idStatus(0)
                .statusFunctional(false)
                .statusAvailable(false)
                .latitude(35.55f)
                .longitude(34.44f)
                .valid(true)
                .customValid(true)
                .build();
    }
}
