package com.user.exceptionH;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "For registration you need to be an adult")
public class NotAnAdultException extends RuntimeException{
    public NotAnAdultException(String message){
        super(message);
    }
}
