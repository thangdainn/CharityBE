package org.dainn.charitybe.services.impls;

import lombok.RequiredArgsConstructor;
import org.dainn.charitybe.dtos.DonationSponsorDTO;
import org.dainn.charitybe.enums.ErrorCode;
import org.dainn.charitybe.exceptions.AppException;
import org.dainn.charitybe.mapper.IDonationReportMapper;
import org.dainn.charitybe.models.DonationSponsorEntity;
import org.dainn.charitybe.repositories.ICharityProjectRepository;
import org.dainn.charitybe.repositories.IDonationSponsorRepository;
import org.dainn.charitybe.repositories.ISponsorRepository;
import org.dainn.charitybe.services.IDonationSponsorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor

public class DonationSponsorService implements IDonationSponsorService {
    private final IDonationSponsorRepository donationSponsorRepository;
    private final ICharityProjectRepository projectRepository;
    private final ISponsorRepository sponsorRepository;
    private final IDonationReportMapper donationReportMapper;

    @Transactional
    @Override
    public DonationSponsorDTO insert(DonationSponsorDTO dto) {
        DonationSponsorEntity entity = donationReportMapper.toEntity(dto);
        entity.setCharityProject(projectRepository.findById(dto.getProjectId())
                .orElseThrow(() -> new AppException(ErrorCode.PROJECT_NOT_EXISTED)));
        entity.setSponsor(sponsorRepository.findById(dto.getSponsorId())
                .orElseThrow(() -> new AppException(ErrorCode.SPONSOR_NOT_EXISTED)));
        return donationReportMapper.toDTO(donationSponsorRepository.save(entity));
    }

    @Transactional
    @Override
    public void delete(List<Integer> ids) {
        donationSponsorRepository.deleteAllByIdInBatch(ids);
    }

    @Override
    public DonationSponsorDTO findById(Integer id) {
        return donationReportMapper.toDTO(donationSponsorRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.DONATION_SPONSOR_NOT_EXISTED)));
    }

    @Override
    public List<DonationSponsorDTO> findAll() {
        return donationSponsorRepository.findAll()
                .stream().map(donationReportMapper::toDTO).toList();
    }

    @Override
    public DonationSponsorDTO findByProjectIdAndSponsorId(Integer projectId, Integer sponsorId) {
        return donationReportMapper.toDTO(donationSponsorRepository.findByCharityProjectIdAndSponsorId(projectId, sponsorId)
                .orElseThrow(() -> new AppException(ErrorCode.DONATION_SPONSOR_NOT_EXISTED)));
    }
}
