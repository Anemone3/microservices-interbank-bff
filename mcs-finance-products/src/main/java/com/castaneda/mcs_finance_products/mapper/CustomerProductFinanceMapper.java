package com.castaneda.mcs_finance_products.mapper;

import com.castaneda.mcs_finance_products.domain.CustomerProductFinance;
import com.castaneda.mcs_finance_products.dto.CustomerProductFinanceResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;


@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,uses = ProductFinanceMapper.class)
public interface CustomerProductFinanceMapper {

    @Mapping(source = "product", target = "productFinance")
    CustomerProductFinanceResponseDTO toCustomerProductFinanceResponseDTO(CustomerProductFinance customerProductFinance);



}
