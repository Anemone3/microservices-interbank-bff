package com.castaneda.mcs_customers.mapper;

import com.castaneda.mcs_customers.domain.Customer;
import com.castaneda.mcs_customers.dto.CustomerDTO;
import com.castaneda.mcs_customers.dto.CustomerResponseDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring", 
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true))
public interface CustomerMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "registrationDate", ignore = true)
    Customer toCustomer(CustomerDTO customerDTO);

    CustomerResponseDTO toCustomerResponseDTO(Customer customer);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "registrationDate", ignore = true)
    Customer updateCustomerFromDto(CustomerDTO customerDto, @MappingTarget Customer customerToUpdate);
}
