package com.security.analyzer.v1.checklist;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.security.analyzer.v1.checklistItem.CheckListItem;
import jakarta.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A CheckList.
 */
@Entity
@Table(name = "tbl_check_list")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CheckList implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "check_list_id")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<CheckListItem> checkListItems = new HashSet<>();

    public Long getId() {
        return this.id;
    }

    public CheckList id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public CheckList name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<CheckListItem> getCheckListItems() {
        return this.checkListItems;
    }


    public void setCheckListItems(Set<CheckListItem> checkListItems) {
        this.checkListItems = checkListItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CheckList)) {
            return false;
        }
        return getId() != null && getId().equals(((CheckList) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CheckList{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
