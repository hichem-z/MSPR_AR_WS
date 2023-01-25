package fr.group.mspr_ar_ws.security.service;

import fr.group.mspr_ar_ws.security.beans.EmailDetails;

import javax.mail.MessagingException;

public interface EmailService {
    String sendSimpleMail(EmailDetails details);
    String sendMailWithAttachment(EmailDetails details) throws MessagingException;
}
