package com.castaneda.mcs_finance_products.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerProductFinanceDTO {
    private String productId;
    private String accountNumber;
    private BigDecimal balance;
}
