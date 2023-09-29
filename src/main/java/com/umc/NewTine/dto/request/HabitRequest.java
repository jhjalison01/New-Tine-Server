package com.umc.NewTine.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class HabitRequest {

    private int nums;

    private List<String> days;

    private String hour;

    private String minute;


}
