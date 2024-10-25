package org.dainn.charitybe.utils;

import jakarta.servlet.http.Cookie;
import org.dainn.charitybe.constants.TokenConstant;

public class CookieUtil {
    public static Cookie createRefreshTokenCookie(String refreshToken) {
        System.out.println("refreshToken: " + refreshToken);
        Cookie refreshTokenCookie = new Cookie(TokenConstant.REFRESH_TOKEN, refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(false);
        refreshTokenCookie.setMaxAge(15 * 24 * 60 * 60);
        refreshTokenCookie.setPath("/");
        return refreshTokenCookie;
    }
}
