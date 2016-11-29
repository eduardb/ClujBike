package com.deveddy.clujbike.data.repository.mapper;

public interface DataMapper<T, V> {
    V mapFrom(T response);
}
