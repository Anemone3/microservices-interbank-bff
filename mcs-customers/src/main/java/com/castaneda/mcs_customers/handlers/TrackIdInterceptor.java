package com.castaneda.mcs_customers.handlers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class TrackIdInterceptor implements HandlerInterceptor {
    
    private static final String TRACK_ID_HEADER = "X-Track-Id";
    private static final String BFF_SOURCE_HEADER = "X-BFF-Source";
    private static final String TRACK_ID_KEY = "track-id";
    private static final String BFF_SOURCE_KEY = "bffSource";
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String trackId = request.getHeader(TRACK_ID_HEADER);
        String bffSource = request.getHeader(BFF_SOURCE_HEADER);
        
        // validar
        if (trackId == null || trackId.trim().isEmpty()) {
            sendErrorResponse(response, "Missing X-Track-Id header", "Track ID is required for all requests");
            return false;
        }
        
        if (bffSource == null || bffSource.trim().isEmpty()) {
            sendErrorResponse(response, "Missing X-BFF-Source header", "BFF source identification is required");
            return false;
        }
        
        if (!isValidTrackIdFormat(trackId, bffSource)) {
            sendErrorResponse(response, "Invalid Track-ID format", "Track ID must follow BFF-TIMESTAMP-UUID format");
            return false;
        }
        
        // agregar al MDC para logging
        MDC.put(TRACK_ID_KEY, trackId);  
        MDC.put(BFF_SOURCE_KEY, bffSource);
        
        return true;
    }
    
    private boolean isValidTrackIdFormat(String trackId, String bffSource) {
        return trackId.toUpperCase().startsWith(bffSource.toUpperCase() + "-");
    }
    
    private void sendErrorResponse(HttpServletResponse response, String error, String message) throws Exception {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.getWriter().write(
            String.format("{\"error\": \"%s\", \"message\": \"%s\"}", error, message)
        );
    }
    
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        MDC.remove(TRACK_ID_KEY);
        MDC.remove(BFF_SOURCE_KEY);
    }
}