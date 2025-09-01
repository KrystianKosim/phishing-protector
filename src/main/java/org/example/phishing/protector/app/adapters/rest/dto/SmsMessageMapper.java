package org.example.phishing.protector.app.adapters.rest.dto;

import org.example.phishing.protector.app.domain.model.SmsMessage;
import org.mapstruct.Mapper;

@Mapper
public interface SmsMessageMapper {

    SmsMessage mapToDomain(SmsRequestDto smsRequestDto);

    SmsResponseDto mapToResponse(SmsMessage smsMessage);
}
