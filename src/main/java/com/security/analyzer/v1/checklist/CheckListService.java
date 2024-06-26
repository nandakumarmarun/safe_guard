package com.security.analyzer.v1.checklist;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {CheckList}.
 */
public interface CheckListService {
    /**
     * Save a checkList.
     *
     * @param checkList the entity to save.
     * @return the persisted entity.
     */
    CheckList save(CheckList checkList);

    CheckListResponseDTO save(CheckListRequestDTO checkListRequestDTO);

    /**
     * Updates a checkList.
     *
     * @param CheckListUpdateDTO @return the persisted entity.
     */
    CheckListResponseDTO update(CheckListUpdateDTO CheckListUpdateDTO);

    /**
     * Partially updates a checkList.
     *
     * @param checkList the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CheckList> partialUpdate(CheckList checkList);

    /**
     * Get all the checkLists.
     *
     * @return the list of entities.
     */
    List<CheckListResponseDTO> findAll();

    /**
     * Get the "id" checkList.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CheckList> findOne(Long id);

    /**
     * Delete the "id" checkList.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
