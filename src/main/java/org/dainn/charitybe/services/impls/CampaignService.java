package org.dainn.charitybe.services.impls;

import lombok.RequiredArgsConstructor;
import org.dainn.charitybe.dtos.CampaignDTO;
import org.dainn.charitybe.dtos.CampaignDetailDTO;
import org.dainn.charitybe.dtos.request.CampaignSearch;
import org.dainn.charitybe.enums.CampaignStatus;
import org.dainn.charitybe.enums.ErrorCode;
import org.dainn.charitybe.exceptions.AppException;
import org.dainn.charitybe.mapper.ICampaignMapper;
import org.dainn.charitybe.models.CampaignEntity;
import org.dainn.charitybe.repositories.*;
import org.dainn.charitybe.repositories.specification.SearchOperation;
import org.dainn.charitybe.repositories.specification.SpecSearchCriteria;
import org.dainn.charitybe.repositories.specification.SpecificationBuilder;
import org.dainn.charitybe.services.ICampaignService;
import org.dainn.charitybe.utils.Paging;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class CampaignService implements ICampaignService {
    private final ICampaignRepository projectRepository;
    private final IUserRepository userRepository;
    private final ICategoryRepository categoryRepository;
    private final IDonationRepository donationRepository;
    private final IEducationRepository educationRepository;
    private final ICampaignMapper projectMapper;

    @Transactional
    @Override
    public CampaignDTO insert(CampaignDTO dto) {
        CampaignEntity entity = projectMapper.toEntity(dto);
        setAttributes(entity, dto);
        return projectMapper.toDTO(projectRepository.save(entity));
    }


    @Transactional
    @Override
    public CampaignDTO update(CampaignDTO dto) {
        CampaignEntity old = projectRepository.findById(dto.getId())
                .orElseThrow(() -> new AppException(ErrorCode.CAMPAIGN_NOT_EXISTED));
        CampaignEntity entity = projectMapper.updateEntity(old, dto);
        setAttributes(entity, dto);
        return projectMapper.toDTO(projectRepository.save(entity));
    }

    @Transactional
    @Override
    public CampaignDTO updateCurrentAmount(BigDecimal amount, Integer id) {
        CampaignEntity entity = projectRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CAMPAIGN_NOT_EXISTED));
        entity.setCurrentAmount(entity.getCurrentAmount().add(amount));
        if (entity.getCurrentAmount().compareTo(entity.getTargetAmount()) >= 0) {
            entity.setStatus(CampaignStatus.COMPLETED);
        }
        return projectMapper.toDTO(projectRepository.save(entity));
    }

    @Override
    public List<CampaignDTO> findByUserId(Integer userId) {
        return projectRepository.findAllByUserId(userId)
                .stream().map(projectMapper::toDTO).toList();
    }

    private String generateCodeFromName(String name) {
        String noAccent = Normalizer.normalize(name, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String noSpecialChar = pattern.matcher(noAccent).replaceAll("");
        return noSpecialChar.toLowerCase().replaceAll("\\s+", "-");
    }

    private void setAttributes(CampaignEntity entity, CampaignDTO dto) {
        entity.setCode(generateCodeFromName(dto.getName()));
        entity.setUser(userRepository.findById(dto.getCreatedId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
        entity.setCategory(categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTED)));
        entity.setEducation(educationRepository.findById(dto.getEducationId())
                .orElseThrow(() -> new AppException(ErrorCode.EDUCATION_NOT_EXISTED)));
    }

    @Override
    public CampaignDTO findById(Integer id) {
        return projectMapper.toDTO(projectRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CAMPAIGN_NOT_EXISTED)));
    }

    @Override
    public CampaignDetailDTO findByCode(String code) {
        return projectMapper.toDetailDTO(projectRepository.findByCode(code)
                .orElseThrow(() -> new AppException(ErrorCode.CAMPAIGN_NOT_EXISTED)));
    }

    @Override
    public List<CampaignDTO> findAll() {
        return projectRepository.findAll()
                .stream().map(projectMapper::toDTO).toList();
    }

    @Override
    public Page<CampaignDTO> findAllByFilters(CampaignSearch request) {
        request.setSortBy("startDate");
        request.setSortDir("desc");
        SpecificationBuilder<CampaignEntity> builder = new SpecificationBuilder<>();
        Page<CampaignEntity> page;
        Specification<CampaignEntity> spec;
        request.setKeyword(StringUtils.hasText(request.getKeyword()) ? request.getKeyword() : "");
        builder.with("name", SearchOperation.CONTAINS, request.getKeyword() , false);

        if (request.getStartDate() != null){
            builder.with("startDate", SearchOperation.GREATER_THAN_OR_EQUAL, request.getStartDate(), false);
        }
        if (request.getEndDate() != null){
            builder.with("endDate", SearchOperation.LESS_THAN_OR_EQUAL, request.getEndDate(), false);
        }
        builder.with("status", SearchOperation.NEGATION, CampaignStatus.REJECTED, false);
        builder.with("status", SearchOperation.NEGATION, CampaignStatus.PENDING, false);


        spec = builder.build();
        if (request.getCategoryId() != null) {
            List<SpecSearchCriteria> prjCriteria = new ArrayList<>();
            prjCriteria.add(new SpecSearchCriteria("id", SearchOperation.EQUALITY, request.getCategoryId(), true));
            Specification<CampaignEntity> prjSpec = builder.joinTableWithCondition("category", prjCriteria);
            spec = Specification.where(spec).and(prjSpec);
        }
        page = projectRepository.findAll(Objects.requireNonNull(spec), Paging.getPageable(request));

        return page.map(campaign -> {
            CampaignDTO dto = projectMapper.toDTO(campaign);
            dto.setTotalDonation(donationRepository.countByCampaignIdAndIsPaid(campaign.getId(), true));
            return dto;
        });
    }
}
