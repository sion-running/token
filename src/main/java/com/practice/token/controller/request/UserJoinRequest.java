package com.practice.token.controller.request;

import lombok.Getter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
public class UserJoinRequest {
    @Size(min = 6, max = 10, message = "아이디는 6자 이상 10자 이하로 입력해주세요.")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "아이디는 영문 대소문자와 숫자만 허용됩니다.")
    private String userName;

    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    private String password;
}
