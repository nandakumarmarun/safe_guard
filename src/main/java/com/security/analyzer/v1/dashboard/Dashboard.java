package com.security.analyzer.v1.dashboard;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.security.analyzer.v1.Enum.SecurityLevel;
import com.security.analyzer.v1.company.Company;
import com.security.analyzer.v1.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "tbl_dashboard")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dashboard {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    private String testID;

    @Column(name = "application_name")
    private String applicationName;


    @Column(name = "test_status")
    private String testStatus;

    @Column(name = "test_Description")
    private String testDescription;

    @Column(name = "test_score")
    private Double testScore;

    @Enumerated(EnumType.STRING)
    @Column(name = "security_level")
    private SecurityLevel securityLevel;

    @Column(name = "system_no")
    private Integer systemNo;

    private Long userId;

    private String userName;

    private Long comapanyId;

    private String CompanyName;

}
