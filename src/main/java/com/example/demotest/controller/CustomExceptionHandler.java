package com.example.demotest.controller;

import com.example.demotest.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(RequestAlreadyHandledException.class)
    public final ResponseEntity<String> handleRequestAlreadyHandledException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }
    @ExceptionHandler(UserAlreadyExistException.class)
    public final ResponseEntity<String> handleUserAlreadyExistExceptions(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public final ResponseEntity<String> handleInvalidRequestException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NoSuchAccountException.class)
    public final ResponseEntity<String> handleNoSuchAccountException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<String> handleConstraintViolationException(Exception ex) {
        return new ResponseEntity<>("invalid request format, please check the api documentation", HttpStatus.BAD_REQUEST);
    }
}
