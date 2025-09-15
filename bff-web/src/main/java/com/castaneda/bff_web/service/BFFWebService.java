package com.castaneda.bff_web.service;

import com.castaneda.bff_web.client.adapter.ICustomerServiceClient;
import com.castaneda.bff_web.client.adapter.IProductFinanceServiceClient;
import com.castaneda.bff_web.dto.api.customers.CustomerDetailBFFDTO;
import com.castaneda.bff_web.dto.api.finances.AccountCustomerProductBFFDTO;
import com.castaneda.bff_web.dto.api.finances.ProductFinanceBFFDTO;
import com.castaneda.bff_web.dto.external.customers.CustomerDTO;
import com.castaneda.bff_web.dto.external.finances.CustomerProductFinanceDTO;
import com.castaneda.bff_web.dto.external.finances.ProductFinanceDTO;
import com.castaneda.bff_web.mapper.CustomerMapper;
import com.castaneda.bff_web.mapper.ProductFinanceMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class BFFWebService implements IBFFService{
    private final IProductFinanceServiceClient productClient;
    private final ICustomerServiceClient customerClient;

    private final ProductFinanceMapper productFinanceMapper;
    private final CustomerMapper customerMapper;
    @Override
    public Flux<ProductFinanceBFFDTO> getProductFinances() {
        return productClient.getProductFinances().map(productFinanceMapper::toProductFinanceBFFDTO);
    }

    @Override
    public Mono<ProductFinanceBFFDTO> createProductFinance(ProductFinanceDTO productFinanceDTO) {
        return productClient.createProductFinance(productFinanceDTO).map(productFinanceMapper::toProductFinanceBFFDTO);
    }

    @Override
    public Mono<ProductFinanceBFFDTO> getProductFinance(String productId) {
        return productClient.getProductFinanceById(productId).map(productFinanceMapper::toProductFinanceBFFDTO);
    }

    @Override
    public Mono<AccountCustomerProductBFFDTO> createAccountFinanceToCustomer(String documentNumber, CustomerProductFinanceDTO financeDTO) {
        return customerClient.findCustomerByDocument(documentNumber)
                .flatMap(customer-> productClient.registerProductFinanceCustomerId(customer.getId(), financeDTO)
                        .map(productFinanceMapper::toAccountCustomerProductBFFDTO)
                );

    }

    @Override
    public Mono<CustomerDetailBFFDTO> createCustomer(CustomerDTO customerDetail) {
        return customerClient.createCustomer(customerDetail).filter(customer-> customer.getStatus().equalsIgnoreCase("ACTIVO")).map(customerMapper::toCustomerDetailBFFDTO);
    }

    @Override
    public Flux<CustomerDetailBFFDTO> getCustomers() {
        return customerClient.findAllCustomers()
                .filter(customer-> customer.getStatus().equalsIgnoreCase("ACTIVO"))
                .map(customerMapper::toCustomerDetailBFFDTO);
    }

    @Override
    public Mono<CustomerDetailBFFDTO> updateCustomer(String customerId, CustomerDTO customerDTO) {
        return customerClient.findCustomerByDocument(customerId).flatMap(customerFound->
            customerClient.updateCustomer(customerFound.getId(), customerDTO).map(customerMapper::toCustomerDetailBFFDTO)
        );
    }

    @Override
    public Mono<CustomerDetailBFFDTO> blockCustomer(String documentNumber) {
        return customerClient.findCustomerByDocument(documentNumber).flatMap(customerFound->
            customerClient.deleteCustomer(customerFound.getId()).map(customerMapper::toCustomerDetailBFFDTO)
        );
    }
}
