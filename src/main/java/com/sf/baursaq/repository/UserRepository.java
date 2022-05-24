package com.sf.baursaq.repository;

import com.sf.baursaq.entity.User;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
    @Transactional
    Long deleteByUsername(String username);
}
