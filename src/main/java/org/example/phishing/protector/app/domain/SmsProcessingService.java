package org.example.phishing.protector.app.domain;

import org.example.phishing.protector.app.domain.api.SmsProcessingServiceApi;
import org.example.phishing.protector.app.domain.exception.IncorrectManagementMessageException;
import org.example.phishing.protector.app.domain.model.SmsMessage;
import org.example.phishing.protector.app.domain.ports.SubscriptionStoragePort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SmsProcessingService implements SmsProcessingServiceApi {

    public static final String START = "START";
    public static final String STOP = "STOP";
    private final String subscriptionManagementSmsNumber;
    private final SubscriptionStoragePort subscriptionStoragePort;
    private final PhishingProtectorService phishingProtectorService;

    public SmsProcessingService(@Value("${app.subscription.management.sms-number}") String subscriptionManagementSmsNumber,
                                SubscriptionStoragePort subscriptionStoragePort,
                                PhishingProtectorService phishingProtectorService) {
        this.subscriptionManagementSmsNumber = subscriptionManagementSmsNumber;
        this.subscriptionStoragePort = subscriptionStoragePort;
        this.phishingProtectorService = phishingProtectorService;
    }

    @Override
    public SmsMessage processIncomingSmsMessage(SmsMessage smsMessage) {
        if (smsMessage.getRecipient().equals(subscriptionManagementSmsNumber)) {
            return updateSubscriptionStatus(smsMessage);
        }

        if (!subscriptionStoragePort.isSubscriptionActive(smsMessage.getRecipient())) {
            setPhishingFlag(smsMessage, SmsMessage.SmsMessageStatus.NOT_SUBSCRIBED);
            return smsMessage;
        }

        List<String> phishingUrls = phishingProtectorService.getPhishingUrls(smsMessage.getMessage());

        if (phishingUrls.isEmpty()) {
            setPhishingFlag(smsMessage, SmsMessage.SmsMessageStatus.OK);
            return smsMessage;
        } else {
            setPhishingFlag(smsMessage, SmsMessage.SmsMessageStatus.PHISHING);
            setPhishingUrls(smsMessage, phishingUrls);
        }
        return smsMessage;
    }

    private void setPhishingUrls(SmsMessage smsMessage, List<String> phishingUrls) {
        smsMessage.setPhishngUrls(phishingUrls);
    }

    private SmsMessage updateSubscriptionStatus(SmsMessage smsMessage) {
        switch (smsMessage.getMessage().toUpperCase()) {
            case START -> {
                subscriptionStoragePort.saveSubscriptionStatus(smsMessage.getSender(), true);
                setPhishingFlag(smsMessage, SmsMessage.SmsMessageStatus.OK);
                return smsMessage;
            }
            case STOP -> {
                subscriptionStoragePort.saveSubscriptionStatus(smsMessage.getSender(), false);
                setPhishingFlag(smsMessage, SmsMessage.SmsMessageStatus.OK);
                return smsMessage;
            }
            default -> throw new IncorrectManagementMessageException(smsMessage.getMessage());
        }
    }

    private void setPhishingFlag(SmsMessage smsMessage, SmsMessage.SmsMessageStatus status) {
        smsMessage.setIsPhishing(status);
    }
}
