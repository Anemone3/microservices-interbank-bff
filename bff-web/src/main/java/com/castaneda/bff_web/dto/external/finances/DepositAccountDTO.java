package com.castaneda.bff_web.dto.external.finances;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DepositAccountDTO {
    BigDecimal amount;
}
