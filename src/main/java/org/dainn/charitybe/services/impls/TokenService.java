package org.dainn.charitybe.services.impls;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.dainn.charitybe.dtos.TokenDTO;
import org.dainn.charitybe.dtos.response.JwtResponse;
import org.dainn.charitybe.enums.ErrorCode;
import org.dainn.charitybe.exceptions.AppException;
import org.dainn.charitybe.filters.JwtProvider;
import org.dainn.charitybe.mapper.ITokenMapper;
import org.dainn.charitybe.models.TokenEntity;
import org.dainn.charitybe.repositories.ITokenRepository;
import org.dainn.charitybe.repositories.IUserRepository;
import org.dainn.charitybe.services.ITokenService;
import org.dainn.charitybe.utils.CookieUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class TokenService implements ITokenService {
    private final ITokenRepository tokenRepository;
    private final IUserRepository userRepository;
    private final ITokenMapper tokenMapper;
    private final JwtProvider jwtProvider;

    @Transactional
    @Override
    public TokenDTO insert(TokenDTO dto) {
        TokenEntity tokenEntity = tokenMapper.toEntity(dto);
        tokenEntity.setUser(userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
        return tokenMapper.toDTO(tokenRepository.save(tokenEntity));
    }

    @Override
    public String getRefreshTokenFromReq(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals("refresh_token"))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);
    }

    @Transactional
    @Override
    public JwtResponse handleRefreshToken(String refreshToken, HttpServletResponse response) {
        TokenEntity tokenEntity = tokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new AppException(ErrorCode.REFRESH_NOT_EXISTED));
        if (tokenEntity.getRefreshTokenExpirationDate().before(new Date())) {
            tokenRepository.deleteById(tokenEntity.getId());
            throw new AppException(ErrorCode.REFRESH_TOKEN_EXPIRED);
        }
        String accessToken = jwtProvider.generateToken(tokenEntity.getUser().getEmail(), tokenEntity.getUser().getProvider());
        String refreshNewToken = jwtProvider.generateRefreshToken();
        tokenRepository.updateRefreshToken(refreshNewToken, tokenEntity.getId());
        response.addCookie(CookieUtil.createRefreshTokenCookie(refreshNewToken));
        return new JwtResponse(accessToken);
    }

    @Transactional
    @Override
    public void deleteByUserIdAndDeviceInfo(Integer userId, String deviceInfo) {
        tokenRepository.deleteByUser_Id(userId);
    }

    @Override
    public TokenDTO findByRefreshToken(String refreshToken) {
        return tokenMapper.toDTO(tokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new AppException(ErrorCode.REFRESH_NOT_EXISTED)));
    }
}
