package com.security.analyzer.v1.securitytest;

import com.security.analyzer.v1.testchecklist.TestCheckListDTO;
import com.security.analyzer.v1.testchecklist.TestCheckListUpdateDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SecurityTestUpdateDTO {
    private Long id;
    private String applicationName;
    private Integer systemNo;
    private String department;
    private String testStatus;
    private Double testScore;
    private String securityLevel;
    private Set<TestCheckListUpdateDTO> testCheckListUpdateDTOS;
    private Long userId;
    private Long companyId;
    private Long companyName;

}
