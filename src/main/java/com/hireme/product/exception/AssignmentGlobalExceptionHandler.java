package com.hireme.product.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AssignmentGlobalExceptionHandler {
    @ExceptionHandler(InvalidStatusTransitionException.class)
    public ResponseEntity<Object> handleInvalidStatusTransition(InvalidStatusTransitionException ex) {
        // customize the error response
        return new ResponseEntity<>("Invalid status transition: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
