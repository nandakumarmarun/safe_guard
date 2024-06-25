package com.security.analyzer.v1.test;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the SecurityTest entity.
 */
@Repository
public interface TestRepository extends JpaRepository<Test, Long> {

    List<Test> findAllByTestID(String testId);

    List<Test> findAllByApplicationUserId(Long companyid);


}
