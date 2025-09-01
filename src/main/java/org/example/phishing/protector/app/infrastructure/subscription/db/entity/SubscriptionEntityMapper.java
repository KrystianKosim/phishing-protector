package org.example.phishing.protector.app.infrastructure.subscription.db.entity;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SubscriptionEntityMapper {
    SubscriptionEntity toEntity(String number, boolean status);
}
