package com.user.exceptionH;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Illegal email")
public class IllegalEmailException extends RuntimeException{
    public IllegalEmailException(String message){
        super(message);
    }
}
