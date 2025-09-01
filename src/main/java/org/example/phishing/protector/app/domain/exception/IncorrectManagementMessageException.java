package org.example.phishing.protector.app.domain.exception;

public class IncorrectManagementMessageException extends RuntimeException {
    public IncorrectManagementMessageException(String message) {
        super(String.format("Incorrect management message has been sent: %s", message));
    }
}
