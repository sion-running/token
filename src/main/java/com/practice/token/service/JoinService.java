package com.practice.token.service;


import com.practice.token.controller.request.UserJoinRequest;
import com.practice.token.exception.ApplicationException;
import com.practice.token.exception.ErrorCode;
import com.practice.token.model.dto.UserDto;
import com.practice.token.model.entity.User;
import com.practice.token.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JoinService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public UserDto join(UserJoinRequest request) {
        userRepository.findByUserName(request.getUserName()).ifPresent(it -> {
            throw new ApplicationException(ErrorCode.DUPLICATE_USER_NAME, request.getUserName());
        });

        String encodedPassword = encoder.encode(request.getPassword());
        User user = User.toEntity(request, encodedPassword);
        userRepository.save(user);

        return UserDto.fromEntity(user);
    }
}
