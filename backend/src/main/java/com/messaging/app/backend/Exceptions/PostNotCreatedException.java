package com.messaging.app.backend.Exceptions;

public class PostNotCreatedException extends RuntimeException {
    public PostNotCreatedException(String message) {
        super(message);
    }
}