package org.example.phishing.protector.app.domain.model;

import lombok.*;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class SmsMessage {
    private String sender;
    private String recipient;
    private String message;
    @Setter
    private SmsMessageStatus isPhishing;
    @Setter
    private List<String> phishngUrls;

    public enum SmsMessageStatus {
        PHISHING,
        OK,
        NOT_SUBSCRIBED
    }
}
