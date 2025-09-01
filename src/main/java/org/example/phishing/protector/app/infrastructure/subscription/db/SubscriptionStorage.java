package org.example.phishing.protector.app.infrastructure.subscription.db;

import lombok.RequiredArgsConstructor;
import org.example.phishing.protector.app.domain.ports.SubscriptionStoragePort;
import org.example.phishing.protector.app.infrastructure.subscription.db.entity.SubscriptionEntity;
import org.example.phishing.protector.app.infrastructure.subscription.db.entity.SubscriptionEntityMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SubscriptionStorage implements SubscriptionStoragePort {

    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionEntityMapper subscriptionEntityMapper;

    @Override
    public void saveSubscriptionStatus(String number, boolean status) {
        SubscriptionEntity subscriptionEntity = subscriptionEntityMapper.toEntity(number, status);

        subscriptionRepository.save(subscriptionEntity);
    }

    @Override
    public Boolean isSubscriptionActive(String recipient) {
        return subscriptionRepository.findById(recipient)
                .map(SubscriptionEntity::getStatus)
                .orElse(false);
    }
}
