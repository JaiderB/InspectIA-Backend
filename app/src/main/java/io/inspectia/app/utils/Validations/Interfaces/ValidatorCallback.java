package io.inspectia.app.utils.Validations.Interfaces;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface ValidatorCallback{

    public <T, V> V validateAndExecute(T element, V object, BiFunction<T, V, V> action);
}

