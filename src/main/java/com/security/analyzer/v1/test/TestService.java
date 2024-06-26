package com.security.analyzer.v1.test;

import com.security.analyzer.v1.Enum.SecurityLevel;
import com.security.analyzer.v1.securitytest.SecurityTest;
import com.security.analyzer.v1.securitytest.SecurityTestDTO;
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
    List<SecurityTestResponseDTO> findAllByCompanyId();


    /**
     * Get the "id" securityTest.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    SecurityTestResponseDTO findOne(String id);

    /**
     * Get the "id" securityTest.
     *
     * @param id the id of the entity.
     * @return the entity.
     */


    /**
     * Delete the "id" securityTest.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
