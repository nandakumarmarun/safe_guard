package com.security.analyzer.v1.securitytest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing {@link com.security.analyzer.v1.securitytest.SecurityTest}.
 */
public interface SecurityTestService {
    /**
     * Save a securityTest.
     *
     * @param securityTestDTO the entity to save.
     * @return the persisted entity.
     */
    SecurityTestResponseDTO save(SecurityTestDTO securityTestDTO);

    /**
     * Updates a securityTest.
     *
     * @param securityTestUpdateDTO@return the persisted entity.
     */
    SecurityTestResponseDTO update(SecurityTestUpdateDTO securityTestUpdateDTO);
    /**
     * Get all the securityTests.
     *
     * @return the list of entities.
     */
    List<SecurityTest> findAll();

    /**
     * Get all the securityTests with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SecurityTest> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" securityTest.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    SecurityTestResponseDTO findOne(Long id);

    /**
     * Get the "id" securityTest.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    void UpdateStatus(Long id);

    /**
     * Delete the "id" securityTest.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
