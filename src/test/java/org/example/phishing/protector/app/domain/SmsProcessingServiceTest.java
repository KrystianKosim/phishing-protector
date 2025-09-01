package org.example.phishing.protector.app.domain;

import org.example.phishing.protector.app.domain.exception.IncorrectManagementMessageException;
import org.example.phishing.protector.app.domain.model.SmsMessage;
import org.example.phishing.protector.app.domain.ports.SubscriptionStoragePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SmsProcessingServiceTest {

    @Mock
    private SubscriptionStoragePort subscriptionStoragePort;

    @Mock
    private PhishingProtectorService phishingProtectorService;

    private SmsProcessingService smsProcessingService;

    private String subscriptionManagementSmsNumber = "12345";

    @BeforeEach
    void setUp() {
        smsProcessingService = new SmsProcessingService(subscriptionManagementSmsNumber, subscriptionStoragePort, phishingProtectorService);
    }

    @Test
    void shouldUpdateSubscriptionStatusToActiveWhenStartMessageReceived() {
        // Given
        SmsMessage smsMessage = SmsMessage.builder().sender("123")
                .recipient(subscriptionManagementSmsNumber)
                .message(SmsProcessingService.START).build();

        // When
        SmsMessage result = smsProcessingService.processIncomingSmsMessage(smsMessage);

        // Then
        verify(subscriptionStoragePort).saveSubscriptionStatus("123", true);
        assertEquals(SmsMessage.SmsMessageStatus.OK, result.getIsPhishing());
    }

    @Test
    void shouldUpdateSubscriptionStatusToInactiveWhenStopMessageReceived() {
        // Given
        SmsMessage smsMessage = SmsMessage.builder().sender("123")
                .recipient(subscriptionManagementSmsNumber)
                .message(SmsProcessingService.STOP).build();

        // When
        SmsMessage result = smsProcessingService.processIncomingSmsMessage(smsMessage);

        // Then
        verify(subscriptionStoragePort).saveSubscriptionStatus("123", false);
        assertEquals(SmsMessage.SmsMessageStatus.OK, result.getIsPhishing());
    }

    @Test
    void shouldThrowExceptionForInvalidManagementMessage() {
        // Given
        SmsMessage smsMessage = SmsMessage.builder().sender("123")
                .recipient(subscriptionManagementSmsNumber)
                .message("INVALID").build();

        // When & Then
        assertThrows(IncorrectManagementMessageException.class, () -> smsProcessingService.processIncomingSmsMessage(smsMessage));
    }

    @Test
    void shouldSetNotSubscribedStatusWhenSubscriptionIsInactive() {
        // Given
        SmsMessage smsMessage = SmsMessage.builder().sender("123")
                .recipient("456")
                .message("Test message").build();
        when(subscriptionStoragePort.isSubscriptionActive("456")).thenReturn(false);

        // When
        SmsMessage result = smsProcessingService.processIncomingSmsMessage(smsMessage);

        // Then
        assertEquals(SmsMessage.SmsMessageStatus.NOT_SUBSCRIBED, result.getIsPhishing());
        verifyNoInteractions(phishingProtectorService);
    }

    @Test
    void shouldSetPhishingStatusWhenPhishingUrlsAreDetected() {
        // Given
        SmsMessage smsMessage = SmsMessage.builder().sender("123")
                .recipient("456")
                .message("Check this link: http://phishing.com").build();
        when(subscriptionStoragePort.isSubscriptionActive("456")).thenReturn(true);
        when(phishingProtectorService.getPhishingUrls("Check this link: http://phishing.com"))
                .thenReturn(List.of("http://phishing.com"));

        // When
        SmsMessage result = smsProcessingService.processIncomingSmsMessage(smsMessage);

        // Then
        assertEquals(SmsMessage.SmsMessageStatus.PHISHING, result.getIsPhishing());
        assertEquals(List.of("http://phishing.com"), result.getPhishngUrls());
    }

    @Test
    void shouldSetOkStatusWhenNoPhishingUrlsAreDetected() {
        // Given
        SmsMessage smsMessage = SmsMessage.builder().sender("123")
                .recipient("456")
                .message("Check this link: http://safe.com").build();
        when(subscriptionStoragePort.isSubscriptionActive("456")).thenReturn(true);
        when(phishingProtectorService.getPhishingUrls("Check this link: http://safe.com"))
                .thenReturn(List.of());

        // When
        SmsMessage result = smsProcessingService.processIncomingSmsMessage(smsMessage);

        // Then
        assertEquals(SmsMessage.SmsMessageStatus.OK, result.getIsPhishing());
        assertNull(result.getPhishngUrls());
    }
}