package com.umc.NewTine.repository;

import com.umc.NewTine.domain.News;
import com.umc.NewTine.domain.User;
import com.umc.NewTine.domain.UserNewsHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserNewsHistoryRepository extends JpaRepository<UserNewsHistory, Long> {
    @Query("SELECT unh.news FROM UserNewsHistory unh WHERE unh.user = :user ORDER BY unh.recentViewTime DESC")
    Optional<List<News>> findNewsByUserOrderByRecentViewTimeDesc(@Param("user") User user);

    Optional<UserNewsHistory> findByUserAndNews(User user, News news);

}