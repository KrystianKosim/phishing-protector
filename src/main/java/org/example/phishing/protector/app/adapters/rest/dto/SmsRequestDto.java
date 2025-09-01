package org.example.phishing.protector.app.adapters.rest.dto;

import jakarta.validation.constraints.NotBlank;

public record SmsRequestDto(
        @NotBlank String sender,
        @NotBlank String recipient,
        @NotBlank String message
) {}
