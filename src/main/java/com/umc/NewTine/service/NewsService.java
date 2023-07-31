package com.umc.NewTine.service;

import com.umc.NewTine.domain.News;
import com.umc.NewTine.dto.response.BaseException;
import com.umc.NewTine.dto.response.NewsDto;
import com.umc.NewTine.repository.NewsRepository;
import com.umc.NewTine.repository.PressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static com.umc.NewTine.dto.response.BaseResponseStatus.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;
    private final PressRepository pressRepository;

    @Transactional(readOnly = true)
    public List<NewsDto> getHomeNews() throws BaseException {
        List<News> allNews = newsRepository.findNews().get();

        if (allNews.isEmpty()) {
            throw new BaseException(NO_NEWS_YET);
        } else {
            return allNews.stream()
                    .map(news -> NewsDto.from(news, news.getPress()))
                    .collect(Collectors.toList());
        }
    }


}
