package com.castaneda.mcs_customers.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum DocumentType {
    DNI,
    CARNET_EXTRANJERIA,
    RUC,
    UNKNOWN;

    @JsonCreator
    public static DocumentType fromString(String value) {
        if (value == null) {
            return null;
        }
        try {
            return DocumentType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }
}
