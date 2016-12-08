package com.deveddy.clujbike.data.repository.mapper;

public interface Mapper<F, T> {
    T from(F from);
}
