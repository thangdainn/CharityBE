package org.dainn.charitybe.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.dainn.charitybe.constants.Endpoint;
import org.dainn.charitybe.dtos.OtpDTO;
import org.dainn.charitybe.dtos.auth.ForgotPassword;
import org.dainn.charitybe.dtos.auth.ResetPassword;
import org.dainn.charitybe.dtos.auth.UserLogin;
import org.dainn.charitybe.dtos.auth.UserRegister;
import org.dainn.charitybe.services.IAuthService;
import org.dainn.charitybe.services.ITokenService;
import org.dainn.charitybe.services.IUserService;
import org.dainn.charitybe.services.impls.OtpService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Endpoint.Auth.BASE)
@RequiredArgsConstructor
public class AuthController {
    private final IAuthService authService;
    private final IUserService userService;
    private final ITokenService tokenService;
    private final OtpService otpService;

    @GetMapping(Endpoint.Auth.ME)
    public ResponseEntity<?> getById(HttpServletRequest request) {
        return ResponseEntity.ok(userService.getMyInfo(request));
    }

    @PostMapping(Endpoint.Auth.LOGIN)
    public ResponseEntity<?> login(@Valid @RequestBody UserLogin request, HttpServletResponse response) {
        return ResponseEntity.ok(authService.login(request, response));
    }

    @PostMapping(Endpoint.Auth.REGISTER)
    public ResponseEntity<?> register(@Valid @RequestBody UserRegister request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping(Endpoint.Auth.REFRESH_TOKEN)
    public ResponseEntity<?> refresh(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = tokenService.getRefreshTokenFromReq(request);
        return ResponseEntity.ok(tokenService.handleRefreshToken(refreshToken, response));
    }

    @PostMapping(Endpoint.Auth.LOGIN_GOOGLE)
    public ResponseEntity<?> googleLogin(HttpServletRequest request, HttpServletResponse response){
        return ResponseEntity.ok(authService.loginGoogle(request, response));
    }

    @PostMapping(Endpoint.Auth.FORGOT_PASSWORD)
    public ResponseEntity<?> forgotPassword(@RequestBody ResetPassword request) {
        authService.forgotPassword(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping(Endpoint.Auth.SEND_OTP)
    public ResponseEntity<?> sendOTP(@RequestBody ForgotPassword request) {
        authService.sendOtp(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping(Endpoint.Auth.VERIFY_OTP)
    public ResponseEntity<?> verifyOTP(@RequestBody OtpDTO request) {
        otpService.verify(request);
        return ResponseEntity.ok().build();
    }

}
