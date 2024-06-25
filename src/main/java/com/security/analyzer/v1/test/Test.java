package com.security.analyzer.v1.test;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.security.analyzer.v1.Enum.PriorityLevel;
import com.security.analyzer.v1.Enum.SecurityLevel;
import com.security.analyzer.v1.company.Company;
import com.security.analyzer.v1.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_security_tests")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Test {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    private String testID;

    @Column(name = "application_name")
    private String applicationName;

    @Column(name = "system_no")
    private Integer systemNo;

    @Column(name = "department")
    private String department;

    @Column(name = "test_status")
    private String testStatus;

    @Column(name = "test_Description")
    private String testDescription;

    @Column(name = "test_score")
    private Double testScore;

    @Enumerated(EnumType.STRING)
    @Column(name = "security_level")
    private SecurityLevel securityLevel;

    @Column(name = "check_list_id")
    private long  checkListId;

    @Column(name = "check_list_name")
    private  String  checkListName;

    @Column(name = "marked")
    private Boolean marked;

    @Column(name = "check_list_itemId")
    private Long  checklistitemId;

    @Column(name = "check_list_item_name")
    private String checklistitemName;

    @Column(name = "value")
    private Double value;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority_level")
    private PriorityLevel priorityLevel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "internalUser", "userAttributes", "securityTests" }, allowSetters = true)
    private User applicationUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "securityTests", "country", "state", "district", "city", "location" }, allowSetters = true)
    private Company company;



}
