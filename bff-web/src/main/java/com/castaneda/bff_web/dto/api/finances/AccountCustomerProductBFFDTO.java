package com.castaneda.bff_web.dto.api.finances;

import com.castaneda.bff_web.dto.external.finances.ProductFinanceResponseDTO;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class AccountCustomerProductBFFDTO {
    private String id;
    private String accountNumber;
    private BigDecimal balance;
    private ProductFinanceResponseDTO productFinance;
}
