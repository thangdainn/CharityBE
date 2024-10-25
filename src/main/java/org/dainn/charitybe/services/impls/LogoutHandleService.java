package org.dainn.charitybe.services.impls;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.dainn.charitybe.constants.TokenConstant;
import org.dainn.charitybe.repositories.ITokenRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class LogoutHandleService implements LogoutHandler {
    private final ITokenRepository tokenRepository;

    @Transactional
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Cookie[] cookies = request.getCookies();
        String refreshToken = Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(TokenConstant.REFRESH_TOKEN))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);
        if (refreshToken == null) {
            return;
        }
        tokenRepository.deleteByRefreshToken(refreshToken);
        Cookie cookie = new Cookie(TokenConstant.REFRESH_TOKEN, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
