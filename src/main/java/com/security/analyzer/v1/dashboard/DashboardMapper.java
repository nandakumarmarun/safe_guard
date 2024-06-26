package com.security.analyzer.v1.dashboard;

import com.security.analyzer.v1.Enum.SecurityLevel;
import com.security.analyzer.v1.test.SecurityTestResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
        dashboard.setSystemNo(securityTestResponseDTO.getSystemNo());
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
            dashboard.setSystemNo(securityTestResponseDTO.getSystemNo());
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


    public List<SecurityTestResponseDTO> TestToDashboard(List<Dashboard> dashboards){

        List<SecurityTestResponseDTO> securityTestResponseDTOs = new ArrayList<>();
        for(Dashboard dashboard : dashboards){
            SecurityTestResponseDTO securityTestResponseDTO = new SecurityTestResponseDTO() ;
            securityTestResponseDTO.setApplicationName(dashboard.getApplicationName());
            securityTestResponseDTO.setId(dashboard.getTestID());
            securityTestResponseDTO.setTestScore(dashboard.getTestScore());
            securityTestResponseDTO.setTestStatus(dashboard.getTestStatus());
            securityTestResponseDTO.setSecurityLevel(dashboard.getSecurityLevel().toString());
            securityTestResponseDTO.setCompanyId(dashboard.getComapanyId());
            securityTestResponseDTO.setSystemNo(dashboard.getSystemNo());
            securityTestResponseDTO.setCompanyName(dashboard.getCompanyName());
            securityTestResponseDTO.setUserId(dashboard.getUserId());
            securityTestResponseDTO.setUserName(dashboard.getUserName());
            securityTestResponseDTO.setDescription(dashboard.getTestDescription());
            securityTestResponseDTOs.add(securityTestResponseDTO);
        }
        return securityTestResponseDTOs;
    }


}
