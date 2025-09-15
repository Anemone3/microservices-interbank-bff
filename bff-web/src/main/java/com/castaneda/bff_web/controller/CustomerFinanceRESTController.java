package com.castaneda.bff_web.controller;


import com.castaneda.bff_web.dto.api.customers.CustomerDetailBFFDTO;
import com.castaneda.bff_web.dto.api.finances.AccountCustomerProductBFFDTO;
import com.castaneda.bff_web.dto.external.customers.CustomerDTO;
import com.castaneda.bff_web.dto.external.finances.CustomerProductFinanceDTO;
import com.castaneda.bff_web.service.IBFFService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/customers")
@AllArgsConstructor
public class CustomerFinanceRESTController {
    
    private final IBFFService service;


    @GetMapping
    public Flux<CustomerDetailBFFDTO> findAll() {
        return service.getCustomers();
    }

    @PostMapping
    public Mono<CustomerDetailBFFDTO> create(@RequestBody CustomerDTO dto) {
        return service.createCustomer(dto);
    }


    @PostMapping("/{documentNumber}/finance")
    public Mono<AccountCustomerProductBFFDTO> finance(@PathVariable("documentNumber") String documentNumber, @RequestBody CustomerProductFinanceDTO dto) {
        return service.createAccountFinanceToCustomer(documentNumber,dto);
    }


    @PatchMapping("/{documentNumber}")
    public Mono<CustomerDetailBFFDTO> update(@PathVariable("documentNumber") String documentNumber,@RequestBody CustomerDTO dto) {
        return service.updateCustomer(documentNumber,dto);
    }

    @DeleteMapping("/{customerId}/block")
    public Mono<CustomerDetailBFFDTO> delete(@PathVariable("customerId") String customerId) {
        return service.blockCustomer(customerId);
    }
}
