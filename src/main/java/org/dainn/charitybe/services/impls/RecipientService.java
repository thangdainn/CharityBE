package org.dainn.charitybe.services.impls;

import lombok.RequiredArgsConstructor;
import org.dainn.charitybe.dtos.RecipientDTO;
import org.dainn.charitybe.dtos.request.RecipientSearch;
import org.dainn.charitybe.enums.ErrorCode;
import org.dainn.charitybe.exceptions.AppException;
import org.dainn.charitybe.mapper.IRecipientMapper;
import org.dainn.charitybe.models.RecipientEntity;
import org.dainn.charitybe.repositories.IRecipientRepository;
import org.dainn.charitybe.services.IRecipientService;
import org.dainn.charitybe.utils.Paging;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RecipientService implements IRecipientService {
    private final IRecipientRepository recipientRepository;
    private final IRecipientMapper recipientMapper;

    @Override
    public RecipientDTO insert(RecipientDTO dto) {
        recipientRepository.findByCode(dto.getCode())
                .ifPresent(role -> {
                    throw new AppException(ErrorCode.RECIPIENT_EXISTED);
                });
        RecipientEntity entity = recipientMapper.toEntity(dto);
        return recipientMapper.toDTO(recipientRepository.save(entity));
    }

    @Override
    public RecipientDTO update(RecipientDTO dto) {
        RecipientEntity old = recipientRepository.findById(dto.getId())
                .orElseThrow(() -> new AppException(ErrorCode.RECIPIENT_NOT_EXISTED));
        if(recipientRepository.existsByCodeAndIdNot(dto.getCode(), dto.getId())) {
            throw new AppException(ErrorCode.RECIPIENT_EXISTED);
        }

        RecipientEntity entity = recipientMapper.updateEntity(old, dto);
        return recipientMapper.toDTO(recipientRepository.save(entity));
    }

    @Override
    public void delete(List<Integer> ids) {
        recipientRepository.deleteAllByIdInBatch(ids);
    }

    @Override
    public RecipientDTO findById(Integer id) {
        return recipientMapper.toDTO(recipientRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.RECIPIENT_NOT_EXISTED)));
    }

    @Override
    public RecipientDTO findByName(String name) {
        return recipientMapper.toDTO(recipientRepository.findByName(name)
                .orElseThrow(() -> new AppException(ErrorCode.RECIPIENT_NOT_EXISTED)));
    }

    @Override
    public RecipientDTO findByCode(String code) {
        return recipientMapper.toDTO(recipientRepository.findByCode(code)
                .orElseThrow(() -> new AppException(ErrorCode.RECIPIENT_NOT_EXISTED)));
    }

    @Override
    public List<RecipientDTO> findAll() {
        return recipientRepository.findAll().stream()
                .map(recipientMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<RecipientDTO> findAllByConditions(RecipientSearch request) {
        Page<RecipientEntity> page = recipientRepository.findAllByConditions(request.getKeyword(), Paging.getPageable(request));
        return page.map(recipientMapper::toDTO);
    }
}