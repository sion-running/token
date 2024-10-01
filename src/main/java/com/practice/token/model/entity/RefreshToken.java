package com.practice.token.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.annotation.Id;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash(value = "token", timeToLive = 120)
public class RefreshToken {
    @Id
    private String userName;

    private String token;
}
