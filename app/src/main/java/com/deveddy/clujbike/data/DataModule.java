package com.deveddy.clujbike.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.deveddy.clujbike.data.api.ApiModule;

import android.app.Application;

import java.lang.reflect.Modifier;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

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
    Gson provideGson() {
        return new GsonBuilder()
//                .setDateFormat("yyyy-MM-dd HH:mm:ss") // TODO
                .excludeFieldsWithModifiers(Modifier.STATIC)
                .create();
    }
}
