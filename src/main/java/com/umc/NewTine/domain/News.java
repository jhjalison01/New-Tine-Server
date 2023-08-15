package com.umc.NewTine.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import com.sun.istack.NotNull;

@NoArgsConstructor
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

    @NotNull
    @Column
    private long category_id;

    @NotNull
    @Column
    private long press_id;

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
    @Builder
    public News(Long id, String title, String content, long category_id, long press_id) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.category_id = category_id;
        this.press_id = press_id;
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

    public long getPress_id() {
        return press_id;
    }

    public void setViews(int views) {
        this.views = views;
    }
}
