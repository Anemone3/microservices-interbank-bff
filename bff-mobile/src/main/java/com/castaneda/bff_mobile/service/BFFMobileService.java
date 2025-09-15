package com.castaneda.bff_mobile.service;

import com.castaneda.bff_mobile.client.adapter.ICustomerServiceClient;
import com.castaneda.bff_mobile.client.adapter.IProductFinanceServiceClient;
import com.castaneda.bff_mobile.dto.api.customers.CustomerDetailBFFDTO;
import com.castaneda.bff_mobile.dto.api.customers.CustomerDetailFinanceBFFDTO;
import com.castaneda.bff_mobile.dto.api.finances.AccountCustomerProductBFFDTO;
import com.castaneda.bff_mobile.dto.api.finances.ProductFinanceBFFDTO;
import com.castaneda.bff_mobile.dto.external.finances.DepositAccountDTO;
import com.castaneda.bff_mobile.dto.external.finances.ProductFinanceResponseDTO;
import com.castaneda.bff_mobile.mapper.CustomerMapper;
import com.castaneda.bff_mobile.mapper.ProductFinanceMapper;
import lombok.AllArgsConstructor;

import java.util.Collections;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
@Service
@AllArgsConstructor
public class BFFMobileService {
    private final IProductFinanceServiceClient productFinanceServiceClient;
    private final ICustomerServiceClient customerServiceClient;
    private final ProductFinanceMapper productFinanceMapper;
    private final CustomerMapper customerMapper;
    private final EncryptService encryptService;

    public Flux<ProductFinanceBFFDTO> getProductFinances() {
        Flux<ProductFinanceResponseDTO> productFinances = productFinanceServiceClient.getProductFinances();

        return productFinances.map(productFinanceMapper::toProductFinanceResponseDTO);
    }

    public Mono<CustomerDetailBFFDTO> getCustomerByDocumentNumber(String documentNumber) {
        return customerServiceClient.findCustomerByDocument(documentNumber)
                    .map(customerMapper::toCustomerDetailBFFDTO);
    }


    public Mono<CustomerDetailFinanceBFFDTO> getInfoByUniqueCode(String uniqueCode) {
//            if(uniqueCode.isEmpty() || uniqueCode == null){
//                return Mono.error(new EncryptException("Codigo invalido o no se puede desincreptar"));
//            }
            // 1.- desencriptar el codigo que llega
            var uniqueCodeDecrypt = encryptService.decryptMessage(uniqueCode);
            log.info("Codigo desencriptado: " + uniqueCodeDecrypt);
            
//            if (uniqueCodeDecrypt == null || uniqueCodeDecrypt.trim().isEmpty()) {
//                return Mono.error(new EncryptException("El código desencriptado es inválido"));
//            }
//
            // 2.- buscar los recursos de diferentes microservicios para transformar el resultado
            var customerMono = customerServiceClient.findCustomerByDocument(uniqueCodeDecrypt);
            
            return customerMono.flatMap(customer->{
                log.info("Customer recibido: {}", customer);

                var financesMono = productFinanceServiceClient.getAllDetailFinancesByCustomerId(customer.getId())
                                        .collectList().defaultIfEmpty(Collections.emptyList());
                return financesMono.map(finances-> new CustomerDetailFinanceBFFDTO(customer,finances));
            });
    }


    public Mono<AccountCustomerProductBFFDTO> depositAccount(String accountNumber,DepositAccountDTO depositDto){
        return productFinanceServiceClient.depositInAccount(accountNumber,depositDto).map(productFinanceMapper::toAccountCustomerProductBFFDTO);
    }

}
