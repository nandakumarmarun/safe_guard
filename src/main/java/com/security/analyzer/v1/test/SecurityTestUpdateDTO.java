package com.security.analyzer.v1.test;

import com.security.analyzer.v1.testchecklist.TestCheckListUpdateDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SecurityTestUpdateDTO {
    private String id;
    private String applicationName;
    private Integer systemNo;
    private String department;
    private String testStatus;
    private Double testScore;
    private String securityLevel;
    private Set<TestCheckListUpdateDTO> testCheckLists;
    private Long userId;
    private Long companyId;
    private Long companyName;

}
