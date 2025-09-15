package com.castaneda.mcs_customers.exceptions;

public class InvalidDocumentNumberException extends RuntimeException {
    public InvalidDocumentNumberException(String message) {
        super(message);
    }
}
