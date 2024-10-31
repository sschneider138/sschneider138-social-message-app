package com.messaging.app.backend.Exceptions;

public class UserNotCreatedException extends RuntimeException {
  public UserNotCreatedException(String message) {
    super(message);
  }
}