package com.hireme.product.exception;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

public class CustomResponseErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) {
        // Define your error detection logic here
        return false; // Return true if an error is detected
    }

    @Override
    public void handleError(ClientHttpResponse response) {
        // Handle the error response here
        // You can extract error information from the response and throw an exception or perform other actions
    }
}
