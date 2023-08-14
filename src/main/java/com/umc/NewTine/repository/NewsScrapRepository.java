package com.umc.NewTine.repository;

import com.umc.NewTine.domain.NewsScrap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NewsScrapRepository extends JpaRepository<NewsScrap,Long> {
    boolean existsByNewsIdAndUserId(Long newsId, Long userId);

    List<NewsScrap> findAllByUserId(Long userId);
    Optional<NewsScrap> findByUserIdAndNewsId(Long userId, Long newsId);
}
