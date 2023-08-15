package com.umc.NewTine.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import com.sun.istack.NotNull;

@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "news")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;

    @NotNull
    @Column
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

//    @NotNull
//    @Column
//    private long categoryId;

    @NotNull
    @Column
    private long pressId;

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
  
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="pressId")
    private Press press;
    //추가-현정 끝

    public News(Long id, String title, String content, long categoryId, long pressId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.pressId = pressId;
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

    public long getPressId() {
        return pressId;
    }

    public void setViews(int views) {
        this.views = views;
    }
}
