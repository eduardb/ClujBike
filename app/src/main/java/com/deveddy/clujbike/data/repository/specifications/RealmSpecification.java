package com.deveddy.clujbike.data.repository.specifications;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;

public interface RealmSpecification<T extends RealmObject> extends Specification {
    RealmQuery<T> toRealmQuery(Realm realm);
}
