package com.deveddy.clujbike.data.repository.specifications;

public interface SqlSpecification extends Specification {

    String whereClause();

    String[] whereArgs();
}
