package com.castaneda.bff_mobile.controller;


import com.castaneda.bff_mobile.dto.api.finances.ProductFinanceBFFDTO;
import com.castaneda.bff_mobile.service.BFFMobileService;

import lombok.AllArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
@RequestMapping("/api/product-finances")
@AllArgsConstructor
public class ProductFinanceRESTController {

    private final BFFMobileService bffMobileService;

    @GetMapping
    public Flux<ProductFinanceBFFDTO> getProductFinances() {
        return bffMobileService.getProductFinances();
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ProductFinanceBFFDTO> getProductFinancesStream() {
        return bffMobileService.getProductFinances()
                .delayElements(Duration.ofSeconds(1)); 
    }
}
