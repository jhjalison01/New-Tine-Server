package com.umc.NewTine.dto.response;

import com.umc.NewTine.domain.Habit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HabitDto {

    private int nums;
}