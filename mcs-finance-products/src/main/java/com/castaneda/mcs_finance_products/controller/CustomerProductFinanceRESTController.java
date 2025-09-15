package com.castaneda.mcs_finance_products.controller;

import com.castaneda.mcs_finance_products.dto.CustomerProductFinanceDTO;
import com.castaneda.mcs_finance_products.dto.CustomerProductFinanceResponseDTO;
import com.castaneda.mcs_finance_products.dto.DepositRequestDTO;
import com.castaneda.mcs_finance_products.services.adapter.ICustomerProductFinanceService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/finances")
@AllArgsConstructor
public class CustomerProductFinanceRESTController {

    private final ICustomerProductFinanceService customerProductFinanceService;

    @PostMapping("/customer/{customerId}")
    public ResponseEntity<CustomerProductFinanceResponseDTO> registerProductFinanceCustomer(@PathVariable String customerId, @RequestBody CustomerProductFinanceDTO dto) {
        return  new ResponseEntity<>(customerProductFinanceService.registerProductFinanceCustomerId(customerId,dto), HttpStatus.OK);
    }

    @GetMapping("/customer/{customerId}/details")
    public ResponseEntity<List<CustomerProductFinanceResponseDTO>> getAllDetailProductsByCustomer(@PathVariable String customerId) {
        return new ResponseEntity<>(customerProductFinanceService.getAllDetailFinancesByCustomerId(customerId),HttpStatus.OK);
    }

    @GetMapping("/{accountNumber}/account")
    public ResponseEntity<CustomerProductFinanceResponseDTO> getCustomerProductFinanceByAccount(@PathVariable String accountNumber) {
        return new ResponseEntity<>(customerProductFinanceService.getProductFinanceByAccountNumber(accountNumber),HttpStatus.OK);
    }

    @PostMapping("/{accountNumber}/account/deposit")
    public ResponseEntity<CustomerProductFinanceResponseDTO> depositProductFinance(@PathVariable String accountNumber, @RequestBody DepositRequestDTO dto) {
        return new ResponseEntity<>(customerProductFinanceService.depositInAccount(accountNumber,dto.getAmount()),HttpStatus.OK);
    }
}
