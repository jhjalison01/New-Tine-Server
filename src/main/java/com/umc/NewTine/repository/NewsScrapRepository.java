package com.umc.NewTine.repository;

import com.umc.NewTine.domain.NewsScrap;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsScrapRepository extends JpaRepository<NewsScrap,Long> {
    boolean existsByNewsIdAndUserId(Long newsId, Long userId);
}
