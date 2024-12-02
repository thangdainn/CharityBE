package org.dainn.charitybe.services.impls;

import lombok.RequiredArgsConstructor;
import org.dainn.charitybe.dtos.MailData;
import org.dainn.charitybe.services.IEmailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService implements IEmailService {
    private final JavaMailSender mailSender;

    @Override
    public void sendEmail(MailData mailData) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailData.getTo());
        message.setSubject(mailData.getSubject());
        message.setText(mailData.getBody());
        mailSender.send(message);
    }
}
