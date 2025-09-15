package com.castaneda.mcs_finance_products.services;

import com.castaneda.mcs_finance_products.domain.ProductFinance;
import com.castaneda.mcs_finance_products.dto.ProductFinanceDTO;
import com.castaneda.mcs_finance_products.dto.ProductFinanceResponseDTO;
import com.castaneda.mcs_finance_products.exceptions.base.ProductFinanceNotFoundException;
import com.castaneda.mcs_finance_products.exceptions.operations.ProductFinanceAlreadyExistsException;
import com.castaneda.mcs_finance_products.mapper.ProductFinanceMapper;
import com.castaneda.mcs_finance_products.repository.ProductFinanceRepository;
import com.castaneda.mcs_finance_products.services.impl.ProductFinanceServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests para ProductFinanceServiceImpl")
class ProductFinanceServiceImplTest {

    @Mock
    private ProductFinanceRepository productFinanceRepository;
    
    @Mock
    private ProductFinanceMapper productFinanceMapper;
    
    @InjectMocks
    private ProductFinanceServiceImpl productFinanceService;
    
    private ProductFinance productFinance;
    private ProductFinanceDTO productFinanceDTO;
    private ProductFinanceResponseDTO productFinanceResponseDTO;
    
    @BeforeEach
    void setUp() {
        productFinance = new ProductFinance();
        productFinance.setId("product-1");
        productFinance.setProductType("CUENTA_AHORROS");
        productFinance.setDescription("Cuenta de Ahorros");
        productFinance.setInterestRate(new BigDecimal("2.5"));
        
        productFinanceDTO = new ProductFinanceDTO();
        productFinanceDTO.setProductType("CUENTA_AHORROS");
        productFinanceDTO.setDescription("Cuenta de Ahorros");
        productFinanceDTO.setInterestRate(new BigDecimal("2.5"));
        
        productFinanceResponseDTO = new ProductFinanceResponseDTO();
        productFinanceResponseDTO.setProductId("product-1");
        productFinanceResponseDTO.setProductType("CUENTA_AHORROS");
        productFinanceResponseDTO.setDescription("Cuenta de Ahorros");
        productFinanceResponseDTO.setInterestRate(new BigDecimal("2.5"));
    }
    
    @Test
    @DisplayName("Debe obtener producto financiero por ID exitosamente")
    void shouldGetProductFinanceByIdSuccessfully() {
        // Given
        when(productFinanceRepository.findById("product-1")).thenReturn(Optional.of(productFinance));
        when(productFinanceMapper.toProductFinanceResponseDTO(productFinance)).thenReturn(productFinanceResponseDTO);
        
        // When
        ProductFinanceResponseDTO result = productFinanceService.getProductFinanceById("product-1");
        
        // Then
        assertNotNull(result);
        assertEquals("product-1", result.getProductId());
        assertEquals("CUENTA_AHORROS", result.getProductType());
        verify(productFinanceRepository).findById("product-1");
        verify(productFinanceMapper).toProductFinanceResponseDTO(productFinance);
    }
    
    @Test
    @DisplayName("Debe lanzar ProductFinanceNotFoundException cuando producto no existe")
    void shouldThrowProductFinanceNotFoundExceptionWhenProductNotExists() {
        // Given
        when(productFinanceRepository.findById("product-inexistente")).thenReturn(Optional.empty());
        
        // When & Then
        ProductFinanceNotFoundException exception = assertThrows(
                ProductFinanceNotFoundException.class,
                () -> productFinanceService.getProductFinanceById("product-inexistente")
        );
        
        assertTrue(exception.getMessage().contains("product-inexistente"));
        verify(productFinanceRepository).findById("product-inexistente");
        verify(productFinanceMapper, never()).toProductFinanceResponseDTO(any());
    }
    
    @Test
    @DisplayName("Debe obtener todos los productos financieros")
    void shouldGetAllProductFinances() {
        // Given
        List<ProductFinance> productFinances = Arrays.asList(productFinance);
        when(productFinanceRepository.findAll()).thenReturn(productFinances);
        when(productFinanceMapper.toProductFinanceResponseDTO(productFinance)).thenReturn(productFinanceResponseDTO);
        
        // When
        List<ProductFinanceResponseDTO> result = productFinanceService.getProductsFinances();
        
        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("CUENTA_AHORROS", result.get(0).getProductType());
        verify(productFinanceRepository).findAll();
    }
    
    @Test
    @DisplayName("Debe actualizar producto financiero exitosamente")
    void shouldUpdateProductFinanceSuccessfully() {
        // Given
        when(productFinanceRepository.findById("product-1")).thenReturn(Optional.of(productFinance));
        when(productFinanceRepository.save(productFinance)).thenReturn(productFinance);
        when(productFinanceMapper.toProductFinanceResponseDTO(productFinance)).thenReturn(productFinanceResponseDTO);
        
        // When
        ProductFinanceResponseDTO result = productFinanceService.updateProductsFinance("product-1", productFinanceDTO);
        
        // Then
        assertNotNull(result);
        assertEquals("product-1", result.getProductId());
        verify(productFinanceRepository).findById("product-1");
        verify(productFinanceMapper).updateProductFromDto(productFinanceDTO, productFinance);
        verify(productFinanceRepository).save(productFinance);
    }
    
    @Test
    @DisplayName("Debe lanzar ProductFinanceNotFoundException al actualizar producto inexistente")
    void shouldThrowProductFinanceNotFoundExceptionWhenUpdatingNonExistentProduct() {
        // Given
        when(productFinanceRepository.findById("product-inexistente")).thenReturn(Optional.empty());
        
        // When & Then
        ProductFinanceNotFoundException exception = assertThrows(
                ProductFinanceNotFoundException.class,
                () -> productFinanceService.updateProductsFinance("product-inexistente", productFinanceDTO)
        );
        
        assertTrue(exception.getMessage().contains("product-inexistente"));
        verify(productFinanceRepository).findById("product-inexistente");
        verify(productFinanceRepository, never()).save(any());
    }
    
    @Test
    @DisplayName("Debe agregar nuevo producto financiero exitosamente")
    void shouldAddProductFinanceSuccessfully() {
        // Given
        when(productFinanceRepository.findByProductType("CUENTA_AHORROS")).thenReturn(Optional.empty());
        when(productFinanceMapper.toProductFinance(productFinanceDTO)).thenReturn(productFinance);
        when(productFinanceRepository.save(productFinance)).thenReturn(productFinance);
        when(productFinanceMapper.toProductFinanceResponseDTO(productFinance)).thenReturn(productFinanceResponseDTO);
        
        // When
        ProductFinanceResponseDTO result = productFinanceService.addProductsFinance(productFinanceDTO);
        
        // Then
        assertNotNull(result);
        assertEquals("CUENTA_AHORROS", result.getProductType());
        verify(productFinanceRepository).findByProductType("CUENTA_AHORROS");
        verify(productFinanceRepository).save(productFinance);
    }
    
    @Test
    @DisplayName("Debe lanzar ProductFinanceAlreadyExistsException cuando tipo de producto ya existe")
    void shouldThrowProductFinanceAlreadyExistsExceptionWhenProductTypeExists() {
        // Given
        when(productFinanceRepository.findByProductType("CUENTA_AHORROS")).thenReturn(Optional.of(productFinance));
        
        // When & Then
        ProductFinanceAlreadyExistsException exception = assertThrows(
                ProductFinanceAlreadyExistsException.class,
                () -> productFinanceService.addProductsFinance(productFinanceDTO)
        );
        
        assertTrue(exception.getMessage().contains("CUENTA_AHORROS"));
        verify(productFinanceRepository).findByProductType("CUENTA_AHORROS");
        verify(productFinanceRepository, never()).save(any());
    }
}