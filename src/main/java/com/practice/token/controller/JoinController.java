package com.practice.token.controller;

import com.practice.token.controller.request.UserJoinRequest;
import com.practice.token.model.dto.UserDto;
import com.practice.token.service.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/join")
public class JoinController {
    private final JoinService joinService;
    @PostMapping
    public ResponseEntity<UserDto> join(@Valid @RequestBody UserJoinRequest request) {
        UserDto saved = joinService.join(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}
