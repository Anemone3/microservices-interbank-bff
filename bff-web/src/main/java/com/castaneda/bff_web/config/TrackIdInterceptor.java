package com.castaneda.bff_web.config;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.util.UUID;


@Component
public class TrackIdInterceptor implements WebFilter {

    public static final String TRACK_ID_HEADER = "X-Track-Id";
    public static final String BFF_SOURCE_HEADER = "X-BFF-Source";
    public static final String TRACK_ID_ATTRIBUTE = "trackId";
    public static final String BFF_SOURCE_ATTRIBUTE = "bffSource";
    private static final String BFF_WEB_ID = "BFF-WEB";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        // generar track-id 
        String trackId = generateTrackId();
        String bffSource = BFF_WEB_ID;

       
        exchange.getAttributes().put(TRACK_ID_ATTRIBUTE, trackId);
        exchange.getAttributes().put(BFF_SOURCE_ATTRIBUTE, bffSource);

        exchange.getResponse().getHeaders().add(TRACK_ID_HEADER, trackId);
        exchange.getResponse().getHeaders().add(BFF_SOURCE_HEADER, bffSource);

        return chain.filter(exchange)
                .contextWrite(Context.of("trackId", trackId, "bffSource", bffSource));
    }

    private String generateTrackId() {
        long timestamp = System.currentTimeMillis();
        String uuid = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return String.format("%s-%d-%s", BFF_WEB_ID, timestamp, uuid);
    }
}