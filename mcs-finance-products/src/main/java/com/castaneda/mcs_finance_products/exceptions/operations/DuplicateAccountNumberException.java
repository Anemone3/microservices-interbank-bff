package com.castaneda.mcs_finance_products.exceptions.operations;

public class DuplicateAccountNumberException extends BusinessRuleException {
    public DuplicateAccountNumberException(String accountNumber) {
        super("El número de cuenta ya existe: " + accountNumber);
    }
}