package com.castaneda.mcs_finance_products.services.adapter;

import com.castaneda.mcs_finance_products.dto.CustomerProductFinanceDTO;
import com.castaneda.mcs_finance_products.dto.CustomerProductFinanceResponseDTO;

import java.math.BigDecimal;
import java.util.List;

public interface ICustomerProductFinanceService {

    CustomerProductFinanceResponseDTO registerProductFinanceCustomerId(String customerId,CustomerProductFinanceDTO productFinanceDTO);
    List<CustomerProductFinanceResponseDTO> getAllDetailFinancesByCustomerId(String customerId);
    CustomerProductFinanceResponseDTO getProductFinanceByAccountNumber(String accountNumber);
    CustomerProductFinanceResponseDTO depositInAccount(String accountNumber,BigDecimal amount);
}
