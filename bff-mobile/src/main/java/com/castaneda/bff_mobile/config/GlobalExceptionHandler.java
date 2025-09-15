package com.castaneda.bff_mobile.config;

import com.castaneda.bff_mobile.dto.api.error.MicroserviceErrorException;
import com.castaneda.bff_mobile.dto.external.error.ErrorResponseDTO;
import com.castaneda.bff_mobile.exceptions.EncryptionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.resource.NoResourceFoundException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(EncryptionException.class)
    public Mono<ErrorResponseDTO> noResourceFoundException(final EncryptionException e,ServerWebExchange exchange) {
        ErrorResponseDTO errorResponse = ErrorResponseDTO.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .path(exchange.getRequest().getPath().value())
                .build();
        return Mono.just(errorResponse);
    }
    @ExceptionHandler(NoResourceFoundException.class)
    public Mono<ErrorResponseDTO> noResourceFoundException(final NoResourceFoundException e,ServerWebExchange exchange) {
        ErrorResponseDTO errorResponse = ErrorResponseDTO.builder()
                .status(e.getStatusCode().value())
                .message(e.getMessage())
                .path(exchange.getRequest().getPath().value())
                .build();
        return Mono.just(errorResponse);
    }

    @ExceptionHandler(MicroserviceErrorException.class)
    public Mono<ResponseEntity<ErrorResponseDTO>> handleMicroserviceError(
            MicroserviceErrorException ex, 
            ServerWebExchange exchange) {
        
        log.error("Error de microservicio: {} - Status: {} - Path: {}", 
                ex.getErrorDto().getMessage(), ex.getErrorDto().getStatus(), ex.getPath());
        
        ErrorResponseDTO errorResponse = ErrorResponseDTO.builder()
                .status(ex.getErrorDto().getStatus())
                .message(ex.getErrorDto().getMessage()) 
                .path(exchange.getRequest().getPath().value())
                .build();
        
        HttpStatus httpStatus = HttpStatus.valueOf(ex.getStatus());
        
        return Mono.just(ResponseEntity.status(httpStatus).body(errorResponse));
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<ErrorResponseDTO>> handleGenericError(
            Exception ex, 
            ServerWebExchange exchange) {
        
        log.error("Error interno del servidor: {}", ex.getMessage(), ex);
        
        ErrorResponseDTO errorResponse = ErrorResponseDTO.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Error interno del servidor")
                .path(exchange.getRequest().getPath().value())
                .build();
        
        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponse));
    }
}