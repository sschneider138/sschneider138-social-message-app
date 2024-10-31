package com.messaging.app.backend.Exceptions;

public class UserNotUpdatedException extends RuntimeException {
  public UserNotUpdatedException(String message) {
    super(message);
  }
}