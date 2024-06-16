package com.security.analyzer.v1.securitytest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

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
     * @param securityTest the entity to update.
     * @return the persisted entity.
     */
    SecurityTest update(SecurityTest securityTest);
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
    Optional<SecurityTest> findOne(Long id);

    /**
     * Delete the "id" securityTest.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
