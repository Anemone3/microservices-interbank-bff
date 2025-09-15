package com.castaneda.mcs_finance_products.exceptions.operations;

public class BusinessRuleException extends RuntimeException {
    public BusinessRuleException(String message) {
        super(message);
    }
}