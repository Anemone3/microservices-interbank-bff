package com.castaneda.bff_web.controller;



import com.castaneda.bff_web.dto.api.finances.ProductFinanceBFFDTO;
import com.castaneda.bff_web.dto.external.finances.ProductFinanceDTO;
import com.castaneda.bff_web.service.IBFFService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping("/api/product-finances")
@AllArgsConstructor
public class ProductFinanceRESTController {

    private final IBFFService service;

    @GetMapping
    public Flux<ProductFinanceBFFDTO> getAl() {
        return service.getProductFinances();
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ProductFinanceBFFDTO> getProductFinancesStream() {
        return service.getProductFinances()
                .delayElements(Duration.ofSeconds(1)); 
    }

    @GetMapping("/{productId}")
    public Mono<ProductFinanceBFFDTO> getById(@PathVariable("productId") String productId) {
        return service.getProductFinance(productId);
    }

    @PostMapping
    public Mono<ProductFinanceBFFDTO> create(@RequestBody  ProductFinanceDTO productFinanceDTO) {
        return service.createProductFinance(productFinanceDTO);
    }
}
