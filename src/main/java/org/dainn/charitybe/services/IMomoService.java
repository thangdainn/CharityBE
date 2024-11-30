package org.dainn.charitybe.services;

import org.dainn.charitybe.dtos.momo.MomoCallbackDTO;
import org.dainn.charitybe.dtos.momo.MomoCreatePaymentDTO;

public interface IMomoService {
    MomoCreatePaymentDTO createMomoPayment(Integer donationId) throws Exception;
    int handleMomoCallBack(MomoCallbackDTO callbackDto);
}
