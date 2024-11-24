package org.dainn.charitybe.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.dainn.charitybe.config.security.CustomUserDetailService;
import org.dainn.charitybe.constants.Endpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final CustomUserDetailService customUserDetailService;
    private final JwtProvider jwtProvider;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver exception;

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
            if (isByPassToken(request)) {
                filterChain.doFilter(request, response);
                return;
            }
            String jwt = getJwtFromRequest(request);
            if (!StringUtils.hasText(jwt) || !jwtProvider.validateToken(jwt)) {
                filterChain.doFilter(request, response);
                return;
            }
            Integer id = jwtProvider.extractId(jwt);
            UserDetails userDetails = customUserDetailService.loadUserById(id);
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
    private boolean isByPassToken(@NonNull HttpServletRequest request) {
        final String apiPrefix = Endpoint.API_PREFIX;
        final List<Pair<String, String>> byPassToken = Arrays.asList(
                Pair.of(String.format("%s/auth", apiPrefix), "POST"),
                Pair.of(String.format("%s/payment", apiPrefix), "GET"),
                Pair.of(String.format("%s/logout", apiPrefix), "POST"),
                Pair.of(String.format("%s/donations", apiPrefix), "POST")
        );
        for (Pair<String, String> item : byPassToken) {
            if (request.getRequestURI().contains(item.getFirst()) &&
                    request.getMethod().contains(item.getSecond())) {
                return true;
            }
        }
        return false;
    }
}
