package com.deveddy.clujbike.data.repository.mapper.station;

import com.deveddy.clujbike.data.repository.mapper.Mapper;
import com.deveddy.clujbike.data.repository.models.StationEntity;
import com.deveddy.clujbike.data.repository.models.StationRealm;

public class StationRealmEntityMapper implements Mapper<StationRealm, StationEntity> {
    @Override
    public StationEntity from(StationRealm from) {
        return StationEntity.builder()
                .id(from.getId())
                .name(from.getName())
                .address(from.getAddress())
                .occupiedSpots(from.getOccupiedSpots())
                .emptySpots(from.getEmptySpots())
                .maximumNumberOfBikes(from.getMaximumNumberOfBikes())
                .lastSyncDate(from.getLastSyncDate())
                .idStatus(from.getIdStatus())
                .statusFunctional(from.isStatusFunctional())
                .statusAvailable(from.isStatusAvailable())
                .latitude(from.getLatitude())
                .longitude(from.getLongitude())
                .valid(from.isStationValid())
                .customValid(from.isCustomIsValid())
                .build();
    }
}
