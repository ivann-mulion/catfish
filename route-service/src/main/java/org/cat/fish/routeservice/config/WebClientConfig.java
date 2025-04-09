package org.cat.fish.routeservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${media.service.url:http://localhost:8090}")
    private String mediaServiceUrl;

    @Bean
    public WebClient mediaServiceWebClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder
                .baseUrl(mediaServiceUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE)
                .build();
    }
}
