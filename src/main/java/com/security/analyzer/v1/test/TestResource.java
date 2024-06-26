package com.security.analyzer.v1.test;


import com.security.analyzer.v1.Enum.SecurityLevel;
import com.security.analyzer.v1.exceptions.BadRequestAlertException;
import com.security.analyzer.v1.securitytest.SecurityTest;
import com.security.analyzer.v1.securitytest.SecurityTestDTO;
import com.security.analyzer.v1.securitytest.chart.ChartDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

/**
 * Service Interface for managing {@link SecurityTest}.
 */
@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestResource {

    private final Logger log = LoggerFactory.getLogger(TestResource.class);

    private static final String ENTITY_NAME = "securityTest";

    private final TestService testService;

    private final TestRepository securityTestRepository;

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
        SecurityTestResponseDTO securityTestResponseDTO = testService.save(securityTest);
        return ResponseEntity.ok().body(securityTestResponseDTO);
    }




    /**
     * {@code PUT  /security-tests/:id} : Updates an existing securityTest.
     *
     * @param id the id of the securityTest to save.
     * @param securityTestUpdateDTO the securityTest to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated securityTest,
     * or with status {@code 400 (Bad Request)} if the securityTest is not valid,
     * or with status {@code 500 (Internal Server Error)} if the securityTest couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SecurityTestResponseDTO> updateSecurityTest(
        @PathVariable(value = "id", required = false) String id,
        @RequestBody SecurityTestUpdateDTO securityTestUpdateDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SecurityTest : {}, {}", id, securityTestUpdateDTO);
        if (securityTestUpdateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, securityTestUpdateDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        securityTestUpdateDTO.setId(id);
        SecurityTestResponseDTO securityTestResponseDTO = testService.update(securityTestUpdateDTO);
        return ResponseEntity.ok().body(securityTestResponseDTO);
    }


    /**
     * {@code GET  /security-tests} : get all the securityTests.
     ** @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of securityTests in body.
     */
    @GetMapping("")
    public  ResponseEntity<List<SecurityTestResponseDTO>> getAllSecurityTests() {
        log.debug("REST request to get all SecurityTests");
        return ResponseEntity.ok().body(testService.findAllByCompanyId());
    }



    /**
     * {@code GET  /security-tests/:id} : get the "id" securityTest.
     *
     * @param id the id of the securityTest to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the securityTest, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SecurityTestResponseDTO> getSecurityTest(@PathVariable("id") String id) {
        log.debug("REST request to get SecurityTest : {}", id);
        SecurityTestResponseDTO securityTestResponseDTO = testService.findOne(id);
        return ResponseEntity.ok().body(securityTestResponseDTO);
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
        testService.delete(id);
//        return ResponseEntity.noContent()
//            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
//            .build();
        return null;
    }
}
