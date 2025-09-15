package com.castaneda.mcs_finance_products.services.impl;


import com.castaneda.mcs_finance_products.dto.ProductFinanceDTO;
import com.castaneda.mcs_finance_products.dto.ProductFinanceResponseDTO;
import com.castaneda.mcs_finance_products.exceptions.base.ProductFinanceNotFoundException;
import com.castaneda.mcs_finance_products.exceptions.operations.ProductFinanceAlreadyExistsException;
import com.castaneda.mcs_finance_products.mapper.ProductFinanceMapper;
import com.castaneda.mcs_finance_products.repository.ProductFinanceRepository;
import com.castaneda.mcs_finance_products.services.adapter.IProductFinanceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductFinanceServiceImpl implements IProductFinanceService {
    private final ProductFinanceRepository productFinanceRepository;
    private final ProductFinanceMapper productFinanceMapper;

    @Override
    public ProductFinanceResponseDTO getProductFinanceById(String productId) {
        var productFinance = productFinanceRepository.findById(productId)
                .orElseThrow(() -> new ProductFinanceNotFoundException(productId));
       return productFinanceMapper.toProductFinanceResponseDTO(productFinance);
    }

    @Override
    public List<ProductFinanceResponseDTO> getProductsFinances() {
        return productFinanceRepository.findAll().stream().map(productFinanceMapper::toProductFinanceResponseDTO).collect(Collectors.toList());
    }

    @Override
    public ProductFinanceResponseDTO updateProductsFinance(String productId,ProductFinanceDTO productFinanceDTO) {
        var productFinance = productFinanceRepository.findById(productId)
                .orElseThrow(() -> new ProductFinanceNotFoundException(productId));

        productFinanceMapper.updateProductFromDto(productFinanceDTO, productFinance);
        var productUpdated = productFinanceRepository.save(productFinance);

        return productFinanceMapper.toProductFinanceResponseDTO(productUpdated);
    }

    @Override
    public ProductFinanceResponseDTO addProductsFinance(ProductFinanceDTO productFinanceDTO) {
        var existsProductType = productFinanceRepository.findByProductType(productFinanceDTO.getProductType());
        if(existsProductType.isPresent()){
           throw new ProductFinanceAlreadyExistsException(productFinanceDTO.getProductType());
        }
        var newProductFinance = productFinanceMapper.toProductFinance(productFinanceDTO);

        return productFinanceMapper.toProductFinanceResponseDTO(productFinanceRepository.save(newProductFinance));
    }
}
