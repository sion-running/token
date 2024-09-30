package com.practice.token.controller;

import com.practice.token.controller.request.LoginRequest;
import com.practice.token.model.dto.TokenDto;
import com.practice.token.service.LoginService;
import com.practice.token.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/login")
public class LoginController {
    private final LoginService loginService;
    private final TokenService tokenService;

    @PostMapping
    public ResponseEntity<TokenDto> login(@Valid @RequestBody LoginRequest request) {
        TokenDto token = loginService.login(request);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/refresh")
    public ResponseEntity<TokenDto> refreshToken(@RequestHeader("Authorization") String authorizationHeader) {
        TokenDto token = tokenService.reissueRefreshToken(authorizationHeader.substring(7));
        return ResponseEntity.ok(token);
    }
}
