package com.deveddy.clujbike.data.repository;

public interface Mapper<V, T> {
    T map(V from);
}
