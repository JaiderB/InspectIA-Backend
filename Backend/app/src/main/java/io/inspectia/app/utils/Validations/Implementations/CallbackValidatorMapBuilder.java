package io.inspectia.app.utils.Validations.Implementations;

import io.inspectia.app.utils.Validations.Interfaces.FirebaseMapBuilder;
import io.inspectia.app.utils.Validations.Interfaces.ValidatorCallback;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

@NoArgsConstructor
@Component("callbackValidatorMapBuilder")
@Scope("prototype")
public class CallbackValidatorMapBuilder <T, V> implements FirebaseMapBuilder<T, V> {

    private ValidatorCallback validatorCallback;

    private V userUpdated;
    private final Map<T, BiFunction<T, V, V>> actions = new HashMap<>();

    @Override
    public CallbackValidatorMapBuilder<T,V> init(V initialMap, ValidatorCallback validatorCallback) {  // Método para inicializar
        this.userUpdated = initialMap;
        this.validatorCallback = validatorCallback;
        return this;
    }

    @Override
    public CallbackValidatorMapBuilder<T,V> add(BiFunction<T, V, V> action, T element) {
        actions.put(element, action);
        return this;
    }

    @Override
    public V build() {
        V temp = userUpdated;
        for (Map.Entry<T, BiFunction<T, V, V>>  entry: actions.entrySet()) {
            temp = validatorCallback.validateAndExecute(entry.getKey(), temp, entry.getValue());  // Aplica secuencialmente en CPU/RAM
        }
        return temp;  // Retorna mapa final
    }
}