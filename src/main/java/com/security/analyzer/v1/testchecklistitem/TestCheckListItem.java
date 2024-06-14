package com.security.analyzer.v1.testchecklistitem;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.security.analyzer.v1.checklistItem.CheckListItem;
import com.security.analyzer.v1.testchecklist.TestCheckList;
import jakarta.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serializable;

/**
 * A TestCheckListItem.
 */
@Entity
@Table(name = "test_check_list_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TestCheckListItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "marked")
    private Boolean marked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "checkList" }, allowSetters = true)
    private CheckListItem checklistitem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "testCheckListItems", "checkLists", "securityTest" }, allowSetters = true)
    private TestCheckList testCheckList;


    public Long getId() {
        return this.id;
    }

    public TestCheckListItem id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getMarked() {
        return this.marked;
    }

    public TestCheckListItem marked(Boolean marked) {
        this.setMarked(marked);
        return this;
    }

    public void setMarked(Boolean marked) {
        this.marked = marked;
    }

    public CheckListItem getChecklistitem() {
        return this.checklistitem;
    }

    public void setChecklistitem(CheckListItem checkListItem) {
        this.checklistitem = checkListItem;
    }

    public TestCheckListItem checklistitem(CheckListItem checkListItem) {
        this.setChecklistitem(checkListItem);
        return this;
    }

    public TestCheckList getTestCheckList() {
        return this.testCheckList;
    }

    public void setTestCheckList(TestCheckList testCheckList) {
        this.testCheckList = testCheckList;
    }

    public TestCheckListItem testCheckList(TestCheckList testCheckList) {
        this.setTestCheckList(testCheckList);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TestCheckListItem)) {
            return false;
        }
        return getId() != null && getId().equals(((TestCheckListItem) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TestCheckListItem{" +
            "id=" + getId() +
            ", marked='" + getMarked() + "'" +
            "}";
    }
}
