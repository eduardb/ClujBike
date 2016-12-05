package com.deveddy.clujbike.data.api;

import com.deveddy.clujbike.data.api.model.StationReadResponse;

import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface ClujBikeService {

    @FormUrlEncoded
    @POST("Station/Read")
    Observable<StationReadResponse> getStations();
}
