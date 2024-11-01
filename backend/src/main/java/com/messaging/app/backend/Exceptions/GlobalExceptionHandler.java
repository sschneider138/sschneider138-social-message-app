package com.messaging.app.backend.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleUserNotFound(UserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDto("user could not be found"));
    }

    @ExceptionHandler(UserNotCreatedException.class)
    public ResponseEntity<ErrorResponseDto> handleUserNotCreated(UserNotCreatedException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDto("user could not be created"));
    }

    @ExceptionHandler(UserNotUpdatedException.class)
    public ResponseEntity<ErrorResponseDto> handleUserNotUpdated(UserNotUpdatedException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDto("user could not be updated"));
    }

    @ExceptionHandler(UserNotUpdatedException.class)
    public ResponseEntity<ErrorResponseDto> handlePostNotCreated(PostNotCreatedException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDto("post could not be created"));
    }

    @ExceptionHandler(UserNotUpdatedException.class)
    public ResponseEntity<ErrorResponseDto> handlePostNotFound(PostNotFoundException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDto("post could not be found"));
    }

}
