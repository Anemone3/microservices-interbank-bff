package com.castaneda.mcs_finance_products.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;


@Entity
@Table(name = "t_customer_finance_products")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerProductFinance {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "customer_id",nullable = false)
    private String customerId;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductFinance product;

    @Column(name = "account_number",nullable = false,unique = true)
    private String accountNumber;
    @Column(name = "balance",nullable = false,precision = 10,scale = 2)
    private BigDecimal balance;

    @CreatedDate
    @Column(name = "creation_date")
    private final Timestamp createdDate = Timestamp.from(Instant.now());
}
