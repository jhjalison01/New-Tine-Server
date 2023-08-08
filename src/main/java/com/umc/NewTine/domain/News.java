package com.umc.NewTine.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "news")
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private String summary;
    private LocalDateTime createdAt;
    private int views;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="pressId")
    private Press press;

}
