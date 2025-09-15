package com.castaneda.mcs_finance_products.mapper;

import com.castaneda.mcs_finance_products.domain.ProductFinance;
import com.castaneda.mcs_finance_products.dto.ProductFinanceDTO;
import com.castaneda.mcs_finance_products.dto.ProductFinanceResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductFinanceMapper {

    @Mapping(source = "id", target = "productId")
    ProductFinanceResponseDTO toProductFinanceResponseDTO(ProductFinance productFinance);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "customerProductFinances", ignore = true)
    ProductFinance toProductFinance(ProductFinanceDTO productFinanceDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "customerProductFinances", ignore = true)
    ProductFinance updateProductFromDto(ProductFinanceDTO dto, @MappingTarget ProductFinance productFinance);
}
