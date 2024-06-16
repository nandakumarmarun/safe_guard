package com.security.analyzer.v1.securitytest;


import com.security.analyzer.v1.exceptions.BadRequestAlertException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.security.analyzer.v1.securitytest.SecurityTest}.
 */
@RestController
@RequestMapping("/api/security-tests")
@RequiredArgsConstructor
public class SecurityTestResource {

    private final Logger log = LoggerFactory.getLogger(SecurityTestResource.class);

    private static final String ENTITY_NAME = "securityTest";

    private final SecurityTestService securityTestService;

    private final SecurityTestRepository securityTestRepository;

    /**
     * {@code POST  /security-tests} : Create a new securityTest.
     *
     * @param securityTest the securityTest to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new securityTest, or with status {@code 400 (Bad Request)} if the securityTest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<SecurityTestResponseDTO> createSecurityTest(
        @RequestBody SecurityTestDTO securityTest) throws URISyntaxException {
        log.debug("REST request to save SecurityTest : {}", securityTest);
        SecurityTestResponseDTO securityTestResponseDTO = securityTestService.save(securityTest);
        return ResponseEntity.ok().body(securityTestResponseDTO);
    }




    /**
     * {@code PUT  /security-tests/:id} : Updates an existing securityTest.
     *
     * @param id the id of the securityTest to save.
     * @param securityTest the securityTest to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated securityTest,
     * or with status {@code 400 (Bad Request)} if the securityTest is not valid,
     * or with status {@code 500 (Internal Server Error)} if the securityTest couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SecurityTest> updateSecurityTest(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SecurityTest securityTest
    ) throws URISyntaxException {
        log.debug("REST request to update SecurityTest : {}, {}", id, securityTest);
        if (securityTest.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, securityTest.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!securityTestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        securityTest = securityTestService.update(securityTest);
//        return ResponseEntity.ok()
//            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, securityTest.getId().toString()))
//            .body(securityTest);
        return null;
    }


    /**
     * {@code GET  /security-tests} : get all the securityTests.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of securityTests in body.
     */
    @GetMapping("")
    public List<SecurityTest> getAllSecurityTests(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all SecurityTests");
        return securityTestService.findAll();
    }



    /**
     * {@code GET  /security-tests/:id} : get the "id" securityTest.
     *
     * @param id the id of the securityTest to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the securityTest, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SecurityTest> getSecurityTest(@PathVariable("id") Long id) {
        log.debug("REST request to get SecurityTest : {}", id);
        Optional<SecurityTest> securityTest = securityTestService.findOne(id);
//        return ResponseUtil.wrapOrNotFound(securityTest);
        return null;
    }




    /**
     * {@code DELETE  /security-tests/:id} : delete the "id" securityTest.
     *
     * @param id the id of the securityTest to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSecurityTest(@PathVariable("id") Long id) {
        log.debug("REST request to delete SecurityTest : {}", id);
        securityTestService.delete(id);
//        return ResponseEntity.noContent()
//            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
//            .build();
        return null;
    }
}