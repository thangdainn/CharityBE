package org.dainn.charitybe.services.impls;

import lombok.RequiredArgsConstructor;
import org.dainn.charitybe.dtos.EducationDTO;
import org.dainn.charitybe.dtos.request.EducationSearch;
import org.dainn.charitybe.enums.ErrorCode;
import org.dainn.charitybe.exceptions.AppException;
import org.dainn.charitybe.mapper.IEducationMapper;
import org.dainn.charitybe.models.EducationEntity;
import org.dainn.charitybe.repositories.IEducationRepository;
import org.dainn.charitybe.repositories.IEducationTypeRepository;
import org.dainn.charitybe.services.IEducationService;
import org.dainn.charitybe.utils.Paging;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor

public class EducationService implements IEducationService {
    private final IEducationRepository educationRepository;
    private final IEducationTypeRepository educationTypeRepository;
    private final IEducationMapper educationMapper;

    @Transactional
    @Override
    public EducationDTO insert(EducationDTO dto) {
        educationRepository.findByName(dto.getName())
                .ifPresent(role -> {
                    throw new AppException(ErrorCode.EDUCATION_EXISTED);
                });
        EducationEntity entity = educationMapper.toEntity(dto);
        entity.setEducationType(educationTypeRepository.findById(dto.getTypeId())
                .orElseThrow(() -> new AppException(ErrorCode.EDUCATION_TYPE_NOT_EXISTED)));
        return educationMapper.toDTO(educationRepository.save(entity));
    }

    @Transactional
    @Override
    public EducationDTO update(EducationDTO dto) {
        EducationEntity old = educationRepository.findById(dto.getId())
                .orElseThrow(() -> new AppException(ErrorCode.EDUCATION_NOT_EXISTED));
        if (educationRepository.existsByNameAndIdNot(dto.getName(), dto.getId())) {
            throw new AppException(ErrorCode.EDUCATION_EXISTED);
        }
        EducationEntity entity = educationMapper.updateEntity(old, dto);
        return educationMapper.toDTO(educationRepository.save(entity));
    }

    @Transactional
    @Override
    public void delete(List<Integer> ids) {
        educationRepository.deleteAllByIdInBatchCustom(ids);
    }

    @Override
    public EducationDTO findById(Integer id) {
        return educationMapper.toDTO(educationRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.EDUCATION_NOT_EXISTED)));
    }

    @Override
    public EducationDTO findByName(String name) {
        return educationMapper.toDTO(educationRepository.findByName(name)
                .orElseThrow(() -> new AppException(ErrorCode.EDUCATION_NOT_EXISTED)));
    }

    @Override
    public List<EducationDTO> findAll() {
        return educationRepository.findAll()
                .stream().map(educationMapper::toDTO).toList();
    }

    @Override
    public List<EducationDTO> findAll(Integer status) {
        return educationRepository.findAllByStatus(status)
                .stream().map(educationMapper::toDTO).toList();
    }

    @Override
    public Page<EducationDTO> findAllByConditions(EducationSearch request) {
        Page<EducationEntity> page = educationRepository.findAllByConditions(request.getKeyword(), request.getTypeId(), request.getStatus(), Paging.getPageable(request));
        return page.map(educationMapper::toDTO);
    }
}
