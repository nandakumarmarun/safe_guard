package com.security.analyzer.v1.dashboard;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DashboardServiceImpl implements  DashboardService{

    private final DashboardRepository dashboardRepository;

    @Override
    @Async
    public void save(Dashboard dashboard) {
        dashboardRepository.save(dashboard);
    }



}
