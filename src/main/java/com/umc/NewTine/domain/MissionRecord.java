package com.umc.NewTine.domain;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
public class MissionRecord extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;

    @JoinColumn(name="user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column
    private int mission_id;

    public MissionRecord(User user, int mission_id) {
        this.user = user;
        this.mission_id = mission_id;
    }

    //    @JoinColumn(name="mission_id")
//    @ManyToOne(fetch = FetchType.LAZY)
//    private Mission mission;

}
