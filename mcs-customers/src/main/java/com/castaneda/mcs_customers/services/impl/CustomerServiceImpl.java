package com.castaneda.mcs_customers.services.impl;


import com.castaneda.mcs_customers.domain.Customer;
import com.castaneda.mcs_customers.dto.CustomerDTO;
import com.castaneda.mcs_customers.dto.CustomerResponseDTO;
import com.castaneda.mcs_customers.enums.StatusCustomer;
import com.castaneda.mcs_customers.exceptions.CustomerNotFoundException;
import com.castaneda.mcs_customers.mapper.CustomerMapper;
import com.castaneda.mcs_customers.repository.CustomerRepository;
import com.castaneda.mcs_customers.services.adapter.ICustomerService;
import com.castaneda.mcs_customers.validations.CustomerValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public CustomerResponseDTO findCustomerById(String id) {

        Optional<Customer> customer = customerRepository.findById(id);

        if(customer.isPresent()){
            return customerMapper.toCustomerResponseDTO(customer.get());
        }
        throw new CustomerNotFoundException("Customer not found with id: " + id);
    }

    @Override
    public CustomerResponseDTO findCustomerByDocument(String documentNumber){
        var customer = customerRepository.findByDocumentNumber(documentNumber).orElseThrow(
                () -> new CustomerNotFoundException("Customer not found with documentNumber: " + documentNumber)
        );
        CustomerValidator.validateDocuments(CustomerDTO.builder()
                        .documentType(customer.getDocumentType())
                        .documentNumber(documentNumber)
                .build());

        return customerMapper.toCustomerResponseDTO(customer);
    }

    @Override
    public List<CustomerResponseDTO> findAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream().map(customerMapper::toCustomerResponseDTO).toList();
    }

    @Override
    public CustomerResponseDTO createCustomer(CustomerDTO customerDto) {
        CustomerValidator.validateFields(customerDto);
        Customer newCustomer = customerMapper.toCustomer(customerDto);
        newCustomer.setStatus(StatusCustomer.ACTIVO);
        Customer savedCustomer = customerRepository.save(newCustomer);
        return customerMapper.toCustomerResponseDTO(savedCustomer);
    }

    @Override
    public CustomerResponseDTO updateCustomer(String id, CustomerDTO customerDto) {
        Customer customerToUpdate = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + id));

        CustomerValidator.validateStatusCustomer(customerToUpdate.getStatus());

        customerMapper.updateCustomerFromDto(customerDto, customerToUpdate);

        Customer updatedCustomer = customerRepository.save(customerToUpdate);

        return customerMapper.toCustomerResponseDTO(updatedCustomer);
    }

    @Override
    public CustomerResponseDTO deleteCustomer(String id) {
        Customer customerToDelete = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + id));

        if(customerToDelete.getStatus() == StatusCustomer.ACTIVO){
            customerToDelete.setStatus(StatusCustomer.BLOQUEADO);
            customerRepository.save(customerToDelete);
        }
        return customerMapper.toCustomerResponseDTO(customerToDelete);
    }
}
