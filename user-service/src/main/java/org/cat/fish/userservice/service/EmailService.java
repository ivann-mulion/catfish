package org.cat.fish.userservice.service;

import org.cat.fish.userservice.constant.AppConstant;
import org.cat.fish.userservice.model.dto.request.EmailDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class EmailService {
    private final WebClient.Builder webClientBuilder;

    @Autowired
    public EmailService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public String sendEmail(EmailDetails emailDetails) {
        return webClientBuilder.baseUrl(AppConstant.DiscoveredDomainApi.API_GATEWAY_HOST).build()
                .post()
                .uri("/api/email/sendMail")
                .bodyValue(emailDetails)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
