package com.efrei.mailsendermicroservice.service;

import com.efrei.mailsendermicroservice.model.MailRequest;

public interface MailSenderService {
    void sendMail(String bearerToken, MailRequest request);
}
