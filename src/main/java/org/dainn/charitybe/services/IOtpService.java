package org.dainn.charitybe.services;


import org.dainn.charitybe.dtos.OtpDTO;

public interface IOtpService {

    OtpDTO insert(OtpDTO dto);
    void verify(OtpDTO dto);
    void delete(Integer id);

    OtpDTO findByCodeAndEmail(OtpDTO dto);

}
