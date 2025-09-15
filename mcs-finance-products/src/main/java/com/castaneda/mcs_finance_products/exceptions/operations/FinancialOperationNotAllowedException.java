package com.castaneda.mcs_finance_products.exceptions.operations;

public class FinancialOperationNotAllowedException extends BusinessRuleException {
    public FinancialOperationNotAllowedException(String operation, String reason) {
        super(String.format("Operación financiera '%s' no permitida: %s", operation, reason));
    }
}
