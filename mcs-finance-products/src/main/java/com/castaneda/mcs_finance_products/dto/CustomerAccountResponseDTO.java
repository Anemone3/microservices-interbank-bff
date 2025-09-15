package com.castaneda.mcs_finance_products.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerAccountResponseDTO {
    private String id;
    private String customerId;
    private List<CustomerProductFinanceResponseDTO> productsFinances;
}
