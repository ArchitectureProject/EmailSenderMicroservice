package com.efrei.mailsendermicroservice.service;

import com.efrei.mailsendermicroservice.model.MailParam;
import com.efrei.mailsendermicroservice.model.MailRequest;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class MailSenderService {
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String fromMail;

    @Async
    public void sendMail(MailRequest request) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        mimeMessageHelper.setFrom(fromMail);
        mimeMessageHelper.setTo(request.receiverMailAdress());
        mimeMessageHelper.setSubject(request.mailObject());

        Context context = new Context();
        /*
           content is the variable defined in our HTML template within the div tag
           */
        for(MailParam param : request.emailParams()) {
            context.setVariable(param.id(), param.value());
        }

        String processedString = templateEngine.process(request.templateName(), context);
        mimeMessageHelper.setText(processedString, true);
        mailSender.send(mimeMessage);
    }
}
