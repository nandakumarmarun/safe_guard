package com.security.analyzer.v1.checklistItem;


import com.security.analyzer.v1.exceptions.BadRequestAlertException;
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
 * REST controller for managing {@link com.security.analyzer.v1.checklistItem.CheckListItem}.
 */
@RestController
@RequestMapping("/api/check-list-items")
public class CheckListItemResource {

    private final Logger log = LoggerFactory.getLogger(CheckListItemResource.class);

    private static final String ENTITY_NAME = "checkListItem";


    private final CheckListItemService checkListItemService;

    private final CheckListItemRepository checkListItemRepository;

    public CheckListItemResource(CheckListItemService checkListItemService, CheckListItemRepository checkListItemRepository) {
        this.checkListItemService = checkListItemService;
        this.checkListItemRepository = checkListItemRepository;
    }

    /**
     * {@code POST  /check-list-items} : Create a new checkListItem.
     *
     * @param checkListItem the checkListItem to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new checkListItem, or with status {@code 400 (Bad Request)} if the checkListItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CheckListItem> createCheckListItem(@RequestBody CheckListItem checkListItem) throws URISyntaxException {
        log.debug("REST request to save CheckListItem : {}", checkListItem);
        if (checkListItem.getId() != null) {
            throw new BadRequestAlertException("A new checkListItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        checkListItem = checkListItemService.save(checkListItem);
        return ResponseEntity.created(new URI("/api/check-list-items/" + checkListItem.getId()))
            .body(checkListItem);
    }

    /**
     * {@code PUT  /check-list-items/:id} : Updates an existing checkListItem.
     *
     * @param id the id of the checkListItem to save.
     * @param checkListItem the checkListItem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated checkListItem,
     * or with status {@code 400 (Bad Request)} if the checkListItem is not valid,
     * or with status {@code 500 (Internal Server Error)} if the checkListItem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CheckListItem> updateCheckListItem(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CheckListItem checkListItem
    ) throws URISyntaxException {
        log.debug("REST request to update CheckListItem : {}, {}", id, checkListItem);
        if (checkListItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, checkListItem.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!checkListItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        checkListItem = checkListItemService.update(checkListItem);
        return ResponseEntity.ok().body(checkListItem);
    }

    /**
     * {@code PATCH  /check-list-items/:id} : Partial updates given fields of an existing checkListItem, field will ignore if it is null
     *
     * @param id the id of the checkListItem to save.
     * @param checkListItem the checkListItem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated checkListItem,
     * or with status {@code 400 (Bad Request)} if the checkListItem is not valid,
     * or with status {@code 404 (Not Found)} if the checkListItem is not found,
     * or with status {@code 500 (Internal Server Error)} if the checkListItem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<?> partialUpdateCheckListItem(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CheckListItem checkListItem
    ) throws URISyntaxException {
        log.debug("REST request to partial update CheckListItem partially : {}, {}", id, checkListItem);
        if (checkListItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, checkListItem.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!checkListItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CheckListItem> result = checkListItemService.partialUpdate(checkListItem);

        if(result.isPresent()){
            return ResponseEntity.ok(result.get());
        }
        return ResponseEntity.ok("Checklist Not Found");
    }

    /**
     * {@code GET  /check-list-items} : get all the checkListItems.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of checkListItems in body.
     */
    @GetMapping("")
    public List<CheckListItem> getAllCheckListItems() {
        log.debug("REST request to get all CheckListItems");
        return checkListItemService.findAll();
    }

    /**
     * {@code GET  /check-list-items/:id} : get the "id" checkListItem.
     *
     * @param id the id of the checkListItem to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the checkListItem, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getCheckListItem(@PathVariable("id") Long id) {
        log.debug("REST request to get CheckListItem : {}", id);
        Optional<CheckListItem> checkListItem = checkListItemService.findOne(id);

        if(checkListItem.isPresent()){
            return ResponseEntity.ok(checkListItem.get());
        }
        return ResponseEntity.ok("Checklist Not Found");
    }

    /**
     * {@code DELETE  /check-list-items/:id} : delete the "id" checkListItem.
     *
     * @param id the id of the checkListItem to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCheckListItem(@PathVariable("id") Long id) {
        log.debug("REST request to delete CheckListItem : {}", id);
        checkListItemService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
