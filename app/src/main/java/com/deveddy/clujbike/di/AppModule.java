package com.deveddy.clujbike.di;

import com.deveddy.clujbike.ClujBikeApp;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private final ClujBikeApp mClujBikeApp;

    public AppModule(ClujBikeApp clujBikeApp) {
        mClujBikeApp = clujBikeApp;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return mClujBikeApp;
    }
}
