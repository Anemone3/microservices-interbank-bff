package com.castaneda.mcs_finance_products.repository;

import com.castaneda.mcs_finance_products.domain.ProductFinance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductFinanceRepository extends JpaRepository<ProductFinance, String> {
    Optional<ProductFinance> findByProductType(String productType);
}
