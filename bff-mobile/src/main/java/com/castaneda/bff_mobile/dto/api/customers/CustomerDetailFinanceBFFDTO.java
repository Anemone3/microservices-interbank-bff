package com.castaneda.bff_mobile.dto.api.customers;

import com.castaneda.bff_mobile.dto.api.finances.AccountCustomerProductBFFDTO;
import com.castaneda.bff_mobile.dto.external.customers.CustomerResponseDTO;
import com.castaneda.bff_mobile.dto.external.finances.CustomerProductFinanceResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDetailFinanceBFFDTO {
    private String names;
    private String surnames;
    private String documentType;
    private String documentNumber;
    private List<AccountCustomerProductBFFDTO> productsFinances;

    public CustomerDetailFinanceBFFDTO(CustomerResponseDTO customer, List<CustomerProductFinanceResponseDTO> finances){
        this.names = customer.getFirstName() + " " + customer.getMiddleName();
        this.surnames = customer.getPatSurname() + " " + customer.getMatSurname(); 
        this.documentType = customer.getDocumentType();
        this.documentNumber = customer.getDocumentNumber();
        this.productsFinances = finances.stream()
            .map(finance -> AccountCustomerProductBFFDTO.builder()
                .id(finance.getId())
                .accountNumber(finance.getAccountNumber())
                .balance(finance.getBalance())
                .productFinance(finance.getProductFinance())
                .build())
            .toList();
    }
}
