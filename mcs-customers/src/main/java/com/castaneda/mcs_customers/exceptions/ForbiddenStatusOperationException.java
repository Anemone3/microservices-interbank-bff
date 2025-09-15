package com.castaneda.mcs_customers.exceptions;

public class ForbiddenStatusOperationException extends RuntimeException {
    public ForbiddenStatusOperationException(String message) {
        super(message);
    }
}
