package org.dainn.charitybe.services.impls;

import lombok.RequiredArgsConstructor;
import org.dainn.charitybe.dtos.OtpDTO;
import org.dainn.charitybe.enums.ErrorCode;
import org.dainn.charitybe.exceptions.AppException;
import org.dainn.charitybe.mapper.IOtpMapper;
import org.dainn.charitybe.models.OtpEntity;
import org.dainn.charitybe.repositories.IOtpRepository;
import org.dainn.charitybe.services.IOtpService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Random;

@Service
@RequiredArgsConstructor

public class OtpService implements IOtpService {
    private final IOtpMapper otpMapper;
    private final IOtpRepository otpRepository;

    @Transactional
    @Override
    public OtpDTO insert(OtpDTO dto) {
        OtpEntity entity = otpMapper.toEntity(dto);
        entity.setCode(String.valueOf(new Random().nextInt(999999 - 100000) + 100000));
        entity.setExpiredDate(new Date(System.currentTimeMillis() + 300000));
        return otpMapper.toDTO(otpRepository.save(entity));
    }


    @Transactional
    @Override
    public void verify(OtpDTO dto) {
        OtpEntity otpEntity = otpRepository.findByCodeAndEmailOrderByCreatedDateDesc(dto.getCode(), dto.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.OTP_IS_INCORRECT));
        if (otpEntity.getExpiredDate().before(new Date())) {
            throw new AppException(ErrorCode.OTP_IS_EXPIRED);
        }
        otpEntity.setIsUsed(true);
        otpRepository.save(otpEntity);
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        otpRepository.deleteById(id);
    }

    @Override
    public OtpDTO findByCodeAndEmail(OtpDTO dto) {
        return otpMapper.toDTO(otpRepository.findByCodeAndEmailOrderByCreatedDateDesc(dto.getCode(), dto.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.OTP_IS_INCORRECT)));
    }
}
