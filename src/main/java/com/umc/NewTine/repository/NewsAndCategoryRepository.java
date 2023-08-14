package com.umc.NewTine.repository;

import com.umc.NewTine.domain.News;
import com.umc.NewTine.domain.NewsAndCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface NewsAndCategoryRepository extends JpaRepository<NewsAndCategory, Long> {
    @Query("SELECT DISTINCT n.news FROM NewsAndCategory n JOIN UserInterest u ON n.newsCategory.id = u.newsCategory.id WHERE u.user.id = :userId")
    Optional<List<News>> findNewsByUserInterest(@Param("userId") Long userId);
}
