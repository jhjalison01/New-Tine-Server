package com.umc.NewTine.repository;


import com.umc.NewTine.domain.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {

}