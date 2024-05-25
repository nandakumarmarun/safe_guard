package com.security.analyzer.v1.checklist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CheckList entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CheckListRepository extends JpaRepository<CheckList, Long> {}
