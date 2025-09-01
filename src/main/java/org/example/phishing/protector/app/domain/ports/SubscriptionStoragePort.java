package org.example.phishing.protector.app.domain.ports;

public interface SubscriptionStoragePort {
    void saveSubscriptionStatus(String number, boolean status);

    Boolean isSubscriptionActive(String recipient);
}
