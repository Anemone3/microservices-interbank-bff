package com.castaneda.bff_mobile.dto.external.finances;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CustomerProductFinanceResponseDTO {
    private String id;
    private String accountNumber;
    private BigDecimal balance;
    private ProductFinanceResponseDTO productFinance;
}
