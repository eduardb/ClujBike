package com.deveddy.clujbike.data.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class StationApiEntity {

    @SerializedName("StationName")
    public String name;

    @SerializedName("Address")
    public String address;

    @SerializedName("OccupiedSpots")
    public int occupiedSpots;

    @SerializedName("EmptySpots")
    public int emptySpots;

    @SerializedName("MaximumNumberOfBikes")
    public int maximumNumberOfBikes;

    @SerializedName("LastSyncDate")
    public Date lastSyncDate;

    @SerializedName("IdStatus")
    public int idStatus;

    @SerializedName("Status")
    public Status status;

    @SerializedName("StatusType")
    public StatusType statusType;

    @SerializedName("Latitude")
    public float latitude;

    @SerializedName("Longitude")
    public float longitude;

    @SerializedName("IsValid")
    public boolean isValid;

    @SerializedName("CustomIsValid")
    public boolean customIsValid;

    @SerializedName("Id")
    public int id;

    public enum Status {
        @SerializedName("Dezactivata")
        DEACTIVATED,
        @SerializedName("Functionala")
        FUNCTIONAL
    }

    public enum StatusType {
        @SerializedName("Online")
        ONLINE,
        @SerializedName("Offline")
        OFFLINE
    }
}
