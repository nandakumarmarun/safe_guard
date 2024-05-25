package com.security.analyzer.v1.checklist;

import com.security.analyzer.v1.exceptions.BadRequestAlertException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link com.security.analyzer.v1.checklist.CheckList}.
 */
@RestController
@RequestMapping("/api/check-lists")
@RequiredArgsConstructor
public class CheckListResource {

    private final Logger log = LoggerFactory.getLogger(CheckListResource.class);

    private static final String ENTITY_NAME = "checkList";

    private final CheckListService checkListService;

    private final CheckListRepository checkListRepository;

    /**
     * {@code POST  /check-lists} : Create a new checkList.
     *
     * @param checkList the checkList to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new checkList, or with status {@code 400 (Bad Request)} if the checkList has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<?> createCheckList(@RequestBody CheckList checkList) throws URISyntaxException {
        log.debug("REST request to save CheckList : {}", checkList);
        if (checkList.getId() != null) {
            throw new BadRequestAlertException("A new checkList cannot already have an ID", ENTITY_NAME, "idexists");
        }
        checkList = checkListService.save(checkList);
        return ResponseEntity.created(new URI("/api/check-lists/" + checkList.getId()))
            .body(checkList);
    }


    /**
     * {@code POST  /create} : Create a new checkList.
     *
     * @param checkListRequestDTO the checkList to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new checkList, or with status {@code 400 (Bad Request)} if the checkList has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/create")
    public ResponseEntity<?> createCheckLists(@RequestBody CheckListRequestDTO checkListRequestDTO) throws URISyntaxException {
        log.debug("REST request to save CheckList : {}", checkListRequestDTO);
        CheckList checkList = checkListService.save(checkListRequestDTO);
        return ResponseEntity.created(new URI("/api/check-lists/" + checkList.getId()))
            .body(checkList);
    }


    /**
     * {@code PUT  /check-lists/:id} : Updates an existing checkList.
     *
     * @param id the id of the checkList to save.
     * @param checkList the checkList to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated checkList,
     * or with status {@code 400 (Bad Request)} if the checkList is not valid,
     * or with status {@code 500 (Internal Server Error)} if the checkList couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CheckList> updateCheckList(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CheckList checkList
    ) throws URISyntaxException {
        log.debug("REST request to update CheckList : {}, {}", id, checkList);
        if (checkList.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, checkList.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!checkListRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        checkList = checkListService.update(checkList);
        return ResponseEntity.ok().body(checkList);
    }

    /**
     * {@code PATCH  /check-lists/:id} : Partial updates given fields of an existing checkList, field will ignore if it is null
     *
     * @param id the id of the checkList to save.
     * @param checkList the checkList to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated checkList,
     * or with status {@code 400 (Bad Request)} if the checkList is not valid,
     * or with status {@code 404 (Not Found)} if the checkList is not found,
     * or with status {@code 500 (Internal Server Error)} if the checkList couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<?> partialUpdateCheckList(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CheckList checkList
    ) throws URISyntaxException {
        log.debug("REST request to partial update CheckList partially : {}, {}", id, checkList);
        if (checkList.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, checkList.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!checkListRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        Optional<CheckList> result = checkListService.partialUpdate(checkList);
        if(result.isPresent()){
            return ResponseEntity.ok(result.get());
        }
        return ResponseEntity.ok("Checklist Not Found");
    }

    /**
     * {@code GET  /check-lists} : get all the checkLists.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of checkLists in body.
     */
    @GetMapping("")
    public List<CheckList> getAllCheckLists() {
        log.debug("REST request to get all CheckLists");
        return checkListService.findAll();
    }

    /**
     * {@code GET  /check-lists/:id} : get the "id" checkList.
     *
     * @param id the id of the checkList to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the checkList, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getCheckList(@PathVariable("id") Long id) {
        log.debug("REST request to get CheckList : {}", id);
        Optional<CheckList> checkList = checkListService.findOne(id);
        if(checkList.isPresent()){
            return ResponseEntity.ok(checkList.get());
        }
         return ResponseEntity.ok("Checklist Not Found");
    }

    /**
     * {@code DELETE  /check-lists/:id} : delete the "id" checkList.
     *
     * @param id the id of the checkList to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCheckList(@PathVariable("id") Long id) {
        log.debug("REST request to delete CheckList : {}", id);
        checkListService.delete(id);
        return ResponseEntity.noContent()
            .build();
    }
}
