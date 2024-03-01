package com.efrei.mailsendermicroservice.controller;

import com.efrei.mailsendermicroservice.model.MailRequest;
import com.efrei.mailsendermicroservice.service.MailSenderServiceImpl;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mail-sender/send")
public class MailSenderController {
    private final MailSenderServiceImpl emailingService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void sendMail(@RequestHeader(name = "Authorization") String bearerToken,
                         @RequestBody MailRequest request) {
        emailingService.sendMail(bearerToken, request);
    }
}