package com.umc.NewTine.repository;


import com.umc.NewTine.domain.News;
import com.umc.NewTine.dto.response.NewsRecentResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface NewsRepository extends JpaRepository<News, Long> {
    List<NewsRecentResponse> findByIdOrderByDateDesc(Long userId);
}


