package com.castaneda.bff_mobile.controller;

import com.castaneda.bff_mobile.client.adapter.ICustomerServiceClient;
import com.castaneda.bff_mobile.service.EncryptService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;


@RestController
@RequestMapping("/tests")
@AllArgsConstructor
public class TestRestController {

    private final ICustomerServiceClient customerServiceClient;
    private final EncryptService encryptService;


    @GetMapping
    public Mono<List<String>> test() {
        return customerServiceClient.findAllCustomers()
                .map(customer -> encryptService.encryptMessage(customer.getDocumentNumber()))
                .collectList();
    }


}
