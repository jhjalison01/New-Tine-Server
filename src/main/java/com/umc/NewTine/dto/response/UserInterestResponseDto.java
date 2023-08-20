package com.umc.NewTine.dto.response;

import com.umc.NewTine.domain.UserInterest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInterestResponseDto {
    private String interest;

    public UserInterestResponseDto(UserInterest userInterest){
        this.interest = userInterest.getNewsCategory().getName();
    }
}
