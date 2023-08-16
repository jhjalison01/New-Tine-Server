package com.umc.NewTine.dto.response;

import com.umc.NewTine.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserUpdateResponseDto {
    private Long userId;
    private String nickname;

    private String message;


    public UserUpdateResponseDto(User user, String message) {
        this.userId = user.getId();
        this.nickname = user.getNickname();
        this.message= message ;
    }
}