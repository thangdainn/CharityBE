package org.dainn.charitybe.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.dainn.charitybe.constants.Endpoint;
import org.dainn.charitybe.services.IPaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(Endpoint.Payment.BASE)
@RequiredArgsConstructor
public class PaymentController {
    private final IPaymentService paymentService;

    @GetMapping(Endpoint.Payment.VN_PAY)
    public ResponseEntity<?> createVNPayPayment(HttpServletRequest request, Integer donationId) {
        return ResponseEntity.ok(paymentService.createVNPayPayment(request, donationId));
    }

    @GetMapping(Endpoint.Payment.VN_PAY_CALLBACK)
    public void vnpCallback(@RequestParam String vnp_ResponseCode,
                            HttpServletResponse response) throws IOException {
        if (vnp_ResponseCode.equals("00")) {
            // update donation status paid
        }
        else {
            // delete donation
        }
        response.sendRedirect("http://localhost:3000/order-status?vnp_ResponseCode=" + vnp_ResponseCode);

    }
}
