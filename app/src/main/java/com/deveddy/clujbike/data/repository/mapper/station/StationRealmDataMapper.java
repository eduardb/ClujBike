package com.deveddy.clujbike.data.repository.mapper.station;

import com.deveddy.clujbike.data.api.model.StationApiEntity;
import com.deveddy.clujbike.data.repository.mapper.DataMapper;
import com.deveddy.clujbike.data.repository.models.StationRealm;

public class StationRealmDataMapper implements DataMapper<StationApiEntity, StationRealm> {
    @Override
    public StationRealm mapFrom(StationApiEntity station) {
        return new StationRealm(station.name,
                station.address,
                station.occupiedSpots,
                station.emptySpots,
                station.maximumNumberOfBikes,
                station.lastSyncDate,
                station.idStatus,
                station.status.toString(),
                station.statusType.toString(),
                station.latitude,
                station.longitude,
                station.isValid,
                station.customIsValid);
    }
}
