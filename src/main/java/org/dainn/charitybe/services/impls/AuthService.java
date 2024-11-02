package org.dainn.charitybe.services.impls;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.dainn.charitybe.constants.RoleConstant;
import org.dainn.charitybe.dtos.TokenDTO;
import org.dainn.charitybe.dtos.UserDTO;
import org.dainn.charitybe.dtos.auth.UserLogin;
import org.dainn.charitybe.dtos.auth.UserRegister;
import org.dainn.charitybe.dtos.response.JwtResponse;
import org.dainn.charitybe.enums.ErrorCode;
import org.dainn.charitybe.enums.Provider;
import org.dainn.charitybe.exceptions.AppException;
import org.dainn.charitybe.filters.JwtProvider;
import org.dainn.charitybe.mapper.IUserMapper;
import org.dainn.charitybe.models.UserEntity;
import org.dainn.charitybe.repositories.IRoleRepository;
import org.dainn.charitybe.repositories.IUserRepository;
import org.dainn.charitybe.services.IAuthService;
import org.dainn.charitybe.services.ITokenService;
import org.dainn.charitybe.services.IUserService;
import org.dainn.charitybe.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {
    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final IUserService userService;
    private final ITokenService tokenService;
    private final PasswordEncoder encoder;
    private final JwtProvider jwtProvider;
    private final IUserMapper userMapper;

    @Value("${jwt.refresh.expiration}")
    private Long expirationRefresh;

    @Value("${google.clientId}")
    private String googleClientId;

    @Override
    public JwtResponse login(UserLogin request, HttpServletResponse response) {
        UserEntity userEntity = userRepository.findByEmailAndProviderAndStatus(request.getEmail(), Provider.LOCAL, 1)
                .orElseThrow(() -> new AppException(ErrorCode.EMAIL_IS_INCORRECT));
        if (!encoder.matches(request.getPassword(), userEntity.getPassword())) {
            throw new AppException(ErrorCode.PASSWORD_IS_INCORRECT);
        }
        String accessToken = jwtProvider.generateToken(userEntity);
        String refreshToken = jwtProvider.generateRefreshToken();
        TokenDTO tokenDTO = createTokenDTO(refreshToken, userEntity.getId());
        tokenService.insert(tokenDTO);
        response.addCookie(CookieUtil.createRefreshTokenCookie(refreshToken));
        return new JwtResponse(accessToken);
    }

    @Override
    public UserDTO register(UserRegister request) {
        return userService.insert(userMapper.toDTO(request));
    }

    @Override
    public JwtResponse loginGoogle(HttpServletRequest request, HttpServletResponse response) {
        try {
            String token = getTokenFromRequest(request);
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                    .setAudience(Collections.singleton(googleClientId))
                    .build();
            GoogleIdToken idToken = verifier.verify(token);
            GoogleIdToken.Payload payload = idToken.getPayload();
            String email = payload.getEmail();
            String name = (String) payload.get("name");
            Optional<UserEntity> optional = userRepository.findByEmailAndProviderAndStatus(email, Provider.GOOGLE, 1);
            UserEntity userEntity = new UserEntity();
            if (optional.isEmpty()) {
                userEntity.setEmail(email);
                userEntity.setName(name);
                userEntity.setPassword(encoder.encode("dainn"));
                userEntity.setProvider(Provider.GOOGLE);
                userEntity.setRole(roleRepository.findByName(RoleConstant.ROLE_PREFIX + "USER")
                        .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED)));
            } else {
                userEntity = optional.get();
            }
            userEntity = userRepository.save(userEntity);
            String accessToken = jwtProvider.generateToken(userEntity);
            String refreshToken = jwtProvider.generateRefreshToken();
            TokenDTO tokenDTO = createTokenDTO(refreshToken, userEntity.getId());
            tokenService.insert(tokenDTO);
            response.addCookie(CookieUtil.createRefreshTokenCookie(refreshToken));
            return new JwtResponse(accessToken);
        } catch (Exception e) {
            throw new AppException(ErrorCode.GOOGLE_LOGIN_FAILED);
        }
    }

    private TokenDTO createTokenDTO(String refreshToken, Integer userId) {
        return TokenDTO.builder()
                .refreshToken(refreshToken)
                .refreshTokenExpirationDate(new Date(new Date().getTime() + expirationRefresh))
                .userId(userId)
                .build();
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
