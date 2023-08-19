package com.umc.NewTine.dto.response;

import com.umc.NewTine.domain.User;

public class UserDetailResponseDto {

    private Long userId;
    private String nickname;
    private String email;


    public UserDetailResponseDto(User user) {
        this.userId = user.getId();
        this.nickname = user.getNickname();
        this.email = user.getEmail();

    }
}
