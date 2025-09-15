package com.castaneda.bff_mobile.dto.api.finances;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DepositRequestBFFDTO {
    String accountNumber;
    BigDecimal amount;
}
