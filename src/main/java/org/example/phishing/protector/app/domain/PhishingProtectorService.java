package org.example.phishing.protector.app.domain;

import lombok.RequiredArgsConstructor;
import org.example.phishing.protector.app.domain.ports.UrlValidatorPort;
import org.example.phishing.protector.app.utils.UrlExtractor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PhishingProtectorService {

    private final UrlValidatorPort urlValidatorPort;
    public List<String> getPhishingUrls(String message) {
        return UrlExtractor.extractUrls(message).stream()
                .filter(urlValidatorPort::isPhishing)
                .toList();
    }
}
