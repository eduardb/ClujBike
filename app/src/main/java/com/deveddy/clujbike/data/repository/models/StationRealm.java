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
    private String status;
    private String statusType;
    private float latitude;
    private float longitude;
    private boolean isValid;
    private boolean customIsValid;

    private enum Status {
        DEACTIVATED, FUNCTIONAL
    }

    private enum StatusType {
        ONLINE, OFFLINE
    }

    public StationRealm() {
    }

    public StationRealm(String name, String address, int occupiedSpots, int emptySpots,
                        int maximumNumberOfBikes, Date lastSyncDate, int idStatus, String status,
                        String statusType, float latitude, float longitude, boolean isValid, boolean customIsValid) {
        this.name = name;
        this.address = address;
        this.occupiedSpots = occupiedSpots;
        this.emptySpots = emptySpots;
        this.maximumNumberOfBikes = maximumNumberOfBikes;
        this.lastSyncDate = lastSyncDate;
        this.idStatus = idStatus;
        this.status = status;
        this.statusType = statusType;
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

    public Status getStatus() {
        return Status.valueOf(status);
    }

    public void setStatus(Status status) {
        this.status = status.toString();
    }

    public StatusType getStatusType() {
        return StatusType.valueOf(statusType);
    }

    public void setStatusType(StatusType statusType) {
        this.statusType = statusType.toString();
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

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public boolean isCustomIsValid() {
        return customIsValid;
    }

    public void setCustomIsValid(boolean customIsValid) {
        this.customIsValid = customIsValid;
    }

}
