package com.castaneda.bff_web.config;

import com.castaneda.bff_web.dto.api.error.MicroserviceErrorException;
import com.castaneda.bff_web.dto.external.error.ErrorResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Configuration
public class WebClientConfig {

    @Bean
    public ExchangeFilterFunction errorResponseFilter() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            if (clientResponse.statusCode().is4xxClientError() || clientResponse.statusCode().is5xxServerError()) {
                return  clientResponse.bodyToMono(ErrorResponseDTO.class)
                        .flatMap(errorDto -> Mono.error(new MicroserviceErrorException(errorDto)));
            }
            return Mono.just(clientResponse);
        });
    }
    
    @Bean
    public ExchangeFilterFunction trackingFilter() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            return Mono.deferContextual(ctx -> {
                // Obtener track-id y BFF source del contexto reactivo
                String trackId = ctx.getOrDefault("trackId", "");
                String bffSource = ctx.getOrDefault("bffSource", "BFF-WEB");
                
                if (trackId.isEmpty()) {
                    trackId = generateTrackId();
                }
                
                ClientRequest newRequest = ClientRequest.from(clientRequest)
                        .header("X-Track-Id", trackId)
                        .header("X-BFF-Source", bffSource)
                        .build();
                        
                return Mono.just(newRequest);
            });
        });
    }
    
    private String generateTrackId() {
        long timestamp = System.currentTimeMillis();
        String uuid = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return String.format("BFF-WEB-%d-%s", timestamp, uuid);
    }

    @Bean("customerWebClient")
    public WebClient customerWebClient(WebClient.Builder builder, 
                                     @Value("${microservices.customer-service-url}") String url,
                                       List<ExchangeFilterFunction> filters) {
        return builder.baseUrl(url)
                .filters(list -> list.addAll(filters))
                .build();
    }

    @Bean("productWebClient")
    public WebClient productWebClient(WebClient.Builder builder, 
                                    @Value("${microservices.product-finance-service-url}") String url,
                                    List<ExchangeFilterFunction> filters) {
        return builder.baseUrl(url)
                .filters(list -> list.addAll(filters))
                .build();
    }


}