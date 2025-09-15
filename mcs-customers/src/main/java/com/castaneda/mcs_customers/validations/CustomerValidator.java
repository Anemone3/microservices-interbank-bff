package com.castaneda.mcs_customers.validations;

import com.castaneda.mcs_customers.dto.CustomerDTO;
import com.castaneda.mcs_customers.enums.DocumentType;
import com.castaneda.mcs_customers.enums.StatusCustomer;
import com.castaneda.mcs_customers.exceptions.ForbiddenStatusOperationException;
import com.castaneda.mcs_customers.exceptions.InvalidDocumentNumberException;
import com.castaneda.mcs_customers.exceptions.InvalidEmailException;
import com.castaneda.mcs_customers.exceptions.InvalidPhoneCharacterException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CustomerValidator {

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private static final Pattern VALID_PHONE_DIGITS = Pattern.compile("^[0-9]{9}$");

    public static void validateFields(CustomerDTO customer) {
        validatePhone(customer);
        validateEmail(customer);
        validateDocuments(customer);
    }

    private static void validatePhone(CustomerDTO customer) {

        if(customer.getPhone() == null || customer.getPhone().isEmpty()) {
            throw new InvalidPhoneCharacterException("El celular es requerido");
        }

        Matcher matcher = VALID_PHONE_DIGITS.matcher(customer.getPhone());
        if(!matcher.matches()) {
            throw new InvalidPhoneCharacterException("El número de celular contiene carácteres no válidos");
        }
    }

    public static void validateEmail(CustomerDTO customer) {
        if(customer.getEmail() == null || customer.getEmail().isEmpty()) {
            throw new InvalidEmailException("El correo es requerido");
        }
        
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(customer.getEmail());
        if(!matcher.matches()) {
            throw new InvalidEmailException("El correo no es válido");
        }
    }

    public static void validateDocuments(CustomerDTO customer) {
        if(customer.getDocumentType() == null || customer.getDocumentNumber() == null) {
            throw new InvalidDocumentNumberException("El documento es requerido");
        }

        if(customer.getDocumentType() == DocumentType.UNKNOWN) {
            throw new InvalidDocumentNumberException("El tipo de documento no es válido.");
        }

        switch (customer.getDocumentType()) {
            case DNI -> {
                if(customer.getDocumentNumber().length() != 8) {
                    throw new InvalidDocumentNumberException("El DNI debe tener 8 digitos");
                }
                break;
            }
            case RUC -> {
                if(customer.getDocumentNumber().length() != 11) {
                    throw new InvalidDocumentNumberException("El RUC debe tener 11 digitos");
                }
                break;
            }
            case CARNET_EXTRANJERIA -> {
                if(customer.getDocumentNumber().length() != 9) {
                    throw new InvalidDocumentNumberException("El Carnet Extranjero debe tener 9 digitos");
                }
                break;
            }
            default -> throw new InvalidDocumentNumberException("Invalido tipo de documento: " + customer.getDocumentType());
        }
    }

    public static void validateStatusCustomer(StatusCustomer statusCustomer) {
        String status = statusCustomer.name();
        if(status.equalsIgnoreCase("BLOQUEADO")) {
            throw new ForbiddenStatusOperationException("El cliente no puede hacer operaciones con estado bloqueado");
        }
    }
}
