package org.example.phishing.protector.app.infrastructure.google.webrisk.rest;

import com.google.cloud.webrisk.v1.WebRiskServiceClient;
import com.google.webrisk.v1.SearchUrisRequest;
import com.google.webrisk.v1.SearchUrisResponse;
import com.google.webrisk.v1.ThreatType;
import lombok.RequiredArgsConstructor;
import org.example.phishing.protector.app.domain.ports.UrlValidatorPort;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.EnumSet;

@Component
@RequiredArgsConstructor
public class GoogleWebRiskClientHttp implements UrlValidatorPort {

    private final WebRiskServiceClient client;

    @Cacheable(value = "webrisk", key = "#url")
    public Boolean isPhishing(String url) {
        EnumSet<ThreatType> threats = EnumSet.of(
                ThreatType.MALWARE,
                ThreatType.SOCIAL_ENGINEERING,
                ThreatType.UNWANTED_SOFTWARE
        );

        SearchUrisRequest request = SearchUrisRequest.newBuilder()
                .setUri(url)
                .addAllThreatTypes(threats)
                .build();


        SearchUrisResponse response = client.searchUris(request);

        return response.hasThreat();
    }
}
