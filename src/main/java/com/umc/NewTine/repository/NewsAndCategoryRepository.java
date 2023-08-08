package com.umc.NewTine.repository;

import com.umc.NewTine.domain.NewsAndCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NewsAndCategoryRepository extends JpaRepository<NewsAndCategory,Long>{
    List<NewsAndCategory> findByNewsId(Long newsId);

}
