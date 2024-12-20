package org.dainn.charitybe.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.dainn.charitybe.constants.Endpoint;
import org.dainn.charitybe.dtos.momo.MomoCallbackDTO;
import org.dainn.charitybe.dtos.momo.MomoCreatePaymentDTO;
import org.dainn.charitybe.services.IDonationService;
import org.dainn.charitybe.services.IMomoService;
import org.dainn.charitybe.services.IPaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(Endpoint.Payment.BASE)
@RequiredArgsConstructor
public class PaymentController {
    private final IPaymentService paymentService;
    private final IDonationService donationService;
    private final IMomoService momoService;

    @GetMapping(Endpoint.Payment.VN_PAY)
    public ResponseEntity<?> createVNPayPayment(HttpServletRequest request, Integer donationId) {
        return ResponseEntity.ok(paymentService.createVNPayPayment(request, donationId));
    }

    @GetMapping(Endpoint.Payment.VN_PAY_CALLBACK)
    public void vnpCallback(@RequestParam String vnp_ResponseCode,
                            HttpServletRequest request,
                            HttpServletResponse response) throws IOException {
        Integer donationId = Integer.parseInt(request.getParameter("vnp_TxnRef"));

        if (vnp_ResponseCode.equals("00")) {
            donationService.updateIsPaid(donationId);
        }
        else {
            donationService.delete(donationId);
        }
        response.sendRedirect("http://localhost:3000/donation-status?vnp_ResponseCode=" + vnp_ResponseCode);
    }

    @GetMapping(Endpoint.Payment.MOMO)
    public ResponseEntity<?> createPaymentMomo(Integer donationId) throws Exception {
        return ResponseEntity.ok(momoService.createMomoPayment(donationId));
    }
    @GetMapping(Endpoint.Payment.MOMO_CALLBACK)
    public void handleMomoCallBack(MomoCallbackDTO callbackDto,
                                   HttpServletResponse response) throws IOException {
        int resultCode = momoService.handleMomoCallBack(callbackDto);
        response.sendRedirect("http://localhost:3000/donation-status?vnp_ResponseCode=" + resultCode);
    }
}
