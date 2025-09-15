package com.castaneda.mcs_customers.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.castaneda.mcs_customers.handlers.TrackIdInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private TrackIdInterceptor trackIdInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(trackIdInterceptor)
                .addPathPatterns("/customers/**");
    }
}