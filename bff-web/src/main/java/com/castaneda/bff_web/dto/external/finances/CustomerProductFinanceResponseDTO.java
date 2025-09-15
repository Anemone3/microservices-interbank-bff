package com.castaneda.bff_web.dto.external.finances;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CustomerProductFinanceResponseDTO {
    private String id;
    private String accountNumber;
    private BigDecimal balance;
    private ProductFinanceResponseDTO productFinance;
}
