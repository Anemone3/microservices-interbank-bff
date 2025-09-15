package com.castaneda.bff_web.dto.external.customers;

import lombok.Data;

@Data
public class CustomerResponseDTO {
    private String id;
    private String firstName;
    private String middleName;
    private String patSurname;
    private String matSurname;
    private String documentType;
    private String documentNumber;
    private String status;
    private String registrationDate;
    private String email;
    private String phone;
    private String address;
}
