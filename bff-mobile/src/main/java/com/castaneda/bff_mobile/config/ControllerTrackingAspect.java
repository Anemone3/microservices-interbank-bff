package com.castaneda.bff_mobile.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.util.context.ContextView;

@Aspect
@Component
@Slf4j
public class ControllerTrackingAspect {

    @Around("@within(org.springframework.web.bind.annotation.RestController)")
    public Object trackControllerMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String serviceName = "BFF-MOBILE";
        
        try {
            Object result = joinPoint.proceed();
            
            if (result instanceof Mono) {
                return ((Mono<?>) result)
                    .doOnEach(signal -> {
                        if (signal.isOnNext() || signal.isOnError()) {
                            ContextView ctx = signal.getContextView();
                            String trackId = ctx.getOrDefault("trackId", "unknown");
                            
                            MDC.put("track-id", trackId);
                            
                            if (signal.isOnNext()) {
                                log.info("[TRACK-{}] [{}] EXITO: {}.{}", 
                                        trackId, serviceName, className, methodName);
                            } else if (signal.isOnError()) {
                                Throwable error = signal.getThrowable();
                                log.error("[TRACK-{}] [{}] ERROR: {}.{} - Error: {}", 
                                        trackId, serviceName, className, methodName, 
                                        error != null ? error.getMessage() : "Unknown error");
                            }
                            
                            MDC.clear();
                        }
                    })
                    .contextWrite(ctx -> {
                        String trackId = ctx.getOrDefault("trackId", generateFallbackTrackId());
                        
                        MDC.put("track-id", trackId);
                        log.info("[TRACK-{}] [{}] INICIO: {}.{}", 
                                trackId, serviceName, className, methodName);
                        MDC.clear();
                        
                        return ctx;
                    });
            } else {
                String trackId = generateFallbackTrackId();
                MDC.put("track-id", trackId);
                
                log.info("[TRACK-{}] [{}] INICIO: {}.{}", 
                        trackId, serviceName, className, methodName);
                log.info("[TRACK-{}] [{}] ÉXITO: {}.{}", 
                        trackId, serviceName, className, methodName);
                        
                MDC.clear();
                return result;
            }
        } catch (Exception e) {
            String trackId = generateFallbackTrackId();
            MDC.put("track-id", trackId);
            
            log.error("[TRACK-{}] [{}] ERROR: {}.{} - Error: {}", 
                    trackId, serviceName, className, methodName, e.getMessage());
                    
            MDC.clear();
            throw e;
        }
    }
    
    private String generateFallbackTrackId() {
        return "BFF-MOBILE-" + System.currentTimeMillis() + "-" + 
               Integer.toHexString((int)(Math.random() * 0x10000)).toUpperCase();
    }
}