package com.umc.NewTine.domain;

import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;

    @NotNull
    @Column
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @NotNull
    @Column
    private Long category_id;

    @NotNull
    @Column
    private Long press_id;

    @NotNull
    @Column
    private Integer view;



}