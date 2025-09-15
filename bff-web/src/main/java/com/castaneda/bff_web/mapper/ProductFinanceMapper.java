package com.castaneda.bff_web.mapper;


import com.castaneda.bff_web.dto.api.finances.AccountCustomerProductBFFDTO;
import com.castaneda.bff_web.dto.api.finances.ProductFinanceBFFDTO;
import com.castaneda.bff_web.dto.external.finances.CustomerProductFinanceResponseDTO;
import com.castaneda.bff_web.dto.external.finances.ProductFinanceResponseDTO;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductFinanceMapper {

  ProductFinanceBFFDTO toProductFinanceBFFDTO(ProductFinanceResponseDTO productFinanceResponseDTO);
   
   @Mapping(source = "productFinance", target = "productFinance")
   AccountCustomerProductBFFDTO toAccountCustomerProductBFFDTO(CustomerProductFinanceResponseDTO customerProductFinanceResponseDTO);
   
}
