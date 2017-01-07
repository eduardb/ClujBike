package com.deveddy.clujbike.data.repository;

import io.realm.Realm;
import io.realm.RealmConfiguration;

class RealmProvider {

    private final RealmConfiguration realmConfiguration;

    RealmProvider(RealmConfiguration realmConfiguration) {
        this.realmConfiguration = realmConfiguration;
    }

    Realm provide() {
        return Realm.getInstance(realmConfiguration);
    }

}
