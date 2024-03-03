package com.efrei.mailsendermicroservice.service;

import com.efrei.mailsendermicroservice.exception.custom.EmailSenderException;
import com.efrei.mailsendermicroservice.model.MailParam;
import com.efrei.mailsendermicroservice.model.MailRequest;

import com.efrei.mailsendermicroservice.utils.JwtUtils;
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
public class MailSenderServiceImpl implements MailSenderService{
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    private final JwtUtils jwtUtils;

    @Value("${spring.mail.username}")
    private String fromMail;

    @Override
    @Async
    public void sendMail(String bearerToken, MailRequest request) {
        jwtUtils.validateJwt(bearerToken.substring(7), null);

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        try{
            mimeMessageHelper.setFrom(fromMail);
            mimeMessageHelper.setTo(request.receiverMailAdress());
            mimeMessageHelper.setSubject(request.mailObject());
        }
        catch (MessagingException e){
            throw new EmailSenderException("Error while sending email", e);
        }

        Context context = new Context();

        for(MailParam param : request.emailParams()) {
            context.setVariable(param.id(), param.value());
        }

        String processedString = templateEngine.process(request.templateName(), context);
        try{
            mimeMessageHelper.setText(processedString, true);
            mailSender.send(mimeMessage);
        }
        catch (MessagingException e){
            throw new EmailSenderException("Error while sending email", e);
        }
    }
}
