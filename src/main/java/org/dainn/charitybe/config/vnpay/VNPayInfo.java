package org.dainn.charitybe.config.vnpay;

public class VNPayInfo {
    public static final String VN_PAY_URL = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
    public static final String VN_PAY_RETURN_URL = "http://localhost:8090/api/payment/vnp-callback";
    public static final String VN_PAY_TMN_CODE = "QBQZOL8I";
    public static final String VN_PAY_SECRET_KEY = "PO02MZB132J2ZDZLP6EYKIMUZSNGE4O7";
    public static final String VN_PAY_HASH_ALGORITHM = "SHA256";
    public static final String VN_PAY_VERSION = "2.1.0";
    public static final String VN_PAY_COMMAND = "pay";
    public static final String VN_PAY_ORDER_TYPE = "other";
}
