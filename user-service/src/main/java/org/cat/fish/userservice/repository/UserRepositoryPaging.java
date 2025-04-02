package org.cat.fish.userservice.repository;

import org.cat.fish.userservice.model.entity.User;
//import org.jspecify.annotations.NullMarked;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

//@NullMarked
@Repository
public interface UserRepositoryPaging extends PagingAndSortingRepository<User, Long> {
    Page<User> findAll(Pageable pageable);
}
