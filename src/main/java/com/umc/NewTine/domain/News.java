package com.umc.NewTine.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.List;
import java.time.LocalDateTime;
import com.sun.istack.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "news")
public class News extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String image;

    private String subject;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pressId")
    private Press press;

    @NotNull
    @Column
    private int views;

    @OneToMany(mappedBy = "news")
    private List<UserNewsHistory> userNewsHistories;

    //추가-현정
    @Column(columnDefinition = "TEXT")
    private String summary;

    @NotNull
    @Column
    private LocalDateTime createdAt;

    //추가-현정 끝

    public News(Long id, String title, String content, Press press) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.press = press;
        this.views = 0;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getViews() {
        return views;
    }


    public void setViews(int views) {
        this.views = views;
    }
}
