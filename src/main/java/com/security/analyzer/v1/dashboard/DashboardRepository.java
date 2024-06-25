package com.security.analyzer.v1.dashboard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DashboardRepository extends JpaRepository<Dashboard ,Long> {


    Optional<Dashboard> findOneByTestID(String Test);

}
