package com.castaneda.bff_mobile.dto.external.customers;

import lombok.Data;

@Data
public class CustomerDTO {
    private String firstName;
    private String middleName;
    private String patSurname;
    private String matSurname;
    private String documentType;
    private String documentNumber;
    private String email;

    private String phone;

    private String address;
}
