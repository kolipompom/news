package com.clane.publish.news.Repository;

import com.clane.publish.news.model.entity.Token;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author kolawole
 */
@Repository
public interface TokenRepository extends PagingAndSortingRepository<Token, Long> {

  Token findOneByToken(String token);

  Token findOneByAuthorAndToken(String userKey, String token);
}
