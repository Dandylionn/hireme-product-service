package com.hireme.product.exception;

import com.hireme.product.exception.ErrorResponse;
import com.hireme.product.exception.RecommendationServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;


@ControllerAdvice
public class RecommendationGlobalExceptionHandler {

    @ExceptionHandler(RecommendationServiceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse handleTutorServiceException(RecommendationServiceException ex) {
        return new ErrorResponse("Tutor Service Error", ex.getMessage());
    }

    // Add more exception handlers for different exceptions if needed
}
