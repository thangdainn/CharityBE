package org.dainn.charitybe.services.impls;

import lombok.RequiredArgsConstructor;
import org.dainn.charitybe.dtos.SponsorDTO;
import org.dainn.charitybe.dtos.request.SponsorSearch;
import org.dainn.charitybe.enums.ErrorCode;
import org.dainn.charitybe.exceptions.AppException;
import org.dainn.charitybe.mapper.ISponsorMapper;
import org.dainn.charitybe.models.SponsorEntity;
import org.dainn.charitybe.repositories.ISponsorRepository;
import org.dainn.charitybe.services.ISponsorService;
import org.dainn.charitybe.utils.Paging;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor

public class SponsorService implements ISponsorService {
    private final ISponsorRepository sponsorRepository;
    private final ISponsorMapper sponsorMapper;

    @Transactional
    @Override
    public SponsorDTO insert(SponsorDTO dto) {
        sponsorRepository.findByName(dto.getName())
                .ifPresent(role -> {
                    throw new AppException(ErrorCode.SPONSOR_EXISTED);
                });
        SponsorEntity entity = sponsorMapper.toEntity(dto);
        return sponsorMapper.toDTO(sponsorRepository.save(entity));
    }

    @Transactional
    @Override
    public SponsorDTO update(SponsorDTO dto) {
        SponsorEntity old = sponsorRepository.findById(dto.getId())
                .orElseThrow(() -> new AppException(ErrorCode.SPONSOR_NOT_EXISTED));
        if (sponsorRepository.existsByNameAndIdNot(dto.getName(), dto.getId())) {
            throw new AppException(ErrorCode.CATEGORY_EXISTED);
        }
        SponsorEntity entity = sponsorMapper.updateEntity(old, dto);
        return sponsorMapper.toDTO(sponsorRepository.save(entity));
    }

    @Transactional
    @Override
    public void delete(List<Integer> ids) {
        sponsorRepository.deleteAllByIdInBatchCustom(ids);
    }

    @Override
    public SponsorDTO findById(Integer id) {
        return sponsorMapper.toDTO(sponsorRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SPONSOR_NOT_EXISTED)));
    }

    @Override
    public SponsorDTO findByName(String name) {
        return sponsorMapper.toDTO(sponsorRepository.findByName(name)
                .orElseThrow(() -> new AppException(ErrorCode.SPONSOR_NOT_EXISTED)));
    }

    @Override
    public List<SponsorDTO> findAll() {
        return sponsorRepository.findAll()
                .stream().map(sponsorMapper::toDTO).toList();
    }

    @Override
    public List<SponsorDTO> findAll(Integer status) {
        return sponsorRepository.findAllByStatus(status)
                .stream().map(sponsorMapper::toDTO).toList();
    }

    @Override
    public Page<SponsorDTO> findAllByName(SponsorSearch request) {
        Page<SponsorEntity> page = (StringUtils.hasText(request.getKeyword())
                ? sponsorRepository.findAllByNameContainingIgnoreCaseAndStatus(request.getKeyword(), request.getStatus(), Paging.getPageable(request))
                : sponsorRepository.findAllByStatus(request.getStatus(), Paging.getPageable(request))
        );
        return page.map(sponsorMapper::toDTO);
    }
}
