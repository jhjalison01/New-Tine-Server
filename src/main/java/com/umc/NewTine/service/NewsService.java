package com.umc.NewTine.service;

import com.umc.NewTine.domain.*;
import com.umc.NewTine.repository.*;
import com.umc.NewTine.dto.response.*;
import com.umc.NewTine.domain.News;
import com.umc.NewTine.dto.response.BaseException;
import com.umc.NewTine.dto.response.NewsDto;
import com.umc.NewTine.dto.request.NewsRecentRequest;
import javax.persistence.EntityNotFoundException;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import static com.umc.NewTine.dto.response.BaseResponseStatus.NO_NEWS_YET;
import static com.umc.NewTine.dto.response.BaseResponseStatus.NO_USER_ID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Collections;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewsService {
    private final NewsRepository newsRepository;
    private final NewsScrapRepository newsScrapRepository;
    private final PressSubscriptionRepository pressSubscriptionRepository;
    private final NewsCategoryRepository newsCategoryRepository;
    private final NewsAndCategoryRepository newsAndCategoryRepository;
    private final UserRepository userRepository;
    private final UserNewsHistoryRepository userNewsHistoryRepository;
    private final MissionRecordRepository missionRecordRepository;

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


    @Transactional
    public SingleNewsResponseDto getSingleNewsById(Long userId, Long newsId) throws BaseException {
        News news = newsRepository.findById(newsId)
                .orElseThrow(() -> new EntityNotFoundException("News not found with ID: " + newsId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Press press = news.getPress();
        boolean scrapped = false;
        boolean subscribed = false;

        if(userId!=null){
            scrapped = newsScrapRepository.existsByNewsIdAndUserId(newsId, userId);
            subscribed = pressSubscriptionRepository.existsByPressIdAndUserId(press.getId(), userId);
        }

        NewsAndCategory newsAndCategory = newsAndCategoryRepository.findByNewsId(newsId);
        String category = newsAndCategory.getNewsCategory().getName();

        if (!missionRecordRepository.existsByUserAndMissionId(user, 1)) {
            missionRecordRepository.save(new MissionRecord(user, 1));
            missionRecordRepository.findSuccessDailyMissionByUser(user);
        }

        String createdAtFormatted = "null";
        LocalDateTime createdAt = news.getCreatedAt();
        if (createdAt != null) {
            createdAtFormatted = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm").format(createdAt);
        }



        return SingleNewsResponseDto.builder()
                .title(news.getTitle())
                .content(news.getContent())
                .createdAt(createdAtFormatted)
                .pressName(press.getName())
                .pressImage(press.getImage())
                .pressSubscriber(press.getSubscriber())
                .subscribed(subscribed)
                .scrapped(scrapped)
                .category(category)
                .newsImg(news.getImage())
                .successMission(missionRecordRepository.findSuccessDailyMissionByUser(user))
                .newsId(news.getId())
                .build();
    }

    //카테고리별 뉴스 기사 조회
    @Transactional
    public List<NewsByCategoryResponse> getNewsByCategory(User user, String category) throws BaseException {
        NewsCategory newsCategory = newsCategoryRepository.findByName(category)
                .orElseThrow(() -> new BaseException(NO_USER_ID));

        Long categoryId = newsCategory.getId();
        List<News> news = newsAndCategoryRepository.findNewsByCategoryId(categoryId)
                .orElse(List.of());

        return news.stream()
                .map(data -> new NewsByCategoryResponse(data.getTitle(),data.getPress().getName(), data.getImage(),data.getContent(), data.getId(),data.getPress().getImage()))
                .collect(Collectors.toList());

    }

    //스크랩한 기사 가져오기
    @Transactional
    public List<ScrapNewsResponseDto> getScrappedNews(Long userId) throws BaseException {

        List<NewsScrap> newsScrapList = newsScrapRepository.findAllByUserId(userId);

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
    public boolean saveNewsScrap(Long userId, Long newsId) throws BaseException {
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
    public boolean deleteNewsScrap(Long userId, Long newsId) throws BaseException {
        NewsScrap newsScrap = newsScrapRepository.findByUserIdAndNewsId(userId, newsId)
                .orElseThrow(() -> new EntityNotFoundException("NewsScrap not found"));

        newsScrapRepository.delete(newsScrap);
        return true;
    }


    @Transactional //최근 본 뉴스 조회
    public List<NewsRecentResponse> getRecentNews(Long userId) throws BaseException {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(NO_USER_ID));
        List<News> newsList = userNewsHistoryRepository.findNewsByUserOrderByRecentViewTimeDesc(user)
                .orElse(List.of());
        return newsList.stream()
                .map(NewsRecentResponse::new)
                .limit(10)
                .collect(Collectors.toList());
    }

    @Transactional// 인기 뉴스 조회
    public List<NewsRankingResponse> getRankingNews() throws BaseException {
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

    @Transactional //추천 뉴스 조회
    public List<NewsRecommendResponse> getRecommendNews(Long userId) throws BaseException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(NO_USER_ID));
        List<News> newsList = newsAndCategoryRepository.findNewsByUserInterest(userId)
                .orElse(List.of());
        Collections.shuffle(newsList); //랜덤하게 4개 추천
        return newsList.stream()
                .map(NewsRecommendResponse::new)
                .limit(4)
                .collect(Collectors.toList());
    }


    @Transactional //사용자-뉴스 기록 저장, viewCount 증가
    public List<String> saveRecentViewTime(Long userId, NewsRecentRequest request) throws BaseException {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(NO_USER_ID));
        News news = newsRepository.findById(request.getNewsId())
                .orElseThrow(() -> new BaseException(NO_NEWS_YET));
        LocalDateTime recentViewTime = request.getRecentViewTime();
        LocalDateTime recentViewExitTime = request.getRecentViewExitTime();

        boolean isDuplicate = userNewsHistoryRepository.existsByUserAndNewsAndRecentViewTimeBetween(user, news, recentViewTime.minusMinutes(1), recentViewTime);

        if (!isDuplicate) {
            news.setViews(news.getViews() + 1);
        }

        UserNewsHistory userNewsHistory = userNewsHistoryRepository.findByUserAndNews(user, news)
                .orElseGet(() -> new UserNewsHistory(user, news, recentViewTime,recentViewExitTime));
        userNewsHistory.setRecentViewTime(recentViewTime);
        userNewsHistoryRepository.save(userNewsHistory);


        if (userNewsHistoryRepository.countTodayNewsViews(user) == 3) {
            //미션테이블기록
            missionRecordRepository.save(new MissionRecord(user,2));
            //미션기록 반환
            return missionRecordRepository.findSuccessDailyMissionByUser(user);

        }




        return Collections.emptyList();
    }
}
