package com.security.analyzer.v1.test;

import com.security.analyzer.v1.Enum.SecurityLevel;
import com.security.analyzer.v1.securitytest.SecurityTest;
import com.security.analyzer.v1.securitytest.SecurityTestDTO;
import com.security.analyzer.v1.securitytest.SecurityTestResponseDTO;
import com.security.analyzer.v1.securitytest.SecurityTestUpdateDTO;
import com.security.analyzer.v1.securitytest.chart.ChartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing {@link SecurityTest}.
 */
public interface TestService {
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

    List<SecurityTestResponseDTO> findAllTests();

    long countBySecurityLevel(SecurityLevel securityLevel);

    ChartDTO getChart();

    List<SecurityTestResponseDTO> findAllTestslimited();

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
