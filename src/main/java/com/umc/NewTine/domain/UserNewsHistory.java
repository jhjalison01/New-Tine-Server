package com.umc.NewTine.domain;

import com.sun.istack.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
public class UserNewsHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;

    @Column
    @NotNull
    private long user_id;

    @Column
    @NotNull
    private long news_id;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
}



