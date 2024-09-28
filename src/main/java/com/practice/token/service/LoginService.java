package com.practice.token.service;

import com.practice.token.controller.request.LoginRequest;
import com.practice.token.exception.ApplicationException;
import com.practice.token.exception.ErrorCode;
import com.practice.token.model.dto.TokenDto;
import com.practice.token.model.entity.User;
import com.practice.token.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    public TokenDto login(LoginRequest request) {
        User user = userService.findUserOrElseThrow(request.getUserName());

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ApplicationException(ErrorCode.INVALID_PASSWORD);
        }

        return TokenDto.builder()
                .accessToken(jwtTokenUtil.generateAccessToken(user.getUsername()))
                .refreshToken(jwtTokenUtil.generateRefreshToken())
                .build();
    }
}
