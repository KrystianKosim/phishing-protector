package org.example.phishing.protector.app.infrastructure.google.webrisk.rest.config;

import com.google.api.gax.core.NoCredentialsProvider;
import com.google.api.gax.rpc.FixedHeaderProvider;
import com.google.cloud.webrisk.v1.WebRiskServiceClient;
import com.google.cloud.webrisk.v1.WebRiskServiceSettings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebRiskConfig {

    @Value("${webrisk.api-key:}")
    private String apiKey;

    @Bean
    public WebRiskServiceClient webRiskServiceClient() throws Exception {
        if (apiKey != null && !apiKey.isBlank()) {
            WebRiskServiceSettings settings = WebRiskServiceSettings.newHttpJsonBuilder()
                    .setCredentialsProvider(NoCredentialsProvider.create())
                    .setHeaderProvider(FixedHeaderProvider.create("X-Goog-Api-Key", apiKey))
                    .build();
            return WebRiskServiceClient.create(settings);
        } else {
            return WebRiskServiceClient.create();
        }
    }
}