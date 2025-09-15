package com.castaneda.mcs_customers.dto;

import com.castaneda.mcs_customers.enums.StatusCustomer;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({ "id", "firstName", "middleName", "patSurname", "matSurname",
        "documentType", "documentNumber", "email", "phone",
        "address", "status", "registrationDate" })
public class CustomerResponseDTO extends CustomerDTO {
    private String id;

    private StatusCustomer status;

    private Instant registrationDate;
}
