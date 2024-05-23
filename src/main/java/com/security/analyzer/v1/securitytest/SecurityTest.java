package com.security.analyzer.v1.securitytest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.security.analyzer.v1.Enum.SecurityLevel;
import com.security.analyzer.v1.company.Company;
import com.security.analyzer.v1.testchecklist.TestCheckList;
import com.security.analyzer.v1.user.User;
import jakarta.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A SecurityTest.
 */
@Entity
@Table(name = "security_test")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SecurityTest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "application_name")
    private String applicationName;

    @Column(name = "system_no")
    private Integer systemNo;

    @Column(name = "department")
    private String department;

    @Column(name = "test_status")
    private String testStatus;

    @Column(name = "test_score")
    private Double testScore;

    @Enumerated(EnumType.STRING)
    @Column(name = "security_level")
    private SecurityLevel securityLevel;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "securityTest")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "testCheckListItems", "securityTest", "checkList" }, allowSetters = true)
    private Set<TestCheckList> testCheckLists = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "internalUser", "userAttributes", "securityTests" }, allowSetters = true)
    private User applicationUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "securityTests", "country", "state", "district", "city", "location" }, allowSetters = true)
    private Company company;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SecurityTest id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApplicationName() {
        return this.applicationName;
    }

    public SecurityTest applicationName(String applicationName) {
        this.setApplicationName(applicationName);
        return this;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public Integer getSystemNo() {
        return this.systemNo;
    }

    public SecurityTest systemNo(Integer systemNo) {
        this.setSystemNo(systemNo);
        return this;
    }

    public void setSystemNo(Integer systemNo) {
        this.systemNo = systemNo;
    }

    public String getDepartment() {
        return this.department;
    }

    public SecurityTest department(String department) {
        this.setDepartment(department);
        return this;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getTestStatus() {
        return this.testStatus;
    }

    public SecurityTest testStatus(String testStatus) {
        this.setTestStatus(testStatus);
        return this;
    }

    public void setTestStatus(String testStatus) {
        this.testStatus = testStatus;
    }

    public Double getTestScore() {
        return this.testScore;
    }

    public SecurityTest testScore(Double testScore) {
        this.setTestScore(testScore);
        return this;
    }

    public void setTestScore(Double testScore) {
        this.testScore = testScore;
    }

    public SecurityLevel getSecurityLevel() {
        return this.securityLevel;
    }

    public SecurityTest securityLevel(SecurityLevel securityLevel) {
        this.setSecurityLevel(securityLevel);
        return this;
    }

    public void setSecurityLevel(SecurityLevel securityLevel) {
        this.securityLevel = securityLevel;
    }

    public Set<TestCheckList> getTestCheckLists() {
        return this.testCheckLists;
    }

    public void setTestCheckLists(Set<TestCheckList> testCheckLists) {
        if (this.testCheckLists != null) {
            this.testCheckLists.forEach(i -> i.setSecurityTest(null));
        }
        if (testCheckLists != null) {
            testCheckLists.forEach(i -> i.setSecurityTest(this));
        }
        this.testCheckLists = testCheckLists;
    }

    public SecurityTest testCheckLists(Set<TestCheckList> testCheckLists) {
        this.setTestCheckLists(testCheckLists);
        return this;
    }

    public SecurityTest addTestCheckList(TestCheckList testCheckList) {
        this.testCheckLists.add(testCheckList);
        testCheckList.setSecurityTest(this);
        return this;
    }

    public SecurityTest removeTestCheckList(TestCheckList testCheckList) {
        this.testCheckLists.remove(testCheckList);
        testCheckList.setSecurityTest(null);
        return this;
    }

    public User getApplicationUser() {
        return applicationUser;
    }

    public void setApplicationUser(User applicationUser) {
        this.applicationUser = applicationUser;
    }

    public Company getCompany() {
        return this.company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public SecurityTest company(Company company) {
        this.setCompany(company);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SecurityTest)) {
            return false;
        }
        return getId() != null && getId().equals(((SecurityTest) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SecurityTest{" +
            "id=" + getId() +
            ", applicationName='" + getApplicationName() + "'" +
            ", systemNo=" + getSystemNo() +
            ", department='" + getDepartment() + "'" +
            ", testStatus='" + getTestStatus() + "'" +
            ", testScore=" + getTestScore() +
            ", securityLevel='" + getSecurityLevel() + "'" +
            "}";
    }
}
