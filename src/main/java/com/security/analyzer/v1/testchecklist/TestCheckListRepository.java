package com.security.analyzer.v1.testchecklist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TestCheckList entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TestCheckListRepository extends JpaRepository<TestCheckList, Long> {}
