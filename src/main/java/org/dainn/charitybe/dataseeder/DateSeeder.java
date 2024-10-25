package org.dainn.charitybe.dataseeder;

import lombok.RequiredArgsConstructor;
import org.dainn.charitybe.constants.RoleConstant;
import org.dainn.charitybe.enums.Provider;
import org.dainn.charitybe.models.RoleEntity;
import org.dainn.charitybe.models.UserEntity;
import org.dainn.charitybe.repositories.IRoleRepository;
import org.dainn.charitybe.repositories.IUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DateSeeder implements CommandLineRunner {
    private final IRoleRepository roleRepository;
    private final IUserRepository userRepository;
    private final PasswordEncoder encoder;
    @Override
    public void run(String... args) {
        if (roleRepository.count() == 0) {
            RoleEntity adminRole = new RoleEntity();
            adminRole.setName(RoleConstant.ROLE_PREFIX + "ADMIN");
            RoleEntity userRole = new RoleEntity();
            userRole.setName(RoleConstant.ROLE_PREFIX + "USER");

            roleRepository.saveAll(List.of(adminRole, userRole));
            if (userRepository.count() == 0) {
                UserEntity admin = new UserEntity();
                admin.setEmail("admin@gmail.com");
                admin.setName("Administrator");
                admin.setPassword(encoder.encode("admin"));
                admin.setProvider(Provider.LOCAL);
                admin.setRole(adminRole);
                userRepository.save(admin);
            }
        }
    }
}
