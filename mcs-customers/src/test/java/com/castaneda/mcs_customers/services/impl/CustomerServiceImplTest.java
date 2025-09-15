package com.castaneda.mcs_customers.services.impl;

import com.castaneda.mcs_customers.domain.Customer;
import com.castaneda.mcs_customers.dto.CustomerDTO;
import com.castaneda.mcs_customers.dto.CustomerResponseDTO;
import com.castaneda.mcs_customers.enums.DocumentType;
import com.castaneda.mcs_customers.enums.StatusCustomer;
import com.castaneda.mcs_customers.exceptions.CustomerNotFoundException;
import com.castaneda.mcs_customers.exceptions.ForbiddenStatusOperationException;
import com.castaneda.mcs_customers.exceptions.InvalidDocumentNumberException;
import com.castaneda.mcs_customers.exceptions.InvalidEmailException;
import com.castaneda.mcs_customers.exceptions.InvalidPhoneCharacterException;
import com.castaneda.mcs_customers.mapper.CustomerMapper;
import com.castaneda.mcs_customers.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests de CustomerServiceImpl")
class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private Customer customer;
    private CustomerDTO customerDTO;
    private CustomerResponseDTO customerResponseDTO;

    @BeforeEach
    void setUp() {
        customer = Customer.builder()
                .id("customer-1")
                .firstName("Juan")
                .middleName("Carlos")
                .patSurname("Pérez")
                .matSurname("García")
                .documentType(DocumentType.DNI)
                .documentNumber("12345678")
                .email("juan.perez@email.com")
                .phone("987654321")
                .address("Av. Principal 123")
                .status(StatusCustomer.ACTIVO)
                .build();

        customerDTO = CustomerDTO.builder()
                .firstName("Juan")
                .middleName("Carlos")
                .patSurname("Pérez")
                .matSurname("García")
                .documentType(DocumentType.DNI)
                .documentNumber("12345678")
                .email("juan.perez@email.com")
                .phone("987654321")
                .address("Av. Principal 123")
                .build();

        customerResponseDTO = new CustomerResponseDTO();
        customerResponseDTO.setId("customer-1");
        customerResponseDTO.setFirstName("Juan");
        customerResponseDTO.setMiddleName("Carlos");
        customerResponseDTO.setPatSurname("Pérez");
        customerResponseDTO.setMatSurname("García");
        customerResponseDTO.setDocumentType(DocumentType.DNI);
        customerResponseDTO.setDocumentNumber("12345678");
        customerResponseDTO.setEmail("juan.perez@email.com");
        customerResponseDTO.setPhone("987654321");
        customerResponseDTO.setAddress("Av. Principal 123");
        customerResponseDTO.setStatus(StatusCustomer.ACTIVO);
        customerResponseDTO.setRegistrationDate(Instant.now());
    }

    @Test
    @DisplayName("Debe encontrar cliente por ID exitosamente")
    void shouldFindCustomerByIdSuccessfully() {
        // Given
        when(customerRepository.findById("customer-1")).thenReturn(Optional.of(customer));
        when(customerMapper.toCustomerResponseDTO(customer)).thenReturn(customerResponseDTO);

        // When
        CustomerResponseDTO result = customerService.findCustomerById("customer-1");

        // Then
        assertNotNull(result);
        assertEquals("customer-1", result.getId());
        assertEquals("Juan", result.getFirstName());
        assertEquals("12345678", result.getDocumentNumber());
        verify(customerRepository).findById("customer-1");
        verify(customerMapper).toCustomerResponseDTO(customer);
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando cliente no existe por ID")
    void shouldThrowExceptionWhenCustomerNotFoundById() {
        // Given
        when(customerRepository.findById("invalid-id")).thenReturn(Optional.empty());

        // When & Then
        CustomerNotFoundException exception = assertThrows(
                CustomerNotFoundException.class,
                () -> customerService.findCustomerById("invalid-id")
        );
        assertEquals("Customer not found with id: invalid-id", exception.getMessage());
        verify(customerRepository).findById("invalid-id");
        verifyNoInteractions(customerMapper);
    }

    @Test
    @DisplayName("Debe encontrar cliente por número de documento exitosamente")
    void shouldFindCustomerByDocumentSuccessfully() {
        // Given
        when(customerRepository.findByDocumentNumber("12345678")).thenReturn(Optional.of(customer));
        when(customerMapper.toCustomerResponseDTO(customer)).thenReturn(customerResponseDTO);

        // When
        CustomerResponseDTO result = customerService.findCustomerByDocument("12345678");

        // Then
        assertNotNull(result);
        assertEquals("12345678", result.getDocumentNumber());
        verify(customerRepository).findByDocumentNumber("12345678");
        verify(customerMapper).toCustomerResponseDTO(customer);
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando cliente no existe por documento")
    void shouldThrowExceptionWhenCustomerNotFoundByDocument() {
        // Given
        when(customerRepository.findByDocumentNumber("87654321")).thenReturn(Optional.empty());

        // When & Then
        CustomerNotFoundException exception = assertThrows(
                CustomerNotFoundException.class,
                () -> customerService.findCustomerByDocument("87654321")
        );
        assertEquals("Customer not found with documentNumber: 87654321", exception.getMessage());
        verify(customerRepository).findByDocumentNumber("87654321");
        verifyNoInteractions(customerMapper);
    }

    @Test
    @DisplayName("Debe crear cliente exitosamente")
    void shouldCreateCustomerSuccessfully() {
        // Given
        Customer newCustomer = Customer.builder()
                .firstName("Juan")
                .middleName("Carlos")
                .patSurname("Pérez")
                .matSurname("García")
                .documentType(DocumentType.DNI)
                .documentNumber("12345678")
                .email("juan.perez@email.com")
                .phone("987654321")
                .address("Av. Principal 123")
                .status(StatusCustomer.ACTIVO)
                .build();

        when(customerMapper.toCustomer(customerDTO)).thenReturn(newCustomer);
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        when(customerMapper.toCustomerResponseDTO(customer)).thenReturn(customerResponseDTO);

        // When
        CustomerResponseDTO result = customerService.createCustomer(customerDTO);

        // Then
        assertNotNull(result);
        assertEquals("Juan", result.getFirstName());
        assertEquals("12345678", result.getDocumentNumber());
        assertEquals(StatusCustomer.ACTIVO, result.getStatus());
        verify(customerMapper).toCustomer(customerDTO);
        verify(customerRepository).save(any(Customer.class));
        verify(customerMapper).toCustomerResponseDTO(customer);
    }

    @Test
    @DisplayName("Debe lanzar excepción al crear cliente con email inválido")
    void shouldThrowExceptionWhenCreatingCustomerWithInvalidEmail() {
        // Given
        CustomerDTO invalidCustomerDTO = CustomerDTO.builder()
                .firstName("Juan")
                .patSurname("Pérez")
                .matSurname("García")
                .documentType(DocumentType.DNI)
                .documentNumber("12345678")
                .email("email-invalido")
                .phone("987654321")
                .address("Av. Principal 123")
                .build();

        // When & Then
        InvalidEmailException exception = assertThrows(
                InvalidEmailException.class,
                () -> customerService.createCustomer(invalidCustomerDTO)
        );
        assertEquals("El correo no es válido", exception.getMessage());
        verifyNoInteractions(customerRepository);
        verifyNoInteractions(customerMapper);
    }

    @Test
    @DisplayName("Debe lanzar excepción al crear cliente con teléfono inválido")
    void shouldThrowExceptionWhenCreatingCustomerWithInvalidPhone() {
        // Given
        CustomerDTO invalidCustomerDTO = CustomerDTO.builder()
                .firstName("Juan")
                .patSurname("Pérez")
                .matSurname("García")
                .documentType(DocumentType.DNI)
                .documentNumber("12345678")
                .email("juan.perez@email.com")
                .phone("123")
                .address("Av. Principal 123")
                .build();

        // When & Then
        InvalidPhoneCharacterException exception = assertThrows(
                InvalidPhoneCharacterException.class,
                () -> customerService.createCustomer(invalidCustomerDTO)
        );
        assertEquals("El número de celular contiene carácteres no válidos", exception.getMessage());
        verifyNoInteractions(customerRepository);
        verifyNoInteractions(customerMapper);
    }

    @Test
    @DisplayName("Debe lanzar excepción al crear cliente con DNI inválido")
    void shouldThrowExceptionWhenCreatingCustomerWithInvalidDNI() {
        // Given
        CustomerDTO invalidCustomerDTO = CustomerDTO.builder()
                .firstName("Juan")
                .patSurname("Pérez")
                .matSurname("García")
                .documentType(DocumentType.DNI)
                .documentNumber("123")
                .email("juan.perez@email.com")
                .phone("987654321")
                .address("Av. Principal 123")
                .build();

        // When & Then
        InvalidDocumentNumberException exception = assertThrows(
                InvalidDocumentNumberException.class,
                () -> customerService.createCustomer(invalidCustomerDTO)
        );
        assertEquals("El DNI debe tener 8 digitos", exception.getMessage());
        verifyNoInteractions(customerRepository);
        verifyNoInteractions(customerMapper);
    }

    @Test
    @DisplayName("Debe actualizar cliente exitosamente")
    void shouldUpdateCustomerSuccessfully() {
        // Given
        when(customerRepository.findById("customer-1")).thenReturn(Optional.of(customer));
        when(customerRepository.save(customer)).thenReturn(customer);
        when(customerMapper.toCustomerResponseDTO(customer)).thenReturn(customerResponseDTO);

        // When
        CustomerResponseDTO result = customerService.updateCustomer("customer-1", customerDTO);

        // Then
        assertNotNull(result);
        verify(customerRepository).findById("customer-1");
        verify(customerMapper).updateCustomerFromDto(customerDTO, customer);
        verify(customerRepository).save(customer);
        verify(customerMapper).toCustomerResponseDTO(customer);
    }

    @Test
    @DisplayName("Debe lanzar excepción al actualizar cliente bloqueado")
    void shouldThrowExceptionWhenUpdatingBlockedCustomer() {
        // Given
        Customer blockedCustomer = Customer.builder()
                .id("customer-1")
                .firstName("Juan")
                .status(StatusCustomer.BLOQUEADO)
                .build();

        when(customerRepository.findById("customer-1")).thenReturn(Optional.of(blockedCustomer));

        // When & Then
        ForbiddenStatusOperationException exception = assertThrows(
                ForbiddenStatusOperationException.class,
                () -> customerService.updateCustomer("customer-1", customerDTO)
        );
        assertEquals("El cliente no puede hacer operaciones con estado bloqueado", exception.getMessage());
        verify(customerRepository).findById("customer-1");
        verify(customerRepository, never()).save(any());
    }

    @Test
    @DisplayName("Debe eliminar (bloquear) cliente exitosamente")
    void shouldDeleteCustomerSuccessfully() {
        // Given
        when(customerRepository.findById("customer-1")).thenReturn(Optional.of(customer));
        when(customerRepository.save(customer)).thenReturn(customer);
        when(customerMapper.toCustomerResponseDTO(customer)).thenReturn(customerResponseDTO);

        // When
        CustomerResponseDTO result = customerService.deleteCustomer("customer-1");

        // Then
        assertNotNull(result);
        assertEquals(StatusCustomer.BLOQUEADO, customer.getStatus());
        verify(customerRepository).findById("customer-1");
        verify(customerRepository).save(customer);
        verify(customerMapper).toCustomerResponseDTO(customer);
    }

}