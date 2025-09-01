package org.example.phishing.protector.app.adapters.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.phishing.protector.app.adapters.rest.dto.SmsMessageMapper;
import org.example.phishing.protector.app.adapters.rest.dto.SmsRequestDto;
import org.example.phishing.protector.app.adapters.rest.dto.SmsResponseDto;
import org.example.phishing.protector.app.domain.api.SmsProcessingServiceApi;
import org.example.phishing.protector.app.domain.exception.IncorrectManagementMessageException;
import org.example.phishing.protector.app.domain.model.SmsMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sms")
@RequiredArgsConstructor
@Validated
public class SmsController {
    private final SmsProcessingServiceApi service;
    private final SmsMessageMapper mapper;

    @PostMapping
    public ResponseEntity<SmsResponseDto> receive(@Valid @RequestBody SmsRequestDto req) {
        SmsMessage smsMessage = service.processIncomingSmsMessage(mapper.mapToDomain(req));

        SmsResponseDto smsRequestDto = mapper.mapToResponse(smsMessage);
        return ResponseEntity.ok().body(smsRequestDto);
    }

    @ExceptionHandler(IncorrectManagementMessageException.class)
    ResponseEntity<String> handleIncorrectManagementMessageException(IncorrectManagementMessageException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
