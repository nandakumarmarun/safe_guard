package com.security.analyzer.v1.user;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Range;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface UserRepository  extends JpaRepository<User,Long> {


    Optional<User> findOneById(Long id);

    Optional<User> findByLogin(String login);

    Optional<User> findOneByLogin(String login);

    List<User> findAllByActivatedIsFalse();

    Optional<User> findOneWithRolesByLogin(String login);

    Page<User> findAllByIdNotNullAndActivatedIsTrue(Pageable pageable);

    Optional<User> findOneWithAuthoritiesByLogin(String login);

    Optional<User> findOneByEmailIgnoreCase(String email);


}
