package com.practice.token.service;

import com.practice.token.exception.ApplicationException;
import com.practice.token.exception.ErrorCode;
import com.practice.token.model.dto.TokenDto;
import com.practice.token.model.entity.RefreshToken;
import com.practice.token.repository.RefreshTokenRepository;
import com.practice.token.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenUtil jwtTokenUtil;

    public TokenDto getToken(String userName) {
        String refreshToken = generateRefreshToken(userName);

        return TokenDto.builder()
                .accessToken(jwtTokenUtil.createAccessToken(userName))
                .refreshToken(refreshToken)
                .build();
    }

    public String generateRefreshToken(String userName) {
        deleteRefreshTokenByUserName(userName);

        String token = jwtTokenUtil.createRefreshToken(userName);
        refreshTokenRepository.save(RefreshToken.builder().userName(userName).token(token).build());

        return token;
    }

    public TokenDto reissueRefreshToken(String refreshToken) {
        try {
            validateRefreshToken(refreshToken);
        } catch (Exception e) {
            throw new ApplicationException(ErrorCode.INVALID_TOKEN);
        }

        return getToken(jwtTokenUtil.getUsernameByToken(refreshToken));
    }

    public void deleteRefreshTokenByUserName(String userName) {
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findById(userName);
        refreshToken.ifPresent(refreshTokenRepository::delete);
    }

    public void validateRefreshToken(String refreshToken) {
        String userName = jwtTokenUtil.getUsernameByToken(refreshToken);
        RefreshToken existing = refreshTokenRepository.findById(userName).get();

        if (!existing.getToken().equals(refreshToken)) {
            throw new ApplicationException(ErrorCode.INVALID_TOKEN);
        }
    }
}
