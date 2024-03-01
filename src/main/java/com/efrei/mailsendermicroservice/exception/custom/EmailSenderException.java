package com.efrei.mailsendermicroservice.exception.custom;

public class EmailSenderException extends RuntimeException {
    public EmailSenderException(String message) {
        super(message);
    }
    public EmailSenderException(String message, Exception e) {
        super(message, e);
    }
}
