package org.dainn.charitybe.services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.dainn.charitybe.dtos.UserDTO;
import org.dainn.charitybe.dtos.auth.UserLogin;
import org.dainn.charitybe.dtos.auth.UserRegister;
import org.dainn.charitybe.dtos.response.JwtResponse;

public interface IAuthService {
    JwtResponse login(UserLogin request, HttpServletResponse response);
    UserDTO register(UserRegister request);
    JwtResponse loginGoogle(HttpServletRequest request, HttpServletResponse response);
    void forgotPassword(String email);
}
