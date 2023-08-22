package com.umc.NewTine.domain;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "newsScrap")
public class NewsScrap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="newsId")
    private News news;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userId")
    private User user;
}
