package com.messaging.app.backend.exceptions;

public class PostNotCreatedException extends RuntimeException {
    public PostNotCreatedException(String message) {
        super(message);
    }
}