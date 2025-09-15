package com.castaneda.bff_mobile.dto.api.finances;

import java.math.BigDecimal;

import com.castaneda.bff_mobile.dto.external.finances.ProductFinanceResponseDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountCustomerProductBFFDTO {
    private String id;
    private String accountNumber;
    private BigDecimal balance;
    private ProductFinanceResponseDTO productFinance;
}
