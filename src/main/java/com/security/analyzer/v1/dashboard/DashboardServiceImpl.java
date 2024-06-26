package com.security.analyzer.v1.dashboard;

import com.security.analyzer.v1.Enum.SecurityLevel;
import com.security.analyzer.v1.config.utils.SecurityUtils;
import com.security.analyzer.v1.test.SecurityTestResponseDTO;
import com.security.analyzer.v1.user.User;
import com.security.analyzer.v1.user.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DashboardServiceImpl implements  DashboardService{

    private final DashboardRepository dashboardRepository;

    private final UserRepository userRepository;


    private final DashboardMapper  dashboardMapper;

    @Override
    @Async
    public void save(Dashboard dashboard) {
        dashboardRepository.save(dashboard);
    }

    @Override
    public void UpdateStatus(String id) {
        Optional<Dashboard> oneByTestID = dashboardRepository.findOneByTestID(id);
        oneByTestID.ifPresent(data->{
            data.setTestStatus("COMPLETED");
            dashboardRepository.save(data);
        });
    }

    @Override
    public ChartDTO getChart() {
        Optional<User> optionalUser = userRepository
                .findByLogin(SecurityUtils.getCurrentUserLogin()
                        .orElse(null));
        ChartDTO chartDTO = new ChartDTO();
        var sucessCount = dashboardRepository.countBySecurityLevel(
                SecurityLevel.EXCELLENT.toString(), optionalUser.get().getId());
        var failedCount = dashboardRepository.countBySecurityLevel(
                SecurityLevel.CRITICAL.toString(), optionalUser.get().getId());
        var moderateCount = dashboardRepository.countBySecurityLevel(
                SecurityLevel.MODERATE.toString(), optionalUser.get().getId());
        chartDTO.setSucessCount(sucessCount);
        chartDTO.setModerateCount(moderateCount);
        chartDTO.setFailedcount(failedCount);
        return chartDTO;
    }

    @Override
    public List<SecurityTestResponseDTO> findAllTestslimited() {
        Optional<User> optionalUser = userRepository
                .findByLogin(SecurityUtils.getCurrentUserLogin()
                        .orElse(null));
        if(optionalUser.isPresent()){
            var user = optionalUser.get();
            var allTest = dashboardRepository.findAllTest(user.getId());
            var securityTestResponseDTOS = dashboardMapper.TestToDashboard(allTest);
            return securityTestResponseDTOS;
        }
        return Collections.emptyList();
    }
}
