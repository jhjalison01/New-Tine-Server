package com.umc.NewTine.service;

import com.umc.NewTine.domain.News;
import com.umc.NewTine.domain.NewsAndCategory;
import com.umc.NewTine.domain.NewsScrap;
import com.umc.NewTine.domain.Press;
import com.umc.NewTine.dto.response.BaseException;
import com.umc.NewTine.dto.response.ScrapNewsResponseDto;
import com.umc.NewTine.dto.response.SingleNewsResponseDto;
import com.umc.NewTine.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    //스크랩한 기사 가져오기
    public List<ScrapNewsResponseDto> getScrapNews() throws BaseException {
        //userId 수정하기
        Long userId=1L;
        List<NewsScrap> newsScrapList=newsScrapRepository.findAllByUserId(userId);

        return newsScrapList.stream()
                .map(this::mapNewsScrapToResponseDto)
                .collect(Collectors.toList());
    }
    //map 메소드에 쓸 함수 정의
    private ScrapNewsResponseDto mapNewsScrapToResponseDto(NewsScrap newsScrap) {
        News news = newsScrap.getNews();
        Press press = news.getPress();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
        String formattedDate = formatter.format(news.getCreatedAt());

        return new ScrapNewsResponseDto(news.getTitle(), formattedDate, press.getName());
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
