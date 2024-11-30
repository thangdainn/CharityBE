package org.dainn.charitybe.services.impls;

import lombok.RequiredArgsConstructor;
import org.dainn.charitybe.dtos.CampaignDTO;
import org.dainn.charitybe.dtos.DonationDTO;
import org.dainn.charitybe.dtos.notification.Notification;
import org.dainn.charitybe.dtos.request.DonationSearch;
import org.dainn.charitybe.enums.ErrorCode;
import org.dainn.charitybe.enums.NotificationStatus;
import org.dainn.charitybe.exceptions.AppException;
import org.dainn.charitybe.mapper.IDonationMapper;
import org.dainn.charitybe.models.CampaignEntity;
import org.dainn.charitybe.models.DonationEntity;
import org.dainn.charitybe.repositories.ICampaignRepository;
import org.dainn.charitybe.repositories.IDonationRepository;
import org.dainn.charitybe.repositories.IUserRepository;
import org.dainn.charitybe.services.ICampaignService;
import org.dainn.charitybe.services.IDonationService;
import org.dainn.charitybe.services.IExcelService;
import org.dainn.charitybe.utils.Paging;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor

public class DonationService implements IDonationService {
    private final IDonationRepository donationRepository;
    private final IDonationMapper donationMapper;
    private final ICampaignRepository campaignRepository;
    private final ICampaignService campaignService;
    private final IUserRepository userRepository;
    private final NotificationService notificationService;
    private final IExcelService excelService;

    @Transactional
    @Override
    public DonationDTO insert(DonationDTO dto) {
        DonationEntity donation = donationMapper.toEntity(dto);
        donation.setCampaign(campaignRepository.findById(dto.getCampaignId())
                .orElseThrow(() -> new AppException(ErrorCode.CAMPAIGN_NOT_EXISTED)));
        if (!dto.getIsAnonymous() && dto.getUserId() != null) {
            donation.setUser(userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
        }
        return donationMapper.toDTO(donationRepository.save(donation));
    }

    @Transactional
    @Override
    public void updateIsPaid(Integer id) {
        donationRepository.updateIsPaidById(id, true);
        DonationEntity donation = donationRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.DONATION_NOT_EXISTED));
        CampaignDTO campaign = campaignService.updateCurrentAmount(donation.getAmount(), donation.getCampaign().getId());
        notificationService.sendNotification(
                campaign.getCreatedId().toString(),
                Notification.builder()
                        .status(NotificationStatus.SUCCESS)
                        .title(campaign.getName())
                        .message("You have received a new donation")
                        .build()
        );
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        donationRepository.deleteById(id);
    }

    @Override
    public DonationDTO findById(Integer id) {
        return donationMapper.toDTO(donationRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.DONATION_NOT_EXISTED)));
    }

    @Override
    public List<DonationDTO> findByCampaignId(Integer campaignId) {
        return donationRepository.findAllByCampaignId(campaignId)
                .stream().map(donationMapper::toDTO).toList();
    }

    @Override
    public Page<DonationDTO> findAllByFilters(DonationSearch request) {
        Page<DonationEntity> page = new PageImpl<>(List.of());
        if (request.getCampaignId() != null) {
            page = donationRepository.findAllByCampaignId(request.getCampaignId(), Paging.getPageable(request));
        }
        else if (request.getUserId() != null) {
            page = donationRepository.findAllByUserId(request.getUserId(), Paging.getPageable(request));
        }
        return page.map(donationMapper::toDTO);
    }

    @Override
    public byte[] exportDonationsByCampaignId(Integer campaignId) {
        CampaignEntity campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new AppException(ErrorCode.CAMPAIGN_NOT_EXISTED));
        List<DonationDTO> donations = donationRepository.findAllByCampaignIdAndIsPaid(campaignId, true)
                .stream().map(donationMapper::toDTO).toList();
        return excelService.generateDonationExcel(donations, campaign.getName());
    }
}
