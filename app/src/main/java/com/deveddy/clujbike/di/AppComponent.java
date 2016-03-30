package com.deveddy.clujbike.di;

import com.deveddy.clujbike.ClujBikeApp;
import com.deveddy.clujbike.data.VariantDataModule;

import dagger.Component;

@Component(modules = {AppModule.class, VariantDataModule.class})
public interface AppComponent {

    void inject(ClujBikeApp clujBikeApp);
}
