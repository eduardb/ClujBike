package com.deveddy.clujbike.di;

import com.deveddy.clujbike.ClujBikeApp;
import com.deveddy.clujbike.data.VariantDataModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, VariantDataModule.class})
public interface AppComponent {

    void inject(ClujBikeApp clujBikeApp);
}
