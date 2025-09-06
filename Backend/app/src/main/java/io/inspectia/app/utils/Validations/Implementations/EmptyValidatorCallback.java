package io.inspectia.app.utils.Validations.Implementations;

import io.inspectia.app.utils.Validations.Interfaces.ValidatorCallback;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

@Component("emptyValidatorCallback")
public class EmptyValidatorCallback implements ValidatorCallback {

    @Override
    public <T, V> V validateAndExecute(T element, V object, BiFunction<T, V, V> action) {
        if (element != null && !element.toString().isEmpty()) {  // Validación hardcoded de no vacío

            return action.apply(element, object);                       // Ejecución de lambda en RAM
        }

        return object;
    }
}
