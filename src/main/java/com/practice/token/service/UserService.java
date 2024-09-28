package com.practice.token.service;

import com.practice.token.exception.ApplicationException;
import com.practice.token.exception.ErrorCode;
import com.practice.token.model.entity.User;
import com.practice.token.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User findUserOrElseThrow(String userName) {
        return userRepository.findByUsername(userName).orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));
    }
}
