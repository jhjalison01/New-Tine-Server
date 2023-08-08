package com.umc.NewTine.service;

import com.umc.NewTine.domain.News;
import com.umc.NewTine.domain.NewsAndCategory;
import com.umc.NewTine.domain.Press;
import com.umc.NewTine.dto.response.SingleNewsResponseDto;
import com.umc.NewTine.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class NewsService {
    private final NewsRepository newsRepository;
    private final NewsScrapRepository newsScrapRepository;
    private final PressSubscriptionRepository pressSubscriptionRepository;
    private final NewsCategoryRepository newsCategoryRepository;
    private final NewsAndCategoryRepository newsAndCategoryRepository;

    @Autowired
    public NewsService(NewsRepository newsRepository,PressSubscriptionRepository pressSubscriptionRepository,
                       NewsScrapRepository newsScrapRepository, NewsCategoryRepository newsCategoryRepository,
                       NewsAndCategoryRepository newsAndCategoryRepository) {
        this.newsRepository=newsRepository;
        this.pressSubscriptionRepository=pressSubscriptionRepository;
        this.newsScrapRepository=newsScrapRepository;
        this.newsCategoryRepository=newsCategoryRepository;
        this.newsAndCategoryRepository=newsAndCategoryRepository;
    }


    public SingleNewsResponseDto getSingleNewsById(Long newsId){

        News news = newsRepository.findById(newsId).orElse(null);
        if (news==null){
            //null 처리
            System.out.println("Service null");
            return null;

        }
        //userId 수정하기
        Long userId= 1L;

        Press press = news.getPress();
        boolean isScrapped=newsScrapRepository.existsByNewsIdAndUserId(newsId, userId);
        boolean isSubscribed= pressSubscriptionRepository.existsByPressIdAndUserId(press.getId(), userId);


        ArrayList<String> category = new ArrayList<String>(List.of());
        List<NewsAndCategory> newsAndCategoryList=newsAndCategoryRepository.findByNewsId(newsId);

        for(NewsAndCategory data: newsAndCategoryList){
            Long categoryId=data.getNewsCategory().getId();
            category.add(newsCategoryRepository.getById(categoryId).getName());
        }


        SingleNewsResponseDto singleNewsResponseDto=new SingleNewsResponseDto(
                news.getTitle(),
                news.getContent(),
                DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm").format(news.getCreatedAt()),
                press.getName(),
                press.getImage(),
                press.getSubscriber(),
                isSubscribed,
                isScrapped,
                category
        );

        return singleNewsResponseDto;
    }

}
