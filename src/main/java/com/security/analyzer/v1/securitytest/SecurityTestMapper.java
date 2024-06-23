package com.security.analyzer.v1.securitytest;

import org.springframework.stereotype.Service;

@Service
public class SecurityTestMapper {


    public SecurityTestResponseDTO securityTestToSecurityTestResponseDTO(SecurityTest securityTest) {
        SecurityTestResponseDTO securityTestResponseDTO  = new SecurityTestResponseDTO();
        securityTestResponseDTO.setId(securityTest.getId());
        securityTestResponseDTO.setApplicationName(securityTest.getApplicationName());
        securityTestResponseDTO.setSystemNo(securityTest.getSystemNo());;
        securityTestResponseDTO.setDepartment(securityTest.getDepartment());
        securityTestResponseDTO.setTestStatus(securityTest.getTestStatus());
        securityTestResponseDTO.setTestScore(securityTest.getTestScore());
        securityTestResponseDTO.setSecurityLevel(String.valueOf(securityTest.getSecurityLevel()));
        securityTestResponseDTO.setUserId(securityTest.getApplicationUser().getId());
        securityTestResponseDTO.setUserName(securityTest.getApplicationUser().getUsername());
        securityTestResponseDTO.setCompanyId(securityTest.getCompany().getId());
        securityTestResponseDTO.setCompanyName(securityTest.getCompany().getCompanyName());
        securityTestResponseDTO.setDescription(securityTest.getTestDescription());
        return securityTestResponseDTO;
    }
}
