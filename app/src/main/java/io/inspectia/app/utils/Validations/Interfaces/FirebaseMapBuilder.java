package io.inspectia.app.utils.Validations.Interfaces;

import java.util.function.BiFunction;

public interface FirebaseMapBuilder<T, V> {

    public FirebaseMapBuilder<T,V> init(V initialMap, ValidatorCallback validatorCallback);
    public FirebaseMapBuilder<T,V> add(BiFunction<T, V, V> action, T element);
    public V build();
}
