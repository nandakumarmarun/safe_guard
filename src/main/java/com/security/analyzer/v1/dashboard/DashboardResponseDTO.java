package com.security.analyzer.v1.dashboard;

import com.security.analyzer.v1.Enum.SecurityLevel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardResponseDTO {

    private Long id;

    private String testID;

    private String applicationName;

    private String testStatus;

    private String testDescription;

    private Double testScore;

    private String securityLevel;

    private Long userId;

    private Long userName;

    private Long comapanyId;

    private Long CompanyName;

}
