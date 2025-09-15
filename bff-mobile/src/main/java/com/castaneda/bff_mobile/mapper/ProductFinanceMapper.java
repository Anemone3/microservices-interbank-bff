package com.castaneda.bff_mobile.mapper;

import com.castaneda.bff_mobile.dto.api.finances.AccountCustomerProductBFFDTO;
import com.castaneda.bff_mobile.dto.api.finances.ProductFinanceBFFDTO;
import com.castaneda.bff_mobile.dto.external.finances.CustomerProductFinanceResponseDTO;
import com.castaneda.bff_mobile.dto.external.finances.ProductFinanceResponseDTO;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductFinanceMapper {

   ProductFinanceBFFDTO toProductFinanceResponseDTO(ProductFinanceResponseDTO productFinanceResponseDTO);
   
   @Mapping(source = "productFinance", target = "productFinance")
   AccountCustomerProductBFFDTO toAccountCustomerProductBFFDTO(CustomerProductFinanceResponseDTO customerProductFinanceResponseDTO);
   
   List<AccountCustomerProductBFFDTO> toAccountCustomerProductBFFDTOList(List<CustomerProductFinanceResponseDTO> customerProductFinanceResponseDTOList);
}
