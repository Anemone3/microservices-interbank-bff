package com.castaneda.mcs_finance_products.exceptions.base;

public class CustomerAccountNotFoundException extends RuntimeException {
    public CustomerAccountNotFoundException(String accountNumber) {
        super("Cuenta de cliente no encontrada con número: " + accountNumber);
    }
}