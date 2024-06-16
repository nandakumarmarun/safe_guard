package com.security.analyzer.v1.checklistItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CheckListItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CheckListItemRepository extends JpaRepository<CheckListItem, Long > {

    @Query("SELECT SUM(c.value) FROM CheckListItem c")
    Double findsumofValue();
}

