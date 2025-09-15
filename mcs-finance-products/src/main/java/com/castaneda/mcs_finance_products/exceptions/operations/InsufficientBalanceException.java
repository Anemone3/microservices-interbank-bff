package com.castaneda.mcs_finance_products.exceptions.operations;

import java.math.BigDecimal;

public class InsufficientBalanceException extends BusinessRuleException {
    public InsufficientBalanceException(String accountNumber, BigDecimal currentBalance, BigDecimal requestedAmount) {
        super(String.format("Saldo insuficiente en la cuenta %s. Actual: %s, Solicitado: %s", 
                accountNumber, currentBalance, requestedAmount));
    }
}