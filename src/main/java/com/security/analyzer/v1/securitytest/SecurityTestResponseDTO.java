package com.security.analyzer.v1.securitytest;

import com.security.analyzer.v1.testchecklist.TestCheckList;
import com.security.analyzer.v1.testchecklist.TestCheckListDTO;
import com.security.analyzer.v1.testchecklist.TestCheckListResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SecurityTestResponseDTO {
    private String applicationName;
    private Integer systemNo;
    private String department;
    private String testStatus;
    private Double testScore;
    private String securityLevel;
    private Set<TestCheckListResponseDTO> testCheckListResponseDTOS;
    private Long userId;
    private String userName;
    private Long companyId;
    private String companyName;
    private String Description;


    public SecurityTestResponseDTO(SecurityTest securityTest) {
        this.applicationName = securityTest.getApplicationName();
        this.systemNo = securityTest.getSystemNo();
        this.department = securityTest.getDepartment();
        this.testStatus = securityTest.getTestStatus();
        this.testScore = securityTest.getTestScore();
        this.securityLevel = String.valueOf(securityTest.getSecurityLevel());
        this.testCheckListResponseDTOS = getTestCheckListResponseDTOS(securityTest.getTestCheckLists());
        this.userId = securityTest.getApplicationUser().getId();
        this.userName = securityTest.getApplicationUser().getUsername();
        this.companyId = securityTest.getCompany().getId();
        this.companyName = securityTest.getCompany().getCompanyName();
        this.Description = securityTest.getTestDescription();
    }

    private Set<TestCheckListResponseDTO> getTestCheckListResponseDTOS(Set<TestCheckList> testCheckLists) {
        Set<TestCheckListResponseDTO> testCheckListResponseDTOS1 = new HashSet<>();
        testCheckLists.forEach(data->{
            testCheckListResponseDTOS1.add(new TestCheckListResponseDTO(data));
        });
        return testCheckListResponseDTOS1;
    }
}
