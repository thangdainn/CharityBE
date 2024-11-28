package org.dainn.charitybe.config.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dainn.charitybe.models.RoleEntity;
import org.dainn.charitybe.models.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomUserDetail implements UserDetails {
    private String email;
    private String password;
    private String name;
    private List<GrantedAuthority> authorities;


    public CustomUserDetail(UserEntity userEntity) {
        email = userEntity.getEmail();
        password = userEntity.getPassword();
        name = userEntity.getName();
        authorities = List.of(new SimpleGrantedAuthority(userEntity.getRole().getName()));
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
