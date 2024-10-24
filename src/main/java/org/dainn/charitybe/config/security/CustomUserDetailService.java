package org.dainn.charitybe.config.security;

import lombok.RequiredArgsConstructor;
import org.dainn.charitybe.enums.ErrorCode;
import org.dainn.charitybe.enums.Provider;
import org.dainn.charitybe.exceptions.AppException;
import org.dainn.charitybe.models.UserEntity;
import org.dainn.charitybe.repositories.IRoleRepository;
import org.dainn.charitybe.repositories.IUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final IUserRepository userRepository;

    private final IRoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserEntity> userEntities = userRepository.findByEmailAndStatus(email, 1);
        if (userEntities.isPresent()) {
            UserEntity user = userEntities.get();
            user.setRole(roleRepository.findById(user.getRole().getId())
                    .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED)
            ));
            return new CustomUserDetail(user);
        } else {
            return new CustomUserDetail(email, "", "", new ArrayList<>());
        }
    }
    public UserDetails loadUserByUsernameAndProvider(String email, Provider provider) throws UsernameNotFoundException {
        Optional<UserEntity> userEntities = userRepository.findByEmailAndProviderAndStatus(email, provider, 1);
        if (userEntities.isPresent()) {
            UserEntity user = userEntities.get();
            user.setRole(roleRepository.findById(user.getRole().getId())
                    .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED)
            ));
            return new CustomUserDetail(user);
        } else {
            return new CustomUserDetail(email, "", "", new ArrayList<>());
        }
    }
}
