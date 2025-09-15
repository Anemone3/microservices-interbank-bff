package com.castaneda.bff_mobile.mapper;

import com.castaneda.bff_mobile.dto.api.customers.CustomerDetailBFFDTO;
import com.castaneda.bff_mobile.dto.external.customers.CustomerResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface CustomerMapper {


    @Mapping(source = "firstName",target = "firstname")
    @Mapping(source = "middleName",target = "middlename")
    CustomerDetailBFFDTO toCustomerDetailBFFDTO(CustomerResponseDTO customerResponseDTO);
}
