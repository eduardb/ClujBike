package com.deveddy.clujbike.data.api;

import retrofit2.http.GET;
import rx.Observable;

public interface ClujBikeService {

    @GET("Station/Read")
    Observable getStations();
}
