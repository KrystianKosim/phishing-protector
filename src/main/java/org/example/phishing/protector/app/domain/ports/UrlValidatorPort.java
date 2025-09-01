package org.example.phishing.protector.app.domain.ports;

public interface UrlValidatorPort {
    Boolean isPhishing(String url);
}
