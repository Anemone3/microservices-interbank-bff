package com.castaneda.bff_web.dto.api.finances;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductFinanceBFFDTO {
    private String productId;
    private String productType;
    private String description;
    private BigDecimal interestRate;
}
