package com.castaneda.bff_mobile.dto.external.finances;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductFinanceResponseDTO {
    private String productId;
    private String productType;
    private String description;
    private BigDecimal interestRate;
}
