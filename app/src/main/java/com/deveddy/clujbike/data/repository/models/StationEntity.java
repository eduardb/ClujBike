package com.deveddy.clujbike.data.repository.models;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;

import java.util.Date;

@AutoValue
public abstract class StationEntity {

    public abstract String name();

    public abstract String address();

    public abstract int occupiedSpots();

    public abstract int emptySpots();

    public abstract int maximumNumberOfBikes();

    public abstract Date lastSyncDate();

    public abstract int idStatus();

    public abstract boolean statusFunctional();

    public abstract boolean statusAvailable();

    public abstract float latitude();

    public abstract float longitude();

    public abstract boolean valid();

    public abstract boolean customValid();

    public abstract int id();

    public static StationEntity.Builder builder() {
        return new AutoValue_StationEntity.Builder();
    }

    @AutoValue.Builder
    public interface Builder {

        Builder name(String value);

        Builder address(String value);

        Builder occupiedSpots(int value);

        Builder emptySpots(int value);

        Builder maximumNumberOfBikes(int value);

        Builder lastSyncDate(Date value);

        Builder idStatus(int value);

        Builder statusFunctional(boolean value);

        Builder statusAvailable(boolean value);

        Builder latitude(float value);

        Builder longitude(float value);

        Builder valid(boolean valid);

        Builder customValid(boolean value);

        Builder id(int value);

        StationEntity build();
    }

}
