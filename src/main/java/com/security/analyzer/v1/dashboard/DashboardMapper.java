package com.security.analyzer.v1.dashboard;

import com.security.analyzer.v1.Enum.SecurityLevel;
import com.security.analyzer.v1.test.SecurityTestResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class DashboardMapper {

    private final DashboardRepository dashboardRepository;

    public Dashboard TestToDashboard(SecurityTestResponseDTO securityTestResponseDTO){
        Dashboard dashboard = new Dashboard();
        dashboard.setTestID(securityTestResponseDTO.getId());
        dashboard.setApplicationName(securityTestResponseDTO.getApplicationName());
        dashboard.setTestScore(securityTestResponseDTO.getTestScore());
        dashboard.setTestStatus(securityTestResponseDTO.getTestStatus());
        dashboard.setComapanyId(securityTestResponseDTO.getCompanyId());
        dashboard.setSecurityLevel(SecurityLevel
            .valueOf(securityTestResponseDTO.getSecurityLevel()));
        dashboard.setComapanyId(securityTestResponseDTO.getCompanyId());
        dashboard.setCompanyName(securityTestResponseDTO.getCompanyName());
        dashboard.setUserId(securityTestResponseDTO.getUserId());
        dashboard.setUserName(securityTestResponseDTO.getUserName());
        dashboard.setTestDescription(securityTestResponseDTO.getDescription());
        return dashboard;
    }
    public Dashboard ToDashboard(SecurityTestResponseDTO securityTestResponseDTO){
        Optional<Dashboard> optionalDashboard =
            dashboardRepository.findOneByTestID(securityTestResponseDTO.getId());
        if(optionalDashboard.isPresent()){
            Dashboard dashboard = optionalDashboard.get();
            dashboard.setTestID(securityTestResponseDTO.getId());
            dashboard.setApplicationName(securityTestResponseDTO.getApplicationName());
            dashboard.setTestScore(securityTestResponseDTO.getTestScore());
            dashboard.setTestStatus(securityTestResponseDTO.getTestStatus());
            dashboard.setComapanyId(securityTestResponseDTO.getCompanyId());
            dashboard.setSecurityLevel(SecurityLevel
                .valueOf(securityTestResponseDTO.getSecurityLevel()));
            dashboard.setComapanyId(securityTestResponseDTO.getCompanyId());
            dashboard.setCompanyName(securityTestResponseDTO.getCompanyName());
            dashboard.setUserId(securityTestResponseDTO.getUserId());
            dashboard.setUserName(securityTestResponseDTO.getUserName());
            dashboard.setTestDescription(securityTestResponseDTO.getDescription());
            return dashboard;
        }
        return null;
    }
}
