package org.dainn.charitybe.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MailData {
    private String to;
    private String subject;
    private String body;
}
