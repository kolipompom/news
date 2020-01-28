package com.clane.publish.news.Repository;


import com.clane.publish.news.model.entity.Author;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends PagingAndSortingRepository<Author, Long>{

    Author findOneById(Integer id);

    Author findOneByUniqueKey(String UniqueKey);

    Author findOneByEmail(String email);

    Author findOneByPhone(String phone);

    List<Author> findAll();

}
