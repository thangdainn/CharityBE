package org.dainn.charitybe.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.dainn.charitybe.config.security.CustomUserDetailService;
import org.dainn.charitybe.enums.Provider;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final CustomUserDetailService customUserDetailService;
    private final JwtProvider jwtProvider = new JwtProvider();
    private final HandlerExceptionResolver exception;

    public JwtAuthenticationFilter(HandlerExceptionResolver exception, CustomUserDetailService customUserDetailService) {
        this.exception = exception;
        this.customUserDetailService = customUserDetailService;
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,@NonNull HttpServletResponse response,@NonNull FilterChain filterChain) {
        try {
            String jwt = getJwtFromRequest(request);
            if (!StringUtils.hasText(jwt) || !jwtProvider.validateToken(jwt)) {
                filterChain.doFilter(request, response);
                return;
            }

            String userName = jwtProvider.extractEmail(jwt);
            String provider = jwtProvider.extractProvider(jwt);
            UserDetails userDetails = customUserDetailService.loadUserByUsernameAndProvider(userName, Provider.valueOf(provider));
            if (userDetails != null) {
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            exception.resolveException(request, response, null, e);
        }

    }
}
