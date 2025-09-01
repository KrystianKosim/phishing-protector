package org.example.phishing.protector.app.infrastructure.subscription.db.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(name = "subscriptions")
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubscriptionEntity {
    @Id
    private String number;

    private Boolean status;

    @UpdateTimestamp
    private Instant changedAt;
}
