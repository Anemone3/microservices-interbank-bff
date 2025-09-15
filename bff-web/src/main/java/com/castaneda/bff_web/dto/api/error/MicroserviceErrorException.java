package com.castaneda.bff_web.dto.api.error;


import com.castaneda.bff_web.dto.external.error.ErrorResponseDTO;

import java.time.Instant;



public class MicroserviceErrorException extends RuntimeException {
    private final ErrorResponseDTO errorDto;
    
    public MicroserviceErrorException(ErrorResponseDTO errorDto) {
        super(errorDto.getMessage());
        this.errorDto = errorDto;
    }
    
    public ErrorResponseDTO getErrorDto() {
        return errorDto;
    }
    
    public Instant getTimeStamp() {
        return errorDto.getTimeStamp();
    }
    
    public int getStatus() {
        return errorDto.getStatus();
    }
    
    public String getPath() {
        return errorDto.getPath();
    }
}