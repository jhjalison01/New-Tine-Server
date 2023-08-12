package com.umc.NewTine.repository;

import com.umc.NewTine.domain.News;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NewsRepository extends JpaRepository<News, Long> {
    Optional<News> findById(Long id);

}


