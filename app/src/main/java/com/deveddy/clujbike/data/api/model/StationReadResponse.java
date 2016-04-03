package com.deveddy.clujbike.data.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StationReadResponse {

    @SerializedName("Data")
    public List<Station> data;

    @SerializedName("Total")
    public int total;
}
