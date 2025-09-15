package com.castaneda.mcs_customers.exceptions;

public class InvalidFieldRequestBodyException extends RuntimeException {
    public InvalidFieldRequestBodyException(String message) {
        super(message);
    }
}
