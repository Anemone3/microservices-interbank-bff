package com.castaneda.bff_mobile.controller;

import com.castaneda.bff_mobile.dto.api.finances.AccountCustomerProductBFFDTO;
import com.castaneda.bff_mobile.dto.api.finances.DepositRequestBFFDTO;
import com.castaneda.bff_mobile.dto.external.finances.DepositAccountDTO;
import org.springframework.web.bind.annotation.*;

import com.castaneda.bff_mobile.dto.api.customers.CustomerDetailFinanceBFFDTO;
import com.castaneda.bff_mobile.service.BFFMobileService;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/customer-finances")
@AllArgsConstructor
public class CustomerFinanceRESTController {
    
    private final BFFMobileService bffMobileService;

    @GetMapping("/{documentNumber}")
    public Mono<CustomerDetailFinanceBFFDTO> getInfoCustomerByUniqueCode(@PathVariable("documentNumber") String documentNumber) {
        return bffMobileService.getInfoByUniqueCode(documentNumber);
    }

    @PostMapping("/account/deposit")
    public Mono<AccountCustomerProductBFFDTO> depositAccount(@RequestBody DepositRequestBFFDTO dto) {
        return bffMobileService.depositAccount(dto.getAccountNumber(), DepositAccountDTO.builder().amount(dto.getAmount()).build());
    }
}
