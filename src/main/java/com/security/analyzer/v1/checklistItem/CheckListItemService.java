package com.security.analyzer.v1.checklistItem;

import com.security.analyzer.v1.checklist.CheckListResponseDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.security.analyzer.v1.checklistItem.CheckListItem}.
 */
public interface CheckListItemService {
    /**
     * Save a checkListItem.
     *
     * @param checkListItemCreateDTO
     */
    CheckListResponseDTO save(CheckListItemCreateDTO checkListItemCreateDTO);

    /**
     * Updates a checkListItem.
     *
     * @param checkListItemUpdateDTO the entity to update.
     * @return the persisted entity.
     */
    CheckListItemResposeDTO update(CheckListItemUpdateDTO checkListItemUpdateDTO);

    /**
     * Partially updates a checkListItem.
     *
     * @param checkListItem the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CheckListItem> partialUpdate(CheckListItem checkListItem);

    /**
     * Get all the checkListItems.
     *
     * @return the list of entities.
     */
    List<CheckListItem> findAll();

    /**
     * Get the "id" checkListItem.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CheckListItem> findOne(Long id);

    /**
     * Delete the "id" checkListItem.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
