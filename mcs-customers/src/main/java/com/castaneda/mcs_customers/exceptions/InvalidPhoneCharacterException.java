package com.castaneda.mcs_customers.exceptions;

public class InvalidPhoneCharacterException extends RuntimeException {
    public InvalidPhoneCharacterException(String message) {
        super(message);
    }
}
