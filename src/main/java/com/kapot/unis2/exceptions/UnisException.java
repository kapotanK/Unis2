package com.kapot.unis2.exceptions;

public abstract class UnisException extends RuntimeException {
    public UnisException() {
        super();
    }

    public UnisException(String message) {
        super(message);
    }
}
