package org.dainn.charitybe.services.impls;

import lombok.RequiredArgsConstructor;
import org.dainn.charitybe.dtos.CategoryDTO;
import org.dainn.charitybe.dtos.request.CategorySearch;
import org.dainn.charitybe.enums.ErrorCode;
import org.dainn.charitybe.exceptions.AppException;
import org.dainn.charitybe.mapper.ICategoryMapper;
import org.dainn.charitybe.models.CategoryEntity;
import org.dainn.charitybe.repositories.ICategoryRepository;
import org.dainn.charitybe.services.ICategoryService;
import org.dainn.charitybe.utils.Paging;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor

public class CategoryService implements ICategoryService {
    private final ICategoryRepository categoryRepository;
    private final ICategoryMapper categoryMapper;

    @Transactional
    @Override
    public CategoryDTO insert(CategoryDTO dto) {
        categoryRepository.findByName(dto.getName())
                .ifPresent(role -> {
                    throw new AppException(ErrorCode.CATEGORY_EXISTED);
                });
        CategoryEntity categoryEntity = categoryMapper.toEntity(dto);
        return categoryMapper.toDTO(categoryRepository.save(categoryEntity));
    }

    @Transactional
    @Override
    public CategoryDTO update(CategoryDTO dto) {
        CategoryEntity brandOld = categoryRepository.findById(dto.getId())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));
        if (categoryRepository.existsByNameAndIdNot(dto.getName(), dto.getId())) {
            throw new AppException(ErrorCode.CATEGORY_EXISTED);
        }
        CategoryEntity categoryEntity = categoryMapper.updateEntity(brandOld, dto);
        return categoryMapper.toDTO(categoryRepository.save(categoryEntity));
    }

    @Transactional
    @Override
    public void delete(List<Integer> ids) {
        categoryRepository.deleteAllByIdInBatchCustom(ids);
    }

    @Override
    public CategoryDTO findById(Integer id) {
        return categoryMapper.toDTO(categoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTED)));
    }

    @Override
    public CategoryDTO findByName(String name) {
        return categoryMapper.toDTO(categoryRepository.findByName(name)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTED)));
    }

    @Override
    public List<CategoryDTO> findAll() {
        return categoryRepository.findAll()
                .stream().map(categoryMapper::toDTO).toList();
    }

    @Override
    public List<CategoryDTO> findAll(Integer status) {
        return categoryRepository.findAllByStatus(status)
                .stream().map(categoryMapper::toDTO).toList();
    }

    @Override
    public Page<CategoryDTO> findAllByName(CategorySearch request) {
        Page<CategoryEntity> page = (StringUtils.hasText(request.getKeyword())
                ? categoryRepository.findAllByNameContainingIgnoreCaseAndStatus(request.getKeyword(), request.getStatus(), Paging.getPageable(request))
                : categoryRepository.findAllByStatus(request.getStatus(), Paging.getPageable(request))
        );
        return page.map(categoryMapper::toDTO);
    }
}
