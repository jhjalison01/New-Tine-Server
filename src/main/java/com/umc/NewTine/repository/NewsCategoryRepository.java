package com.umc.NewTine.repository;

import com.umc.NewTine.domain.NewsCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NewsCategoryRepository extends JpaRepository<NewsCategory,Long> {

    Optional<NewsCategory> findByName(String name);
}
