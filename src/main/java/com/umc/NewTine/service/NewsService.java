package com.umc.NewTine.service;

import com.umc.NewTine.domain.*;
import com.umc.NewTine.repository.*;
import com.umc.NewTine.dto.response.*;
import static com.umc.NewTine.dto.response.BaseResponseStatus.NO_NEWS_YET;
import static com.umc.NewTine.dto.response.BaseResponseStatus.NO_USER_ID;
import com.umc.NewTine.dto.request.NewsRecentRequest;
import javax.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewsService {
    private final NewsRepository newsRepository;
    private final NewsScrapRepository newsScrapRepository;
    private final PressSubscriptionRepository pressSubscriptionRepository;
    private final NewsCategoryRepository newsCategoryRepository;
    private final NewsAndCategoryRepository newsAndCategoryRepository;
    private final UserRepository userRepository;
    private final UserNewsHistoryRepository userNewsHistoryRepository;


    @Autowired
    public NewsService(NewsRepository newsRepository,PressSubscriptionRepository pressSubscriptionRepository,
                       NewsScrapRepository newsScrapRepository, NewsCategoryRepository newsCategoryRepository,
                       NewsAndCategoryRepository newsAndCategoryRepository, UserRepository userRepository,
                       UserNewsHistoryRepository userNewsHistoryRepository) {
        this.newsRepository=newsRepository;
        this.pressSubscriptionRepository=pressSubscriptionRepository;
        this.newsScrapRepository=newsScrapRepository;
        this.newsCategoryRepository=newsCategoryRepository;
        this.newsAndCategoryRepository=newsAndCategoryRepository;
        this.userRepository = userRepository;
        this.userNewsHistoryRepository = userNewsHistoryRepository;
    }
    


  

    @Transactional
    public SingleNewsResponseDto getSingleNewsById(Long userId,Long newsId) throws BaseException {
        News news = newsRepository.findById(newsId)
                .orElseThrow(() -> new EntityNotFoundException("News not found with ID: " + newsId));

        Press press = news.getPress();
        boolean scrapped = newsScrapRepository.existsByNewsIdAndUserId(newsId, userId);
        boolean subscribed = pressSubscriptionRepository.existsByPressIdAndUserId(press.getId(), userId);

        List<NewsAndCategory> newsAndCategoryList = newsAndCategoryRepository.findByNewsId(newsId);
        List<String> category = newsAndCategoryList.stream()
                .map(data -> newsCategoryRepository.getById(data.getNewsCategory().getId()).getName())
                .collect(Collectors.toList());

        return SingleNewsResponseDto.builder()
                .title(news.getTitle())
                .content(news.getContent())
                .createdAt(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm").format(news.getCreatedAt()))
                .pressName(press.getName())
                .pressImage(press.getImage())
                .pressSubscriber(press.getSubscriber())
                .subscribed(subscribed)
                .scrapped(scrapped)
                .category(category)
                .build();
    }
  
    //스크랩한 기사 가져오기
    @Transactional
    public List<ScrapNewsResponseDto> getScrappedNews(Long userId) throws BaseException {

        List<NewsScrap> newsScrapList=newsScrapRepository.findAllByUserId(userId);

        return newsScrapList.stream()
                .map(this::mapNewsScrapToResponseDto)
                .collect(Collectors.toList());
    }
    //뉴스 제목, 생성 날짜, 언론사 이름 매핑
    private ScrapNewsResponseDto mapNewsScrapToResponseDto(NewsScrap newsScrap) {
        News news = newsScrap.getNews();
        Press press = news.getPress();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
        String formattedDate = formatter.format(news.getCreatedAt());

        return new ScrapNewsResponseDto(news.getTitle(), formattedDate, press.getName());
    }

    //newsScrap 저장
    @Transactional
    public boolean saveNewsScrap(Long userId,Long newsId) throws BaseException{
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));

        News news = newsRepository.findById(newsId)
                .orElseThrow(() -> new EntityNotFoundException("News not found with ID: " + newsId));

        NewsScrap newsScrap = new NewsScrap();
        newsScrap.setUser(user);
        newsScrap.setNews(news);

        newsScrapRepository.save(newsScrap);

        return true;
    }

    //newsScrap 삭제
    @Transactional
    public boolean deleteNewsScrap(Long userId, Long newsId) throws BaseException{
        NewsScrap newsScrap = newsScrapRepository.findByUserIdAndNewsId(userId, newsId)
                .orElseThrow(() -> new EntityNotFoundException("NewsScrap not found"));

        newsScrapRepository.delete(newsScrap);
        return true;
    }
   

    @Transactional //최근 본 뉴스 조회
    public List<NewsRecentResponse> getRecentNews(Long userId) throws BaseException {

        User user = userRepository.findById(userId)
                .orElseThrow(()->new BaseException(NO_USER_ID));
        List<News> newsList = userNewsHistoryRepository.findNewsByUserOrderByRecentViewTimeDesc(user)
                .orElse(List.of());
        return newsList.stream()
                .map(NewsRecentResponse::new)
                .limit(10)
                .collect(Collectors.toList());
    }

    @Transactional// 인기 뉴스 조회
    public List<NewsRankingResponse> getRankingNews() throws BaseException{
        List<News> newsList = newsRepository.findAllByOrderByViewsDesc()
                .orElse(List.of());
        return newsList.stream()
                .map(NewsRankingResponse::new)
                .limit(3)
                .collect(Collectors.toList());
    }

    @Transactional //검색어를 포함하는 뉴스 기사 조회
    public List<NewsSearchByWordResponse> searchNewsByWord(String word) throws BaseException {
        List<News> newsList = newsRepository.findNewsByTitleContaining(word)
                .orElse(List.of());
        return newsList.stream()
                .map(NewsSearchByWordResponse::new)
                .limit(5)
                .collect(Collectors.toList());
    }

    @Transactional //사용자-뉴스 기록 저장, viewCount 증가
    public boolean saveRecentViewTime(NewsRecentRequest request) throws BaseException{

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(()->new BaseException(NO_USER_ID));
        News news = newsRepository.findById(request.getNewsId())
                .orElseThrow(()->new BaseException(NO_NEWS_YET));
        LocalDateTime recentViewTime = LocalDateTime.now();
        boolean isDuplicate = userNewsHistoryRepository.existsByUserAndNewsAndRecentViewTimeBetween(user, news, recentViewTime.minusMinutes(1), recentViewTime);

        if (!isDuplicate) {
            news.setViews(news.getViews() + 1);
        }

        UserNewsHistory userNewsHistory = userNewsHistoryRepository.findByUserAndNews(user, news)
                .orElseGet(() -> new UserNewsHistory(user, news, recentViewTime));
        userNewsHistory.setRecentViewTime(recentViewTime);
        userNewsHistoryRepository.save(userNewsHistory);

        return true;
    }


}

