package com.castaneda.mcs_finance_products.services.adapter;

import com.castaneda.mcs_finance_products.dto.ProductFinanceDTO;
import com.castaneda.mcs_finance_products.dto.ProductFinanceResponseDTO;

import java.util.List;

public interface IProductFinanceService {
    ProductFinanceResponseDTO getProductFinanceById(String productId);
    List<ProductFinanceResponseDTO> getProductsFinances();
    ProductFinanceResponseDTO updateProductsFinance(String productId,ProductFinanceDTO productFinanceDTO);
    ProductFinanceResponseDTO addProductsFinance(ProductFinanceDTO productFinanceDTO);

}
