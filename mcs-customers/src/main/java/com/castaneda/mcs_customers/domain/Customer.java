package com.castaneda.mcs_customers.domain;

import com.castaneda.mcs_customers.enums.DocumentType;
import com.castaneda.mcs_customers.enums.StatusCustomer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "t_customer")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer  {



    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id",updatable = false,nullable = false,length = 60)
    private String id;

    @Column(name = "first_name",nullable = false,length = 50)
    private String firstName;

    @Column(name = "middle_name",length = 50)
    private String middleName;

    @Column(name = "pat_surname",nullable = false,length = 50)
    private String patSurname;

    @Column(name = "mat_surname",nullable = false,length = 50)
    private String matSurname;

    @Enumerated(EnumType.STRING)
    @Column(name = "document_type",nullable = false)
    private DocumentType documentType;

    @Column(name = "document_number",nullable = false,unique = true,length = 15)
    private String documentNumber;

    @Column(name = "email",length = 50,nullable = false)
    private String email;

    @Column(name = "phone",nullable = false,length = 9)
    private String phone;

    @Column(name = "address",nullable = false,length = 50)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name="status_account")
    private StatusCustomer status;

    @Column(name = "registration_date")
    private final Instant registrationDate = Instant.now();

}
