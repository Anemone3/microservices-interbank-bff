package com.castaneda.mcs_finance_products.exceptions.base;

public class ProductFinanceNotFoundException extends RuntimeException{
    public ProductFinanceNotFoundException(String productId){
        super("Producto financiero no encontrado con ID: " + productId);
    }
}
