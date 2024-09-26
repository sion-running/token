package com.practice.token.service;

import com.practice.token.exception.ApplicationException;
import com.practice.token.exception.ErrorCode;
import com.practice.token.model.dto.UserDto;
import com.practice.token.model.entity.User;
import com.practice.token.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private UserRepository userRepository;

    public UserDto findUserOrElseThrow(String userName) {
        User user = userRepository.findByUserName(userName).orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));
        return UserDto.fromEntity(user);
    }
}
