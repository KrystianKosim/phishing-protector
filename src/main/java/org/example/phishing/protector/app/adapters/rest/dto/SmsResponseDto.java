package org.example.phishing.protector.app.adapters.rest.dto;

import org.example.phishing.protector.app.domain.model.SmsMessage;

public record SmsResponseDto(
        String sender,
        String recipient,
        String message,
        SmsMessage.SmsMessageStatus isPhishing,
        String cause
) {
}
