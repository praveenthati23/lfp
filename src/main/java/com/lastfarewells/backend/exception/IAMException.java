package com.lastfarewells.backend.exception;

public class IAMException extends RuntimeException{
    public IAMException() {
        super();
    }

    public IAMException(String message) {
        super(message);
    }
}
