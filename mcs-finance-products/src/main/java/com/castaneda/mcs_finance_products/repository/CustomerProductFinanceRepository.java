package com.castaneda.mcs_finance_products.repository;

import com.castaneda.mcs_finance_products.domain.CustomerProductFinance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerProductFinanceRepository extends JpaRepository<CustomerProductFinance, String> {
    Optional<CustomerProductFinance> findCustomerProductFinanceByCustomerIdAndProduct_Id(String customerId, String product_id);

    Optional<CustomerProductFinance> findByAccountNumber(String accountNumber);
    List<CustomerProductFinance> findAllByCustomerId(String customerId);
}
