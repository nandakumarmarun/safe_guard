package com.security.analyzer.v1.dashboard;


import com.security.analyzer.v1.test.SecurityTestResponseDTO;

import java.util.List;

public interface DashboardService {

    void save(Dashboard dashboard);

    void UpdateStatus(String id);

    ChartDTO getChart();


    List<SecurityTestResponseDTO> findAllTestslimited();


}
