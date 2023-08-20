package com.umc.NewTine.dto.response;

import com.umc.NewTine.domain.User;
import com.umc.NewTine.domain.UserInterest;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserDetailResponseDto {

    private Long userId;
    private String nickname;
    private String email;
    private String name;
    private String imageUrl;
    private int point;
    private List<UserInterestResponseDto> userInterests;


    public UserDetailResponseDto(User user) {
        this.userId = user.getId();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.name = user.getName();
        this.point = user.getPoint();
        this.imageUrl = user.getImage().getUrl();
        this.userInterests = user.getUserInterests().stream().map(h -> new UserInterestResponseDto(h)).collect(Collectors.toList());
    }
}
