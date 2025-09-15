package com.castaneda.bff_web.dto.external.finances;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductFinanceDTO {
    private String productType;
    private String description;
    private BigDecimal interestRate;
}
