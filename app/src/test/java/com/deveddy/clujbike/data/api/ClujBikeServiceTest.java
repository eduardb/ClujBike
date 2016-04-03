package com.deveddy.clujbike.data.api;

import com.deveddy.clujbike.data.DataModule;
import com.deveddy.clujbike.data.api.model.Station;
import com.deveddy.clujbike.data.api.model.StationReadResponse;

import java.util.GregorianCalendar;

import org.junit.Test;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.observers.TestSubscriber;

import static org.junit.Assert.assertEquals;

public class ClujBikeServiceTest extends ApiSeviceTest {

    @Test
    public void shouldParseGetStationResponse() throws Exception {
        ClujBikeService clujBikeService = givenClujBikeService();
        enqueueMockResponse("StationRead.json");

        final Observable<StationReadResponse> stations = clujBikeService.getStations();
        TestSubscriber<StationReadResponse> testSubscriber = new TestSubscriber<>();
        stations.subscribe(testSubscriber);
        testSubscriber.assertValueCount(1);

        final StationReadResponse stationReadResponse = testSubscriber.getOnNextEvents().get(0);
        assertEquals(52, stationReadResponse.total);
        assertEquals(52, stationReadResponse.data.size());

        final Station station = stationReadResponse.data.get(0);
        assertEquals("Biblioteca Centrala", station.name);
        assertEquals("Biblioteca Județeană Octavian Goga", station.address);
        assertEquals(2, station.ocuppiedSpots);
        assertEquals(20, station.emptySpots);
        assertEquals(22, station.maximumNumberOfBikes);
        assertEquals(new GregorianCalendar(2016, 3, 3, 19, 5, 0).getTime(), station.lastSyncDate);
        assertEquals(1, station.idStatus);
        assertEquals(Station.Status.FUNCTIONAL, station.status);
        assertEquals(Station.StatusType.ONLINE, station.statusType);
        assertEquals(46.777037f, station.latitude, 0.00001f);
        assertEquals(23.615109f, station.longitude, 0.00001f);
        assertEquals(true, station.isValid);
        assertEquals(false, station.customIsValid);
        assertEquals(85, station.id);
    }

    private ClujBikeService givenClujBikeService() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(getBaseEndpoint())
                .addConverterFactory(GsonConverterFactory.create(DataModule.provideGson()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        return retrofit.create(ClujBikeService.class);
    }
}
