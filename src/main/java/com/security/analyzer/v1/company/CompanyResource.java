package com.security.analyzer.v1.company;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * REST controller for managing {@link com.security.analyzer.v1.company.Company}.
 */
@RestController
@RequestMapping("/api/companies")
public class CompanyResource {

    private final Logger log = LoggerFactory.getLogger(CompanyResource.class);

    private static final String ENTITY_NAME = "company";

    private final CompanyService companyService;

    private final CompanyRepository companyRepository;

    public CompanyResource(CompanyService companyService,
                           CompanyRepository companyRepository) {
        this.companyService = companyService;
        this.companyRepository = companyRepository;
    }
    @PostMapping("")
    public ResponseEntity<CompanyReponseDTO> createCompany(
        @RequestBody CompanyRequestDTO companyRequestDTO) throws URISyntaxException {
        log.debug("REST request to save Company : {}");
        CompanyReponseDTO companyReponseDTO = companyService.save(companyRequestDTO);
        return ResponseEntity.created(new URI("/api/companies" + companyReponseDTO.getId()))
            .body(companyReponseDTO);
    }
    /**
     * {@code PUT  /companies/:id} : Updates an existing company.
     *
     * @param id the id of the company to save.
     * @param companyUpdateDTO the company to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated company,
     * or with status {@code 400 (Bad Request)} if the company is not valid,
     * or with status {@code 500 (Internal Server Error)} if the company couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CompanyReponseDTO> updateCompany(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CompanyUpdateDTO companyUpdateDTO)
        throws URISyntaxException {
        log.debug("REST request to update Company : {}, {}", id, companyUpdateDTO);
        companyUpdateDTO.setId(id);
        CompanyReponseDTO companyReponseDTO = companyService.update(companyUpdateDTO);
        return ResponseEntity.created(new URI("/api/companies" + companyReponseDTO.getId()))
            .body(companyReponseDTO);
    }
    /**
     * {@code GET  /companies} : get all the companies.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of companies in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CompanyReponseDTO>> getAllCompanies() {
        log.debug("REST request to get all Companies");
        List<CompanyReponseDTO> companyReponseDTOList = companyService.findAllByUserId();
        return ResponseEntity.ok().body(companyReponseDTOList);
    }
    /**
     * {@code DELETE  /companies/:id} : delete the "id" company.
     *
     * @param id the id of the company to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable("id") Long id) {
        log.debug("REST request to delete Company : {}", id);
        companyService.delete(id);
        return null;
    }
}
















//    /**
//     * {@code GET  /companies/:id} : get the "id" company.
//     *
//     * @param id the id of the company to retrieve.
//     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the company, or with status {@code 404 (Not Found)}.
//     */
//    @GetMapping("/{id}")
//    public ResponseEntity<Company> getCompany(@PathVariable("id") Long id) {
//        log.debug("REST request to get Company : {}", id);
//        Optional<Company> company = companyService.findOne(id);
//        return null;
//    }
