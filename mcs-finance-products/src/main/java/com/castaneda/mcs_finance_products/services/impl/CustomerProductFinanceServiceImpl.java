package com.castaneda.mcs_finance_products.services.impl;

import com.castaneda.mcs_finance_products.domain.CustomerProductFinance;
import com.castaneda.mcs_finance_products.dto.CustomerProductFinanceDTO;
import com.castaneda.mcs_finance_products.dto.CustomerProductFinanceResponseDTO;
import com.castaneda.mcs_finance_products.exceptions.base.CustomerAccountNotFoundException;
import com.castaneda.mcs_finance_products.exceptions.base.ProductFinanceNotFoundException;
import com.castaneda.mcs_finance_products.exceptions.operations.FinancialOperationNotAllowedException;
import com.castaneda.mcs_finance_products.mapper.CustomerProductFinanceMapper;
import com.castaneda.mcs_finance_products.repository.CustomerProductFinanceRepository;
import com.castaneda.mcs_finance_products.repository.ProductFinanceRepository;
import com.castaneda.mcs_finance_products.services.adapter.ICustomerProductFinanceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service
@AllArgsConstructor
public class CustomerProductFinanceServiceImpl implements ICustomerProductFinanceService {

    private final CustomerProductFinanceRepository customerProductFinanceRepository;
    private final ProductFinanceRepository productFinanceRepository;

    private  final CustomerProductFinanceMapper customerProductFinanceMapper;



    @Override
    public CustomerProductFinanceResponseDTO registerProductFinanceCustomerId(String customerId,CustomerProductFinanceDTO productFinanceDTO) {
        var productFinance = productFinanceRepository.findById(productFinanceDTO.getProductId())
                .orElseThrow(() -> new ProductFinanceNotFoundException(productFinanceDTO.getProductId()));

        var customerFinance = CustomerProductFinance.builder()
                .customerId(customerId)
                .product(productFinance)
                .balance(productFinanceDTO.getBalance())
                .accountNumber(productFinanceDTO.getAccountNumber())
                .build();

        var customerProductFinance = customerProductFinanceRepository.save(customerFinance);
        return customerProductFinanceMapper.toCustomerProductFinanceResponseDTO(customerProductFinance);
    }

    @Override
    public List<CustomerProductFinanceResponseDTO> getAllDetailFinancesByCustomerId(String customerId) {
        return customerProductFinanceRepository.findAllByCustomerId(customerId).stream().map(customerProductFinanceMapper::toCustomerProductFinanceResponseDTO).toList();
    }

    @Override
    public CustomerProductFinanceResponseDTO getProductFinanceByAccountNumber(String accountNumber) {

        var customerProductFinance = customerProductFinanceRepository.findByAccountNumber(accountNumber).orElseThrow(
                () -> new CustomerAccountNotFoundException(accountNumber)
        );
        return customerProductFinanceMapper.toCustomerProductFinanceResponseDTO(customerProductFinance);
    }

    @Override
    public CustomerProductFinanceResponseDTO depositInAccount(String accountNumber,BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
        throw new FinancialOperationNotAllowedException("depósito", "El monto debe ser mayor a cero");
        }
        var customerFinanceProduct = customerProductFinanceRepository.findByAccountNumber(accountNumber).orElseThrow(
                () -> new CustomerAccountNotFoundException(accountNumber)
        );
        BigDecimal currentBalance = customerFinanceProduct.getBalance();
        BigDecimal depositAmount = currentBalance.add(amount);
        customerFinanceProduct.setBalance(depositAmount);
        var customerUpdated = customerProductFinanceRepository.save(customerFinanceProduct);
        return customerProductFinanceMapper.toCustomerProductFinanceResponseDTO(customerUpdated);
    }




}
