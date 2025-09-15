package com.castaneda.mcs_customers.controller;

import com.castaneda.mcs_customers.dto.CustomerDTO;
import com.castaneda.mcs_customers.dto.CustomerResponseDTO;
import com.castaneda.mcs_customers.services.adapter.ICustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
@AllArgsConstructor
public class CustomerRESTController {

    private final ICustomerService customerService;

    @GetMapping
    public ResponseEntity<List<CustomerResponseDTO>> getCustomers() {
        return new ResponseEntity<>(customerService.findAllCustomers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> getCustomer(@PathVariable String id) {
        return new ResponseEntity<>(customerService.findCustomerById(id), HttpStatus.OK);
    }

    @GetMapping("/{documentNumber}/document")
    public ResponseEntity<CustomerResponseDTO> getCustomerDetails(@PathVariable String documentNumber) {
        return new ResponseEntity<>(customerService.findCustomerByDocument(documentNumber), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CustomerResponseDTO> createCustomer(@RequestBody CustomerDTO customerDTO) {
        return new ResponseEntity<>(customerService.createCustomer(customerDTO), HttpStatus.CREATED);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> updateCustomer(@PathVariable String id, @RequestBody CustomerDTO customerDTO) {
        return new ResponseEntity<>(customerService.updateCustomer(id,customerDTO), HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> deleteCustomer(@PathVariable String id) {
        return new ResponseEntity<>(customerService.deleteCustomer(id), HttpStatus.OK);
    }
}
