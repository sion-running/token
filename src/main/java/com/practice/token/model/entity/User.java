package com.practice.token.model.entity;

import com.practice.token.controller.request.UserJoinRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name", unique = true)
    private String userName;

    @Column(name = "password")
    private String password;

    public static User toEntity(UserJoinRequest request, String encodedPassword) {
        return User.builder()
                .userName(request.getUserName())
                .password(encodedPassword)
                .build();
    }
}
