package com.castaneda.mcs_customers.dto;

import com.castaneda.mcs_customers.enums.DocumentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDTO {

    private String firstName;

    private String middleName;


    private String patSurname;


    private String matSurname;

    private DocumentType documentType;

    private String documentNumber;

    private String email;

    private String phone;

    private String address;

}
