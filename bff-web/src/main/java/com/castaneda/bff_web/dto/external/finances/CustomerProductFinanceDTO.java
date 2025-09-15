package com.castaneda.bff_web.dto.external.finances;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CustomerProductFinanceDTO {
    private String productId;
    private String accountNumber;
    private BigDecimal balance;
}
