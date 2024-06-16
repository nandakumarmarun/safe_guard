package com.security.analyzer.v1.testchecklistitem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the TestCheckListItem entity.
 */
@Repository
public interface TestCheckListItemRepository extends JpaRepository<TestCheckListItem, Long> {
    default Optional<TestCheckListItem> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<TestCheckListItem> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<TestCheckListItem> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select testCheckListItem from TestCheckListItem testCheckListItem left join fetch testCheckListItem.checklistitem",
        countQuery = "select count(testCheckListItem) from TestCheckListItem testCheckListItem"
    )
    Page<TestCheckListItem> findAllWithToOneRelationships(Pageable pageable);

    @Query("select testCheckListItem from TestCheckListItem testCheckListItem left join fetch testCheckListItem.checklistitem")
    List<TestCheckListItem> findAllWithToOneRelationships();

    @Query(
        "select testCheckListItem from TestCheckListItem testCheckListItem left join fetch testCheckListItem.checklistitem where testCheckListItem.id =:id"
    )
    Optional<TestCheckListItem> findOneWithToOneRelationships(@Param("id") Long id);
}
