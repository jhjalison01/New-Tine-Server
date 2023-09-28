package com.umc.NewTine.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
@Table(name = "habit")
public class Habit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int nums;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String days;

    private String ampm;

    private String hour;

    private String minute;

    public void updateNums(int nums) {
        this.nums = nums;
    }


}