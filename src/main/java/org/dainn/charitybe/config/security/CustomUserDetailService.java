package org.dainn.charitybe.config.security;

import lombok.RequiredArgsConstructor;
import org.dainn.charitybe.enums.ErrorCode;
import org.dainn.charitybe.exceptions.AppException;
import org.dainn.charitybe.models.UserEntity;
import org.dainn.charitybe.repositories.IRoleRepository;
import org.dainn.charitybe.repositories.IUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final IUserRepository userRepository;

    private final IRoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        return null;
    }
    public UserDetails loadUserById(Integer id) {
        Optional<UserEntity> userEntities = userRepository.findById(id);
        if (userEntities.isPresent()) {
            UserEntity user = userEntities.get();
            user.setRole(roleRepository.findById(user.getRole().getId())
                    .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED)
            ));
            return new CustomUserDetail(user);
        } else {
            return new CustomUserDetail("", "", "", new ArrayList<>());
        }
    }
}
