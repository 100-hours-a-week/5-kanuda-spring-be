package com.ktb.community.controller;

import com.ktb.community.auth.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        // 사용자 정보 로드
        UserDetails userDetails = customUserDetailService.loadUserByUsername(authenticationRequest.getEmail());

        if(!userDetails.getPassword().equals(authenticationRequest.getPassword())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("비밀번호가 올바르지 않습니다.");
        }

        // JWT 생성
        final String jwt = jwtUtil.generateToken(userDetails);

        // JWT를 포함한 응답 반환
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
     }
}
