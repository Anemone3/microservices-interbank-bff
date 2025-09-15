package com.castaneda.bff_mobile.dto.external.finances;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CustomerProductFinanceDTO {
    private String productId;
    private String accountNumber;
    private BigDecimal balance;
}
