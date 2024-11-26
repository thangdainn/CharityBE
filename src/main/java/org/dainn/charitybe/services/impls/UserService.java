package org.dainn.charitybe.services.impls;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.dainn.charitybe.constants.RoleConstant;
import org.dainn.charitybe.dtos.UserDTO;
import org.dainn.charitybe.dtos.request.UserSearch;
import org.dainn.charitybe.enums.ErrorCode;
import org.dainn.charitybe.enums.Provider;
import org.dainn.charitybe.exceptions.AppException;
import org.dainn.charitybe.filters.JwtProvider;
import org.dainn.charitybe.mapper.IUserMapper;
import org.dainn.charitybe.models.RoleEntity;
import org.dainn.charitybe.models.UserEntity;
import org.dainn.charitybe.repositories.IRoleRepository;
import org.dainn.charitybe.repositories.IUserRepository;
import org.dainn.charitybe.repositories.specification.SearchOperation;
import org.dainn.charitybe.repositories.specification.SpecSearchCriteria;
import org.dainn.charitybe.repositories.specification.SpecificationBuilder;
import org.dainn.charitybe.services.IUserService;
import org.dainn.charitybe.utils.Paging;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final PasswordEncoder encoder;
    private final IUserRepository userRepository;
    private final IUserMapper userMapper;
    private final IRoleRepository roleRepository;
    private final JwtProvider jwtProvider;

    @Transactional
    @Override
    public UserDTO insert(UserDTO dto) {
        if (checkEmailAndProvider(dto.getEmail(), dto.getProvider())) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }
        UserEntity userEntity = userMapper.toEntity(dto);
        userEntity.setPassword(encoder.encode(dto.getPassword()));
        userEntity.setRole(handleRole(dto.getRoleName()));
        return userMapper.toDTO(userRepository.save(userEntity));
    }


    @Transactional
    @Override
    public UserDTO update(UserDTO dto) {
        UserEntity userOld = userRepository.findById(dto.getId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        if (!userOld.getEmail().equals(dto.getEmail())
                && checkEmailAndProvider(dto.getEmail(), dto.getProvider())) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }
        if (dto.getPassword() == null || dto.getPassword().isBlank()) {
            dto.setPassword(userOld.getPassword());
        } else {
            dto.setPassword(encoder.encode(dto.getPassword()));
        }
        UserEntity userEntity = userMapper.updateEntity(userOld, dto);
        userEntity.setRole(handleRole(dto.getRoleName()));
        return userMapper.toDTO(userRepository.save(userEntity));
    }

    private boolean checkEmailAndProvider(String email, Provider provider) {
        return userRepository.existsByEmailAndProvider(email, provider);
    }

    private RoleEntity handleRole(String roleName) {
        if (roleName == null || StringUtils.hasText(roleName)) {
            return roleRepository.findByName(RoleConstant.ROLE_PREFIX + "USER")
                    .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));
        }
        return roleRepository.findByName(roleName)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));
    }

    @Transactional
    @Override
    public void delete(List<Integer> ids) {
        userRepository.deleteAllByIdInBatchCustom(ids);
    }

    @Override
    public UserDTO findById(Integer id) {
        return userMapper.toDTO(userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }

    @Override
    public UserDTO getMyInfo(HttpServletRequest request) {
        String jwt = getJwtFromRequest(request);
        if (!StringUtils.hasText(jwt) || !jwtProvider.validateToken(jwt)) {
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }
        Integer id = jwtProvider.extractId(jwt);
        return userMapper.toDTO(userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAll()
                .stream().map(userMapper::toDTO).toList();
    }

    @Override
    public List<UserDTO> findAll(Integer status) {
        return userRepository.findAllByStatus(status)
                .stream().map(userMapper::toDTO).toList();
    }

    @Override
    public Page<UserDTO> findWithSpec(UserSearch request) {
        SpecificationBuilder<UserEntity> builder = new SpecificationBuilder<>();
        Page<UserEntity> page;
        Specification<UserEntity> spec;

        if (StringUtils.hasText(request.getKeyword())) {
            builder.with("email", SearchOperation.CONTAINS, request.getKeyword(), true);
            builder.with("name", SearchOperation.CONTAINS, request.getKeyword(), true);
        }
        if (request.getProvider() != null) {
            builder.with("provider", SearchOperation.EQUALITY, request.getProvider(), false);
        }
        builder.with("status", SearchOperation.EQUALITY, request.getStatus(), false);
        spec = builder.build();
        if (request.getRoleId() != null) {
            List<SpecSearchCriteria> roleCriteria = new ArrayList<>();
            roleCriteria.add(new SpecSearchCriteria("id", SearchOperation.EQUALITY, request.getRoleId(), true));
            Specification<UserEntity> roleSpec = builder.joinTableWithCondition("roles", roleCriteria);
            spec = Specification.where(spec).and(roleSpec);
        }
        page = userRepository.findAll(Objects.requireNonNull(spec), Paging.getPageable(request));
        return page.map(userMapper::toDTO);
    }
}
