package com.clane.publish.news.Repository;

import com.clane.publish.news.model.entity.News;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Kolawole
 */
@Repository
public interface NewsRepository extends PagingAndSortingRepository<News, Long> {

        News findByUniqueKey( String uniqueKey);
        List<News> findByAuthor( String authorKey);
        List<News> findAllByUniqueKeyAndAuthor(String uniqueKey, String authorKey);
        List<News> findAll();
}
