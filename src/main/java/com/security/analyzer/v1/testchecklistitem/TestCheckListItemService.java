package com.security.analyzer.v1.testchecklistitem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.security.analyzer.v1.testchecklistitem.TestCheckListItem}.
 */
public interface TestCheckListItemService  {
    /**
     * Save a testCheckListItem.
     *
     * @param testCheckListItem the entity to save.
     * @return the persisted entity.
     */
    TestCheckListItem save(TestCheckListItem testCheckListItem);

    /**
     * Updates a testCheckListItem.
     *
     * @param testCheckListItem the entity to update.
     * @return the persisted entity.
     */
    TestCheckListItem update(TestCheckListItem testCheckListItem);

    /**
     * Partially updates a testCheckListItem.
     *
     * @param testCheckListItem the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TestCheckListItem> partialUpdate(TestCheckListItem testCheckListItem);

    /**
     * Get all the testCheckListItems.
     *
     * @return the list of entities.
     */
    List<TestCheckListItem> findAll();

    /**
     * Get all the testCheckListItems with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TestCheckListItem> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" testCheckListItem.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TestCheckListItem> findOne(Long id);

    /**
     * Delete the "id" testCheckListItem.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
