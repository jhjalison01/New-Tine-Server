package com.umc.NewTine.repository;

import com.umc.NewTine.domain.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface NewsRepository extends JpaRepository<News, Long> {

//    @Query(value = "select * from news LIMIT 5", nativeQuery = true)
//    Optional<List<News>> findNews();

    @Query(value = "select * from News LIMIT 5", nativeQuery = true)
    Optional<List<News>> findNews();


}
