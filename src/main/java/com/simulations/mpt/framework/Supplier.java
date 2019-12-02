package com.simulations.mpt.framework;


public interface Supplier<T> {
    void initialize();
    void close();
    int size();
    T get();
}
