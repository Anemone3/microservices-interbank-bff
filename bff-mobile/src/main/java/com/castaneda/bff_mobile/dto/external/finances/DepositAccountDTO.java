package com.castaneda.bff_mobile.dto.external.finances;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class DepositAccountDTO {
    BigDecimal amount;
}
