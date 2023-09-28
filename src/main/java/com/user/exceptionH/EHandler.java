package com.user.exceptionH;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class EHandler {
    @ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity<Object> userNotFound(UserNotFoundException e){
        DefaultException exception = new DefaultException(
                e.getMessage(),
                HttpStatus.NOT_FOUND,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {IllegalEmailException.class})
    public ResponseEntity<Object> illegalEmail(IllegalEmailException e){
        DefaultException exception = new DefaultException(
                e.getMessage(),
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {NotAnAdultException.class})
    public ResponseEntity<Object> notAnAdult(NotAnAdultException e){
        DefaultException exception = new DefaultException(
                e.getMessage(),
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }
}
