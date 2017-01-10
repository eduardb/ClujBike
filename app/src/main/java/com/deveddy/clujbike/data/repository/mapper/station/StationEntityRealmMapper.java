package com.deveddy.clujbike.data.repository.mapper.station;

import com.deveddy.clujbike.data.repository.mapper.Mapper;
import com.deveddy.clujbike.data.repository.models.StationEntity;
import com.deveddy.clujbike.data.repository.models.StationRealm;

public class StationEntityRealmMapper implements Mapper<StationEntity, StationRealm> {
    @Override
    public StationRealm from(StationEntity station) {
        return new StationRealm(
                station.id(),
                station.name(),
                station.address(),
                station.occupiedSpots(),
                station.emptySpots(),
                station.maximumNumberOfBikes(),
                station.lastSyncDate(),
                station.idStatus(),
                station.statusFunctional(),
                station.statusAvailable(),
                station.latitude(),
                station.longitude(),
                station.valid(),
                station.customValid());
    }
}
