package com.castaneda.bff_mobile.client.impl;

import com.castaneda.bff_mobile.client.adapter.ICustomerServiceClient;
import com.castaneda.bff_mobile.dto.external.customers.CustomerDTO;
import com.castaneda.bff_mobile.dto.external.customers.CustomerResponseDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class CustomerServiceClientImpl implements ICustomerServiceClient {
    private final WebClient customerWebClient;

    private static final String CUSTOMERS_URL = "/customers";

    public CustomerServiceClientImpl(@Qualifier("customerWebClient") WebClient customerWebClient) {
        this.customerWebClient = customerWebClient;
    }


    @Override
    public Mono<CustomerResponseDTO> findCustomerById(String id) {
        return customerWebClient.get().uri(CUSTOMERS_URL + "/{customerId}" ,id).retrieve().bodyToMono(CustomerResponseDTO.class);
    }

    @Override
    public Mono<CustomerResponseDTO> findCustomerByDocument(String documentNumber) {
        return customerWebClient.get().uri(CUSTOMERS_URL +"/{documentNumber}/document",documentNumber).retrieve().bodyToMono(CustomerResponseDTO.class);
    }

    @Override
    public Flux<CustomerResponseDTO> findAllCustomers() {
        return customerWebClient.get().uri(CUSTOMERS_URL).retrieve().bodyToFlux(CustomerResponseDTO.class);
    }

    @Override
    public Mono<CustomerResponseDTO> createCustomer(CustomerDTO customerDto) {
        return customerWebClient.post().uri(CUSTOMERS_URL).bodyValue(customerDto).retrieve().bodyToMono(CustomerResponseDTO.class);
    }

    @Override
    public Mono<CustomerResponseDTO> updateCustomer(String id, CustomerDTO customerDto) {
        return customerWebClient.patch().uri(CUSTOMERS_URL+"/{customerId}",id).bodyValue(customerDto).retrieve().bodyToMono(CustomerResponseDTO.class);
    }

    @Override
    public Mono<CustomerResponseDTO> deleteCustomer(String id) {
        return customerWebClient.delete().uri(CUSTOMERS_URL+"/{customerId}",id).retrieve().bodyToMono(CustomerResponseDTO.class);
    }
}
