package com.castaneda.bff_web.service;

import com.castaneda.bff_web.dto.api.customers.CustomerDetailBFFDTO;
import com.castaneda.bff_web.dto.api.finances.AccountCustomerProductBFFDTO;
import com.castaneda.bff_web.dto.api.finances.ProductFinanceBFFDTO;
import com.castaneda.bff_web.dto.external.customers.CustomerDTO;
import com.castaneda.bff_web.dto.external.finances.CustomerProductFinanceDTO;
import com.castaneda.bff_web.dto.external.finances.ProductFinanceDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IBFFService {
    Flux<ProductFinanceBFFDTO> getProductFinances();
    Mono<ProductFinanceBFFDTO> createProductFinance(ProductFinanceDTO productFinanceDTO);
    Mono<ProductFinanceBFFDTO> getProductFinance(String productId);

    Mono<AccountCustomerProductBFFDTO> createAccountFinanceToCustomer(String documentNumber, CustomerProductFinanceDTO financeDTO);
    Mono<CustomerDetailBFFDTO> createCustomer(CustomerDTO customerDetail);
    Flux<CustomerDetailBFFDTO> getCustomers();
    Mono<CustomerDetailBFFDTO> updateCustomer(String customerId,CustomerDTO customerDTO);
    Mono<CustomerDetailBFFDTO> blockCustomer(String documentNumber);
}
