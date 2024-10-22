package org.dainn.charitybe.services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.dainn.charitybe.dtos.TokenDTO;
import org.dainn.charitybe.dtos.response.JwtResponse;

public interface ITokenService {
    TokenDTO insert(TokenDTO dto);
    String getRefreshTokenFromReq(HttpServletRequest request);
    JwtResponse handleRefreshToken(String refreshToken, HttpServletResponse response);
    void deleteByUserIdAndDeviceInfo(Integer userId, String deviceInfo);
    TokenDTO findByRefreshToken(String refreshToken);

}
