package com.security.analyzer.v1.dashboard;

import com.security.analyzer.v1.Enum.SecurityLevel;
import com.security.analyzer.v1.securitytest.SecurityTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DashboardRepository extends JpaRepository<Dashboard ,Long> {

    Optional<Dashboard> findOneByTestID(String Test);

    @Query(value = "SELECT Count(*) FROM tbl_dashboard st WHERE security_level = ?1 and user_id = ?2", nativeQuery = true)
    long countBySecurityLevel(String securityLevel, Long userid);

    @Query(value = "SELECT * FROM tbl_dashboard st WHERE user_id = ?1 limit 10", nativeQuery = true)
    List<Dashboard> findAllTest(Long id);

    @Query(value = "SELECT * FROM tbl_dashboard st WHERE user_id = ?1 limit 10", nativeQuery = true)
    List<Dashboard> findAllTestByUserID(Long id);

}
