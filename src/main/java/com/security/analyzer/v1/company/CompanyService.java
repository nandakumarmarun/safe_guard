package com.security.analyzer.v1.company;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.security.analyzer.v1.company.Company}
 */
public interface CompanyService {
    /**
     * Save a company.
     *
     * @param companyDTO the entity to save.
     * @return the persisted entity.
     */
    CompanyReponseDTO save(CompanyRequestDTO companyDTO);

    /**
     * Updates a company.
     *
     * @param companyUpdateDTO the entity to update.
     * @return the persisted entity.
     */
    CompanyReponseDTO update(CompanyUpdateDTO companyUpdateDTO);


    /**
     * Get all the companies.
     *
     * @return the list of entities.
     */
    List<CompanyReponseDTO> findAll();

    /**
     * Get all the companies with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Company> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" company.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Company> findOne(Long id);

    /**
     * Delete the "id" company.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
