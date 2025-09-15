package com.castaneda.mcs_customers.repository;

import com.castaneda.mcs_customers.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, String> {
    Optional<Customer> findByDocumentNumber(String documentNumber);
}
