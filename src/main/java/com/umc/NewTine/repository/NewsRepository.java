package com.umc.NewTine.repository;

import com.umc.NewTine.domain.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
<<<<<<< HEAD
=======

>>>>>>> origin/feature/#17-set-habit
import java.util.List;
import java.util.Optional;

public interface NewsRepository extends JpaRepository<News, Long> {
<<<<<<< HEAD

    Optional<News> findById(Long id);

    Optional<List<News>> findAllByOrderByViewsDesc();

    @Query("SELECT n FROM News n WHERE n.title LIKE %:word%")
    Optional<List<News>> findNewsByTitleContaining(@Param("word") String word);

    @Query(value = "select * from News LIMIT 5", nativeQuery = true)
    Optional<List<News>> findNews();

=======

    @Query(value = "select * from News LIMIT 5", nativeQuery = true)
    Optional<List<News>> findNews();
  
    Optional<News> findById(Long id);
  
    Optional<List<News>> findAllByOrderByViewsDesc();
    
    @Query("SELECT n FROM News n WHERE n.title LIKE %:word%")
    Optional<List<News>> findNewsByTitleContaining(@Param("word") String word);
  
>>>>>>> origin/feature/#17-set-habit
}
