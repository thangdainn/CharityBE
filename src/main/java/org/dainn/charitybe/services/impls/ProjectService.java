package org.dainn.charitybe.services.impls;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.dainn.charitybe.dtos.CharityProjectDTO;
import org.dainn.charitybe.dtos.request.CharityProjectSearch;
import org.dainn.charitybe.enums.ErrorCode;
import org.dainn.charitybe.exceptions.AppException;
import org.dainn.charitybe.mapper.ICharityProjectMapper;
import org.dainn.charitybe.models.CharityProjectEntity;
import org.dainn.charitybe.repositories.ICategoryRepository;
import org.dainn.charitybe.repositories.ICharityProjectRepository;
import org.dainn.charitybe.repositories.IUserRepository;
import org.dainn.charitybe.repositories.specification.SearchOperation;
import org.dainn.charitybe.repositories.specification.SpecSearchCriteria;
import org.dainn.charitybe.repositories.specification.SpecificationBuilder;
import org.dainn.charitybe.services.IProjectService;
import org.dainn.charitybe.utils.Paging;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProjectService implements IProjectService {
    private final ICharityProjectRepository projectRepository;
    private final IUserRepository userRepository;
    private final ICategoryRepository categoryRepository;
    private final ICharityProjectMapper projectMapper;
    private final Cloudinary cloudinary;

    @Transactional
    @Override
    public CharityProjectDTO insert(CharityProjectDTO dto) {
        CharityProjectEntity entity = projectMapper.toEntity(dto);
        setAttributes(entity, dto);
        return projectMapper.toDTO(projectRepository.save(entity));
    }


    @Transactional
    @Override
    public CharityProjectDTO update(CharityProjectDTO dto) {
        CharityProjectEntity old = projectRepository.findById(dto.getId())
                .orElseThrow(() -> new AppException(ErrorCode.PROJECT_NOT_EXISTED));
        CharityProjectEntity entity = projectMapper.updateEntity(old, dto);
        setAttributes(entity, dto);
        return projectMapper.toDTO(projectRepository.save(entity));
    }

    private void setAttributes(CharityProjectEntity entity, CharityProjectDTO dto) {
        entity.setUser(userRepository.findById(dto.getCreatedId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
        entity.setCategory(categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTED)));
    }

    @Transactional
    @Override
    public void delete(List<Integer> ids) {
        projectRepository.deleteAllByIdInBatchCustom(ids);
    }

    @Override
    public CharityProjectDTO findById(Integer id) {
        return projectMapper.toDTO(projectRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }

    @Override
    public CharityProjectDTO insert(CharityProjectDTO dto, MultipartFile thumbnail) {
        CharityProjectEntity entity = projectMapper.toEntity(dto);
        entity.setThumbnail(uploadImage(thumbnail));
        setAttributes(entity, dto);
        return projectMapper.toDTO(projectRepository.save(entity));
    }

    private String uploadImage(MultipartFile image) {
        try {
            return cloudinary.uploader().upload(image.getBytes(), null).get("secure_url").toString();
        } catch (Exception e) {
            throw new AppException(ErrorCode.UPLOAD_IMAGE_FAILED);
        }
    }

    @Override
    public List<CharityProjectDTO> findAll() {
        return projectRepository.findAll()
                .stream().map(projectMapper::toDTO).toList();
    }

    @Override
    public List<CharityProjectDTO> findAll(Integer status) {
        return projectRepository.findAllByStatus(status)
                .stream().map(projectMapper::toDTO).toList();
    }

    @Override
    public Page<CharityProjectDTO> findAllByFilters(CharityProjectSearch request) {
        SpecificationBuilder<CharityProjectEntity> builder = new SpecificationBuilder<>();
        Page<CharityProjectEntity> page;
        Specification<CharityProjectEntity> spec;
        if (StringUtils.hasText(request.getKeyword())) {
            builder.with("name", SearchOperation.CONTAINS, request.getKeyword(), false);
        }
        builder.with("status", SearchOperation.EQUALITY, request.getStatus(), false);
        if (request.getProjectFor() != null){
            builder.with("projectFor", SearchOperation.EQUALITY, request.getProjectFor(), false);
        }
        if (request.getStartDate() != null){
            builder.with("startDate", SearchOperation.GREATER_THAN_OR_EQUAL, request.getStartDate(), false);
        }
        if (request.getEndDate() != null){
            builder.with("endDate", SearchOperation.LESS_THAN_OR_EQUAL, request.getEndDate(), false);
        }
        spec = builder.build();
        if (request.getCategoryId() != null) {
            List<SpecSearchCriteria> prjCriteria = new ArrayList<>();
            prjCriteria.add(new SpecSearchCriteria("id", SearchOperation.EQUALITY, request.getCategoryId(), true));
            Specification<CharityProjectEntity> prjSpec = builder.joinTableWithCondition("category", prjCriteria);
            spec = Specification.where(spec).and(prjSpec);
        }
        page = projectRepository.findAll(Objects.requireNonNull(spec), Paging.getPageable(request));
        return page.map(projectMapper::toDTO);
    }
}
