package org.dainn.charitybe.dtos;

import lombok.Builder;

public class PaymentDTO {
    @Builder
    public static class VNPAYResponse {
        public String code;
        public String message;
        public String paymentUrl;
    }
}
