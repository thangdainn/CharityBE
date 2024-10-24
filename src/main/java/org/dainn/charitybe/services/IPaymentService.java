package org.dainn.charitybe.services;

import jakarta.servlet.http.HttpServletRequest;
import org.dainn.charitybe.dtos.PaymentDTO;

public interface IPaymentService {
    PaymentDTO.VNPAYResponse createVNPayPayment(HttpServletRequest request, Integer donationId);
}
