package com.ktb.community.auth;

import com.ktb.community.domain.entity.User;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");

        String email = null;
        String jwt = null;

        // Authorization 헤더에서 JWT 추출
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            try {
                email = jwtUtil.extractUsername(jwt);
            } catch (ExpiredJwtException e) {
                logger.error("JWT token is expired", e);
            } catch (Exception e) {
                logger.error("Error parsing JWT token", e);
            }
        }

        // JWT 검증 및 인증 설정
        if(email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            User user = new User();
            user.setEmail(email);
            user.setPassword("password");

            CustomUserDetails customUserDetails = new CustomUserDetails(user);

            if(jwtUtil.validateToken(jwt, customUserDetails)) {
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        customUserDetails, null, customUserDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}
