package com.efrei.mailsendermicroservice.model;

import java.util.List;

public record MailRequest(String templateName, List<MailParam> emailParams, String receiverMailAdress, String mailObject) {
}
