package com.castaneda.mcs_finance_products.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DepositRequestDTO {
    private BigDecimal amount;
}