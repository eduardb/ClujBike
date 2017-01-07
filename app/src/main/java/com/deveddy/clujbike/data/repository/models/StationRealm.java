package com.deveddy.clujbike.data.repository.models;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class StationRealm extends RealmObject {
    @PrimaryKey
    private int id;

    private String name;
    private String address;
    private int occupiedSpots;
    private int emptySpots;
    private int maximumNumberOfBikes;
    private Date lastSyncDate;
    private int idStatus;
    private boolean statusFunctional;
    private boolean statusAvailable;
    private float latitude;
    private float longitude;
    private boolean isValid;
    private boolean customIsValid;

    public StationRealm() {
    }

    public StationRealm(int id, String name, String address, int occupiedSpots, int emptySpots,
                        int maximumNumberOfBikes, Date lastSyncDate, int idStatus,
                        boolean statusFunctional, boolean statusAvailable, float latitude,
                        float longitude, boolean isValid, boolean customIsValid) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.occupiedSpots = occupiedSpots;
        this.emptySpots = emptySpots;
        this.maximumNumberOfBikes = maximumNumberOfBikes;
        this.lastSyncDate = lastSyncDate;
        this.idStatus = idStatus;
        this.statusFunctional = statusFunctional;
        this.statusAvailable = statusAvailable;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isValid = isValid;
        this.customIsValid = customIsValid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getOccupiedSpots() {
        return occupiedSpots;
    }

    public void setOccupiedSpots(int occupiedSpots) {
        this.occupiedSpots = occupiedSpots;
    }

    public int getEmptySpots() {
        return emptySpots;
    }

    public void setEmptySpots(int emptySpots) {
        this.emptySpots = emptySpots;
    }

    public int getMaximumNumberOfBikes() {
        return maximumNumberOfBikes;
    }

    public void setMaximumNumberOfBikes(int maximumNumberOfBikes) {
        this.maximumNumberOfBikes = maximumNumberOfBikes;
    }

    public Date getLastSyncDate() {
        return lastSyncDate;
    }

    public void setLastSyncDate(Date lastSyncDate) {
        this.lastSyncDate = lastSyncDate;
    }

    public int getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(int idStatus) {
        this.idStatus = idStatus;
    }

    public boolean isStatusFunctional() {
        return statusFunctional;
    }

    public void setStatusFunctional(boolean statusFunctional) {
        this.statusFunctional = statusFunctional;
    }

    public boolean isStatusAvailable() {
        return statusAvailable;
    }

    public void setStatusAvailable(boolean statusAvailable) {
        this.statusAvailable = statusAvailable;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public boolean isStationValid() {
        return isValid;
    }

    public void setValid(boolean isValid) {
        isValid = isValid;
    }

    public boolean isCustomIsValid() {
        return customIsValid;
    }

    public void setCustomIsValid(boolean customIsValid) {
        this.customIsValid = customIsValid;
    }

}
