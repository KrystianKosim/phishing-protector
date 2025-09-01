package org.example.phishing.protector.app.infrastructure.subscription.db;

import org.example.phishing.protector.app.infrastructure.subscription.db.entity.SubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, String> {
}
