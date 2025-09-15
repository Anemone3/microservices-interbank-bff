package com.castaneda.mcs_finance_products.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductFinanceDTO {
    private String productType;
    private String description;
    private BigDecimal interestRate;
}
