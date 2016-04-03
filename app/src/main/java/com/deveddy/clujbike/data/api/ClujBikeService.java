package com.deveddy.clujbike.data.api;

import com.deveddy.clujbike.data.api.model.StationReadResponse;

import retrofit2.http.GET;
import rx.Observable;

public interface ClujBikeService {

    @GET("Station/Read")
    Observable<StationReadResponse> getStations();
}
