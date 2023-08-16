package com.umc.NewTine.dto.request;

import com.umc.NewTine.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class UserUpdateRequestDto {
    private String nickname;
    private String image;
    private String interest;

    @Builder
    public UserUpdateRequestDto(User user) {
        this.nickname = user.getNickname();
        this.image = user.getImage();
        this.interest = user.getInterest();
    }
}
