package com.castaneda.bff_web.client.adapter;

import com.castaneda.bff_web.dto.external.customers.CustomerDTO;
import com.castaneda.bff_web.dto.external.customers.CustomerResponseDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface ICustomerServiceClient {
    Mono<CustomerResponseDTO> findCustomerById(String id);
    Mono<CustomerResponseDTO> findCustomerByDocument(String documentNumber);
    Flux<CustomerResponseDTO> findAllCustomers();
    Mono<CustomerResponseDTO> createCustomer(CustomerDTO customerDto);
    Mono<CustomerResponseDTO> updateCustomer(String id,CustomerDTO customerDto);
    Mono<CustomerResponseDTO> deleteCustomer(String id);
}
