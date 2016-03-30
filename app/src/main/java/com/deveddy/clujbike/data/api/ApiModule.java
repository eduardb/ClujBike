package com.deveddy.clujbike.data.api;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

@Module
public class ApiModule {

    private static final String API_ENDPOINT = "http://portal.clujbike.eu/";

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient httpClient, Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(API_ENDPOINT)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build();
    }

    @Provides
    @Singleton
    ClujBikeService provideClujBikeService(Retrofit retrofit) {
        return retrofit.create(ClujBikeService.class);
    }
}
