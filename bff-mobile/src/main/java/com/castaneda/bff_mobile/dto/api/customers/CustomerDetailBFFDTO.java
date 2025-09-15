package com.castaneda.bff_mobile.dto.api.customers;

import lombok.Data;

@Data
public class CustomerDetailBFFDTO {
    private String firstname;
    private String middlename;
    private String patSurname;
    private String matSurname;
    private String documentType;
    private String documentNumber;
    private String address;
    private String phone;
}
