package com.umc.NewTine.dto.request;

import com.umc.NewTine.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class UserUpdateRequestDto {
    private String nickname;
    private String name;
    private int point;

    @Builder
    public UserUpdateRequestDto(User user) {
        this.nickname = user.getNickname();
        this.name = user.getName();
        this.point = user.getPoint();
    }
}
