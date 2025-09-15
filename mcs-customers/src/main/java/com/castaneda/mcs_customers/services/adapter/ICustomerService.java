package com.castaneda.mcs_customers.services.adapter;

import com.castaneda.mcs_customers.dto.CustomerDTO;
import com.castaneda.mcs_customers.dto.CustomerResponseDTO;

import java.util.List;

public interface ICustomerService {
    CustomerResponseDTO findCustomerById(String id);
    CustomerResponseDTO findCustomerByDocument(String documentNumber);
    List<CustomerResponseDTO> findAllCustomers();
    CustomerResponseDTO createCustomer(CustomerDTO customerDto);
    CustomerResponseDTO updateCustomer(String id,CustomerDTO customerDto);
    CustomerResponseDTO deleteCustomer(String id);
}
