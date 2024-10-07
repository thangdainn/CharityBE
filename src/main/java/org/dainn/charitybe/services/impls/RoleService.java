package org.dainn.charitybe.services.impls;

import lombok.RequiredArgsConstructor;
import org.dainn.charitybe.constants.RoleConstant;
import org.dainn.charitybe.dtos.RoleDTO;
import org.dainn.charitybe.dtos.request.BaseSearch;
import org.dainn.charitybe.enums.ErrorCode;
import org.dainn.charitybe.exceptions.AppException;
import org.dainn.charitybe.mapper.IRoleMapper;
import org.dainn.charitybe.models.RoleEntity;
import org.dainn.charitybe.repositories.IRoleRepository;
import org.dainn.charitybe.services.IRoleService;
import org.dainn.charitybe.utils.Paging;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor

public class RoleService implements IRoleService {
    private final IRoleRepository roleRepository;
    private final IRoleMapper roleMapper;

    @Transactional
    @Override
    public RoleDTO insert(RoleDTO dto) {
        dto.setName(RoleConstant.ROLE_PREFIX + dto.getName().toUpperCase());
        roleRepository.findByName(dto.getName())
                .ifPresent(role -> {
                    throw new AppException(ErrorCode.ROLE_EXISTED);
                });
        RoleEntity entity = roleMapper.toEntity(dto);
        return roleMapper.toDTO(roleRepository.save(entity));
    }

    @Transactional
    @Override
    public RoleDTO update(RoleDTO dto) {
        dto.setName(RoleConstant.ROLE_PREFIX + dto.getName().toUpperCase());
        RoleEntity roleOld = roleRepository.findById(dto.getId())
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));
        if (roleRepository.existsByNameAndIdNot(dto.getName(), dto.getId())) {
            throw new AppException(ErrorCode.ROLE_EXISTED);
        }
        RoleEntity entity = roleMapper.updateEntity(roleOld, dto);
        return roleMapper.toDTO(roleRepository.save(entity));
    }

    @Transactional
    @Override
    public void delete(List<Integer> ids) {
        roleRepository.deleteAllByIdInBatchCustom(ids);
    }

    @Override
    public RoleDTO findById(Integer id) {
        return roleMapper.toDTO(roleRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED)));
    }

    @Override
    public RoleDTO findByName(String name) {
        return roleMapper.toDTO(roleRepository.findByName(name)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED)));
    }

    @Override
    public List<RoleDTO> findAll() {
        return roleRepository.findAll()
                .stream().map(roleMapper::toDTO).toList();
    }

    @Override
    public List<RoleDTO> findAll(Integer status) {
        return roleRepository.findAllByStatus(status)
                .stream().map(roleMapper::toDTO).toList();
    }

    @Override
    public Page<RoleDTO> findAllByName(BaseSearch request) {
        Page<RoleEntity> page = (StringUtils.hasText(request.getKeyword())
                ? roleRepository.findAllByNameContainingIgnoreCaseAndStatus(request.getKeyword(), request.getStatus(), Paging.getPageable(request))
                : roleRepository.findAllByStatus(request.getStatus(), Paging.getPageable(request))
        );
        return page.map(roleMapper::toDTO);
    }
}
