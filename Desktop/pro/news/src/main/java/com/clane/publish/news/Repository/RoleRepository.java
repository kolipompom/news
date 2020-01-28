package com.clane.publish.news.Repository;

import com.clane.publish.news.model.constants.Status;
import com.clane.publish.news.model.entity.Role;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author kolawole
 */
@Repository
public interface RoleRepository extends PagingAndSortingRepository<Role, Long>{

  List<Role> findAllByUniqueKey(String uniqueKey);

  List<Role> findAll();

  Role findOneByUniqueKey(String uniqueKey);

  Role findOneById(Integer id);

  List<Role> findAllByIsHidden(Status isHidden);
}
