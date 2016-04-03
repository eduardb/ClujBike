package com.deveddy.clujbike.data;

import android.app.Application;
import android.support.annotation.VisibleForTesting;

import com.deveddy.clujbike.data.api.ApiModule;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;
import java.lang.reflect.Modifier;
import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

@Module(includes = {ApiModule.class})
public class DataModule {

    private static final int DISK_CACHE_SIZE = 50 * 1024 * 1024; // 50MB
    private static final int TIMEOUT = 15;

    /**
     * Provides a OkHttpClient dependency that has installed a disk cache
     *
     * @param app The application object
     *
     * @return The custom client
     */
    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Application app) {
        final HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .cache(new Cache(app.getCacheDir(), DISK_CACHE_SIZE))
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .build();
    }

    /**
     * Provides a Gson dependency that plays nice with Realm.io, by skipping the fields
     * added by Realm.io to model classes, and converts custom Realm lists
     *
     * @return The custom Gson instance
     */
    @Provides
    @Singleton
    @VisibleForTesting
    public static Gson provideGson() {
        return new GsonBuilder()
                .setDateFormat("dd.MM.yyyy HH:mm") // ex: 03.04.2016 17:27
                .excludeFieldsWithModifiers(Modifier.STATIC)
                .create();
    }
}
