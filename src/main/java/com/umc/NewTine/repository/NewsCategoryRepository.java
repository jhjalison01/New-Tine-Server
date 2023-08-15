package com.umc.NewTine.repository;

import com.umc.NewTine.domain.NewsCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsCategoryRepository extends JpaRepository<NewsCategory,Long> {
}
