package com.security.analyzer.v1.test;

import com.security.analyzer.v1.Enum.SecurityLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the SecurityTest entity.
 */
@Repository
public interface TestRepository extends JpaRepository<Test, Long> {


}
