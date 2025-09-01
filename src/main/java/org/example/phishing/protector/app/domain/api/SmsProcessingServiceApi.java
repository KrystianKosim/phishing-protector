package org.example.phishing.protector.app.domain.api;

import org.example.phishing.protector.app.domain.model.SmsMessage;

public interface SmsProcessingServiceApi {
    SmsMessage processIncomingSmsMessage(SmsMessage smsMessage);
}
