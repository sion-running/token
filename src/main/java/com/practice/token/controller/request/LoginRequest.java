package com.practice.token.controller.request;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class LoginRequest {
    @NotEmpty
    private String userName;
    @NotEmpty
    private String password;
}
