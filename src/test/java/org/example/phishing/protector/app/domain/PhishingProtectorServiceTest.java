package org.example.phishing.protector.app.domain;

import org.example.phishing.protector.app.domain.ports.UrlValidatorPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PhishingProtectorServiceTest {

    @Mock
    private UrlValidatorPort urlValidatorPort;

    @InjectMocks
    private PhishingProtectorService phishingProtectorService;

    @Test
    void shouldReturnPhishingUrlsWhenUrlsArePhishing() {
        // Given
        // Mock UrlValidatorPort behavior
        when(urlValidatorPort.isPhishing("http://phishing.com")).thenReturn(true);
        when(urlValidatorPort.isPhishing("http://safe.com")).thenReturn(false);

        // When
        List<String> phishingUrls = phishingProtectorService.getPhishingUrls("Test abc abc http://phishing.com and http://safe.com");

        // Then
        assertEquals(List.of("http://phishing.com"), phishingUrls);
    }

    @Test
    void shouldReturnEmptyListWhenNoPhishingUrls() {
        // Given
        String message = "Check this link: http://safe.com";

        // Mock UrlValidatorPort behavior
        when(urlValidatorPort.isPhishing("http://safe.com")).thenReturn(false);

        // When
        List<String> phishingUrls = phishingProtectorService.getPhishingUrls(message);

        // Then
        assertEquals(List.of(), phishingUrls);
    }
}