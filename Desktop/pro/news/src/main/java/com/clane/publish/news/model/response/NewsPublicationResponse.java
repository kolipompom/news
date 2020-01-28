package com.clane.publish.news.model.response;

import com.clane.publish.news.model.constants.Status;
import com.clane.publish.news.model.entity.News;
import com.clane.publish.news.util.TimeUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Kolawole
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewsPublicationResponse {

    private String news;
    private String description;
    private Status status;
    private String createdAt;
    private String updatedAt;

    public static NewsPublicationResponse fromPublishedNews(News publishedNews){
        return NewsPublicationResponse.builder()
                .news(publishedNews.getFile())
                .description(publishedNews.getDescription())
                .status(publishedNews.getStatus())
                .createdAt(TimeUtil.getIsoTime(publishedNews.getCreatedAt()))
                .updatedAt(TimeUtil.getIsoTime(publishedNews.getUpdatedAt()))
                .build();
    }

    public static List<NewsPublicationResponse> fromAllPublichedNews(List<News> newsToReturn) {
        return newsToReturn.stream().map(news -> {
            return fromPublishedNews(news);
        }).collect(
                Collectors.toList());
    }
}
