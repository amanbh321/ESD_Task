package com.aman.esd_task.exception;

import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({JwtException.class})
    public ResponseEntity<String> jwtExceptionHandler(JwtException e) {
        String message = e.getMessage();
        return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({CustomerNotFound.class, ProductNotFoundException.class})
    public ResponseEntity<String> customerNotFoundExceptionHandler(CustomerNotFound e) {
        String message = e.getMessage();
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }


}
