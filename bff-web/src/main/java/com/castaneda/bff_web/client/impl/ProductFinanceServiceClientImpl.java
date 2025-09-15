package com.castaneda.bff_web.client.impl;

import com.castaneda.bff_web.client.adapter.IProductFinanceServiceClient;
import com.castaneda.bff_web.dto.external.finances.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class ProductFinanceServiceClientImpl implements IProductFinanceServiceClient {

    private final WebClient productWebClient;

    private static final String PRODUCT_FINANCES_URL = "/product-finances";
    private static final String FINANCE_URL = "/finances";

    public ProductFinanceServiceClientImpl(@Qualifier("productWebClient") WebClient productWebClient) {
        this.productWebClient = productWebClient;
    }


    @Override
    public Flux<ProductFinanceResponseDTO> getProductFinances() {
        return productWebClient.get().uri(PRODUCT_FINANCES_URL).retrieve().bodyToFlux(ProductFinanceResponseDTO.class);
    }

    @Override
    public Mono<ProductFinanceResponseDTO> getProductFinanceById(String productId) {
        return productWebClient.get().uri(PRODUCT_FINANCES_URL+"/{id}",productId).retrieve().bodyToMono(ProductFinanceResponseDTO.class);
    }

    @Override
    public Mono<ProductFinanceResponseDTO> createProductFinance(ProductFinanceDTO dto) {
        return productWebClient.post().uri(PRODUCT_FINANCES_URL).contentType(MediaType.APPLICATION_JSON).bodyValue(dto).retrieve().bodyToMono(ProductFinanceResponseDTO.class);
    }

    @Override
    public Mono<ProductFinanceResponseDTO> updateProductFinance(String productId, ProductFinanceDTO dto) {
        return productWebClient.patch().uri(PRODUCT_FINANCES_URL+"/{id}",productId).bodyValue(dto).retrieve().bodyToMono(ProductFinanceResponseDTO.class);
    }

    @Override
    public Mono<CustomerProductFinanceResponseDTO> registerProductFinanceCustomerId(String customerId, CustomerProductFinanceDTO productFinanceDTO) {
        return productWebClient.post().uri(FINANCE_URL+"/customer/{id}",customerId).bodyValue(productFinanceDTO).retrieve().bodyToMono(CustomerProductFinanceResponseDTO.class);
    }

    @Override
    public Flux<CustomerProductFinanceResponseDTO> getAllDetailFinancesByCustomerId(String customerId) {
        return productWebClient.get().uri(FINANCE_URL+"/customer/{id}/details",customerId).retrieve().bodyToFlux(CustomerProductFinanceResponseDTO.class);
    }

    @Override
    public Mono<CustomerProductFinanceResponseDTO> getProductFinanceByAccountNumber(String accountNumber) {
        return productWebClient.get().uri(FINANCE_URL+"/{accountNumber}/account",accountNumber).retrieve().bodyToMono(CustomerProductFinanceResponseDTO.class);
    }

    @Override
    public Mono<CustomerProductFinanceResponseDTO> depositInAccount(String accountNumber, DepositAccountDTO depositAccountDTO) {
        return productWebClient.post().uri(FINANCE_URL+"/{accountNumber}/account/deposit",accountNumber).bodyValue(depositAccountDTO).retrieve().bodyToMono(CustomerProductFinanceResponseDTO.class);
    }
}
