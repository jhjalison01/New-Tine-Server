package com.umc.NewTine.service;


import com.umc.NewTine.domain.User;
import com.umc.NewTine.dto.response.NewsRecentResponse;
import com.umc.NewTine.repository.NewsRepository;
import com.umc.NewTine.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NewsService {
    private final NewsRepository newsRepository;
    private final UserRepository userRepository;

    public NewsService(NewsRepository newsRepository, UserRepository userRepository) {
            this.newsRepository = newsRepository;
            this.userRepository = userRepository;
        }

        @Transactional
        public List<NewsRecentResponse> getRecentNews(Long userId) {
            User user = userRepository.findById(userId)
                    .orElseThrow(IllegalArgumentException::new);

            return newsRepository.findByIdOrderByDateDesc(userId);
        }

}