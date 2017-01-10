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
public class StationRealmEntityMapperTest {

    StationRealmEntityMapper sut;

    @Before
    public void setUp() {
        sut = new StationRealmEntityMapper();
    }

    @Test
    public void givenStationRealm_whenMappingToStationEntitySuccessfully_thenAllFieldsAreEqual() {
        StationRealm stationRealm = givenStationRealm();
        StationEntity stationEntity;

        stationEntity = sut.from(stationRealm);

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
    public void givenEmptyStationRealm_whenMappingToStationRealmUnsuccessfully_thenErrorIsThrown() {
        sut.from(null);
    }

    private StationRealm givenStationRealm() {
        return new StationRealm(12, "Union Square", "Manhattan", 2, 4,
                6, new Date(System.currentTimeMillis()), 0, false,
                false, 35.55f, 34.44f, true, true);
    }

}