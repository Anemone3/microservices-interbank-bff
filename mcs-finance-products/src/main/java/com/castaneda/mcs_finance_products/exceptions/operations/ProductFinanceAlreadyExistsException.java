package com.castaneda.mcs_finance_products.exceptions.operations;

public class ProductFinanceAlreadyExistsException extends BusinessRuleException {
    public ProductFinanceAlreadyExistsException(String productType) {
        super("El producto financiero ya existe con tipo: " + productType);
    }
}