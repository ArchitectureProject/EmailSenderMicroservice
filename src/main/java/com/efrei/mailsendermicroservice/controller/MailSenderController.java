package com.efrei.mailsendermicroservice.controller;



import com.efrei.mailsendermicroservice.model.MailRequest;
import com.efrei.mailsendermicroservice.service.MailSenderService;
import jakarta.mail.MessagingException;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mail-sender/send")
public class MailSenderController {
    private final MailSenderService emailingService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void sendMail(@RequestBody MailRequest request) throws MessagingException {
        emailingService.sendMail(request);
    }
}
