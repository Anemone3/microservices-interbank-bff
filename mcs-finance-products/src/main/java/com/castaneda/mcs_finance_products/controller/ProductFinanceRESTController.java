package com.castaneda.mcs_finance_products.controller;


import com.castaneda.mcs_finance_products.dto.ProductFinanceDTO;
import com.castaneda.mcs_finance_products.dto.ProductFinanceResponseDTO;
import com.castaneda.mcs_finance_products.services.adapter.IProductFinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product-finances")
public class ProductFinanceRESTController {

    @Autowired
    private IProductFinanceService productFinanceService;

    @GetMapping
    public ResponseEntity<List<ProductFinanceResponseDTO>> getProductFinances() {
        return new ResponseEntity<>(productFinanceService.getProductsFinances(), HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductFinanceResponseDTO> getProductFinanceById(@PathVariable("productId") String productId) {
        return new ResponseEntity<>(productFinanceService.getProductFinanceById(productId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductFinanceResponseDTO> createProductFinance(@RequestBody ProductFinanceDTO dto){
        return new ResponseEntity<>(productFinanceService.addProductsFinance(dto), HttpStatus.CREATED);
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<ProductFinanceResponseDTO> updateProductFinance(@PathVariable("productId") String productId,@RequestBody ProductFinanceDTO dto){
        return new ResponseEntity<>(productFinanceService.updateProductsFinance(productId,dto), HttpStatus.OK);
    }


}
