package io.inspectia.app.exceptions.AuthExceptions;

public class RecordAlreadyExistsException extends RuntimeException{
    public RecordAlreadyExistsException() {
        super("The record trying to save already exists.");
    }

    public RecordAlreadyExistsException(String mensaje) {
        super(mensaje);
    }

    public RecordAlreadyExistsException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
