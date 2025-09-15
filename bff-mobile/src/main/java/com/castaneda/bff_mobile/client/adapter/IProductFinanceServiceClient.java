package com.castaneda.bff_mobile.client.adapter;

import com.castaneda.bff_mobile.dto.external.finances.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IProductFinanceServiceClient {
    Flux<ProductFinanceResponseDTO> getProductFinances();
    Mono<ProductFinanceResponseDTO> getProductFinanceById(String productId);
    Mono<ProductFinanceResponseDTO> createProductFinance(ProductFinanceDTO dto);
    Mono<ProductFinanceResponseDTO> updateProductFinance(String productId,ProductFinanceDTO dto);

    Mono<CustomerProductFinanceResponseDTO> registerProductFinanceCustomerId(String customerId, CustomerProductFinanceDTO productFinanceDTO);
    Flux<CustomerProductFinanceResponseDTO> getAllDetailFinancesByCustomerId(String customerId);
    Mono<CustomerProductFinanceResponseDTO> getProductFinanceByAccountNumber(String accountNumber);
    Mono<CustomerProductFinanceResponseDTO> depositInAccount(String accountNumber, DepositAccountDTO depositAccountDTO);

}
