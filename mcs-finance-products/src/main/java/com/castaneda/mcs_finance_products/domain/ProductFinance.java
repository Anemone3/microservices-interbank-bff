package com.castaneda.mcs_finance_products.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "t_finance_products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductFinance {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "product_type", nullable = false,length = 50,unique = true)
    private String productType;

    @Column(name = "description")
    private String description;

    @Column(name = "interest_rate", precision = 5,scale = 2)
    private BigDecimal interestRate;

    @OneToMany(mappedBy = "product")
    private List<CustomerProductFinance> customerProductFinances;
}
