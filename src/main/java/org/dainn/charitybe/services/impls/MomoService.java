package org.dainn.charitybe.services.impls;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.dainn.charitybe.config.momo.CreateSignature;
import org.dainn.charitybe.config.momo.MomoSettings;
import org.dainn.charitybe.dtos.DonationDTO;
import org.dainn.charitybe.dtos.momo.ExtraData;
import org.dainn.charitybe.dtos.momo.MomoCallbackDTO;
import org.dainn.charitybe.dtos.momo.MomoCreatePaymentDTO;
import org.dainn.charitybe.dtos.momo.PaymentRequestData;
import org.dainn.charitybe.services.IDonationService;
import org.dainn.charitybe.services.IMomoService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MomoService implements IMomoService {
    private final MomoSettings momoSettings;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final CreateSignature createSignature;
    private final IDonationService donationService;


    @Override
    public MomoCreatePaymentDTO createMomoPayment(Integer donationId) throws Exception {
        DonationDTO donation = donationService.findById(donationId);
        String billInfo = "Thanh toan ung ho";
        String requestId = UUID.randomUUID().toString();
        String donationIdStr = donationId + "_" + UUID.randomUUID();
        String extraData = Base64.getEncoder().encodeToString(objectMapper.writeValueAsBytes(new ExtraData(donationId)));

        String rawData = String.format("accessKey=%s&amount=%.0f&extraData=%s&ipnUrl=%s&orderId=%s&orderInfo=%s" +
                        "&partnerCode=%s&redirectUrl=%s&requestId=%s&requestType=%s",
                momoSettings.getAccessKey(),
                donation.getAmount(),
                extraData,
                momoSettings.getNotifyUrl(),
                donationIdStr,
                billInfo,
                momoSettings.getPartnerCode(),
                momoSettings.getReturnUrl(),
                requestId,
                momoSettings.getRequestType());

        String signature = createSignature.computeHmacSha256(rawData, momoSettings.getSecretKey());

        PaymentRequestData requestData = new PaymentRequestData(
                momoSettings.getPartnerCode(),
                requestId,
                donation.getAmount(),
                donationIdStr,
                billInfo,
                momoSettings.getReturnUrl(),
                momoSettings.getNotifyUrl(),
                "vi",
                15,
                extraData,
                momoSettings.getRequestType(),
                signature,
                true
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
        HttpEntity<PaymentRequestData> httpEntity = new HttpEntity<>(requestData, headers);
        try {
            ResponseEntity<MomoCreatePaymentDTO> response = restTemplate.exchange(
                    momoSettings.getMomoApiUrl(),
                    HttpMethod.POST,
                    httpEntity,
                    MomoCreatePaymentDTO.class
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                throw new Exception("Failed to create Momo payment: " + Objects.requireNonNull(response.getBody()).getMessage());
            }
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            throw ex;
        }
    }

    @Override
    public int handleMomoCallBack(MomoCallbackDTO callbackDto) {
        String rawData = String.format("accessKey=%s&amount=%.0f&extraData=%s&message=%s&orderId=%s" +
                        "&orderInfo=%s&orderType=%s&partnerCode=%s&payType=%s&requestId=%s" +
                        "&responseTime=%d&resultCode=%d&transId=%d",
                momoSettings.getAccessKey(),
                callbackDto.getAmount(),
                callbackDto.getExtraData(),
                callbackDto.getMessage(),
                callbackDto.getOrderId(),
                callbackDto.getOrderInfo(),
                callbackDto.getOrderType(),
                callbackDto.getPartnerCode(),
                callbackDto.getPayType(),
                callbackDto.getRequestId(),
                callbackDto.getResponseTime(),
                callbackDto.getResultCode(),
                callbackDto.getTransId());

        String expectedSignature = null;
        try {
            expectedSignature = createSignature.computeHmacSha256(rawData, momoSettings.getSecretKey());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Integer donationId = Integer.parseInt(callbackDto.getOrderId().split("_")[0]);

        if (callbackDto.getSignature().equals(expectedSignature) && callbackDto.getResultCode() == 0) {
            donationService.updateIsPaid(donationId);
        } else {
            donationService.delete(donationId);
        }
        return callbackDto.getResultCode();
    }
}
