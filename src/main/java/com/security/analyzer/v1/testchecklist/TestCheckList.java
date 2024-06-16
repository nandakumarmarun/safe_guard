package com.security.analyzer.v1.testchecklist;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.security.analyzer.v1.checklist.CheckList;
import com.security.analyzer.v1.securitytest.SecurityTest;
import com.security.analyzer.v1.testchecklistitem.TestCheckListItem;
import jakarta.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A TestCheckList.
 */
@Entity
@Table(name = "test_check_list")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TestCheckList implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "test_check_list_id")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<TestCheckListItem> testCheckListItems = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private CheckList checkList;


    public Long getId() {
        return this.id;
    }

    public TestCheckList id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<TestCheckListItem> getTestCheckListItems() {
        return this.testCheckListItems;
    }

    public void setTestCheckListItems(Set<TestCheckListItem> testCheckListItems) {
        this.testCheckListItems = testCheckListItems;
    }

    public TestCheckList testCheckListItems(Set<TestCheckListItem> testCheckListItems) {
        this.setTestCheckListItems(testCheckListItems);
        return this;
    }


//    public TestCheckList removeTestCheckListItem(TestCheckListItem testCheckListItem) {
//        this.testCheckListItems.remove(testCheckListItem);
//        testCheckListItem.setTestCheckList(null);
//        return this;
//    }

//    public SecurityTest getSecurityTest() {
//        return this.securityTest;
//    }
//
//    public void setSecurityTest(SecurityTest securityTest) {
//        this.securityTest = securityTest;
//    }

//    public TestCheckList securityTest(SecurityTest securityTest) {
//        this.setSecurityTest(securityTest);
//        return this;
//    }

    public CheckList getCheckList() {
        return this.checkList;
    }

    public void setCheckList(CheckList checkList) {
        this.checkList = checkList;
    }

    public TestCheckList checkList(CheckList checkList) {
        this.setCheckList(checkList);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TestCheckList)) {
            return false;
        }
        return getId() != null && getId().equals(((TestCheckList) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TestCheckList{" +
            "id=" + getId() +
            "}";
    }
}
