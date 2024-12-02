package org.dainn.charitybe.services;

import org.dainn.charitybe.dtos.MailData;

public interface IEmailService {
    void sendEmail(MailData mailData);
}
