package com.deveddy.clujbike.data.repository.specifications;

import com.deveddy.clujbike.data.repository.models.StationRealm;

import io.realm.Realm;
import io.realm.RealmQuery;

public interface RealmSpecification extends Specification {
    RealmQuery<StationRealm> toRealmQuery(Realm realm);

}
