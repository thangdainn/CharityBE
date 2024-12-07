package org.dainn.charitybe.services;


import org.dainn.charitybe.dtos.OtpDTO;
import org.dainn.charitybe.dtos.auth.ResetPassword;

public interface IOtpService {

    OtpDTO insert(OtpDTO dto);
    void verify(OtpDTO dto);
    void delete(Integer id);

    OtpDTO findByCodeAndEmail(ResetPassword dto);

}
