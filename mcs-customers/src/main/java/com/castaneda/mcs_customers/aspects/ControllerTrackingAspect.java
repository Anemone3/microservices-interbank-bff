package com.castaneda.mcs_customers.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ControllerTrackingAspect {
    
    private static final Logger logger = LoggerFactory.getLogger(ControllerTrackingAspect.class);
    
    @Around("@within(org.springframework.web.bind.annotation.RestController)")
    public Object trackControllerMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        String trackId = MDC.get("track-id");
        String bffSource = MDC.get("bffSource");
        String methodName = joinPoint.getSignature().getName();
        
        String serviceName = "MCS-CUSTOMERS";
        
        logger.info("[{}] [SERVICE: {}] [FROM-BFF: {}] Iniciando método: {}", 
                   trackId, serviceName, bffSource, methodName);
        
        long startTime = System.currentTimeMillis();
        
        try {
            Object result = joinPoint.proceed();
            long duration = System.currentTimeMillis() - startTime;
            logger.info("[{}] [SERVICE: {}] [FROM-BFF: {}] Método {} completado en {}ms", 
                       trackId, serviceName, bffSource, methodName, duration);
            return result;
        } catch (Exception e) {
            long duration = System.currentTimeMillis() - startTime;
            logger.error("[{}] [SERVICE: {}] [FROM-BFF: {}] Error en método {} después de {}ms: {}", 
                        trackId, serviceName, bffSource, methodName, duration, e.getMessage());
            throw e;
        }
    }
}