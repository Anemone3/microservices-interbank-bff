package com.castaneda.mcs_finance_products.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerProductFinanceResponseDTO {
    private String id;
    private String accountNumber;
    private BigDecimal balance;
    private ProductFinanceResponseDTO productFinance;
}
