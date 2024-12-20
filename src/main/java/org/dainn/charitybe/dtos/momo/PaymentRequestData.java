package org.dainn.charitybe.dtos.momo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class PaymentRequestData {
        @JsonProperty("partnerCode")
        String partnerCode;

        @JsonProperty("requestId")
        String requestId;

        @JsonProperty("amount")
        BigDecimal amount;

        @JsonProperty("orderId")
        String orderId;

        @JsonProperty("orderInfo")
        String orderInfo;

        @JsonProperty("redirectUrl")
        String redirectUrl;

        @JsonProperty("ipnUrl")
        String ipnUrl;

        @JsonProperty("lang")
        String lang;

        @JsonProperty("orderExpireTime")
        int orderExpireTime;

        @JsonProperty("extraData")
        String extraData;

        @JsonProperty("requestType")
        String requestType;

        @JsonProperty("signature")
        String signature;

        @JsonProperty("autoCapture")
        boolean autoCapture;
}
