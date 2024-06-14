package com.security.analyzer.v1.checklistItem;


import com.security.analyzer.v1.checklist.CheckListResponseDTO;
import com.security.analyzer.v1.exceptions.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
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


    public CheckListItemResource(
        CheckListItemService checkListItemService,
        CheckListItemRepository checkListItemRepository) {
        this.checkListItemService = checkListItemService;
        this.checkListItemRepository = checkListItemRepository;
    }
    /**
     * {@code POST  /check-list-items} : Create a new checkListItem.
     *
     * @param checkListItemUpdateDTO the checkListItem to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new checkListItem, or with status {@code 400 (Bad Request)} if the checkListItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CheckListResponseDTO> createCheckListItem(
        @RequestBody CheckListItemCreateDTO checkListItemCreateDTO) throws URISyntaxException {
        log.debug("REST request to save CheckListItem : {}", checkListItemCreateDTO);
        CheckListResponseDTO checkListResponseDTO = checkListItemService.save(checkListItemCreateDTO);
        if(checkListResponseDTO.getId() !=  null){
            return ResponseEntity.created(new URI("/api/check-list-items/" + checkListResponseDTO.getId()))
                .body(checkListResponseDTO);
        }
        return ResponseEntity.badRequest().body(null);
    }
    /**
     * {@code PUT  /check-list-items/:id} : Updates an existing checkListItem.
     *
     * @param id the id of the checkListItem to save.
     * @param checkListItemUpdateDTO the checkListItem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated checkListItem,
     * or with status {@code 400 (Bad Request)} if the checkListItem is not valid,
     * or with status {@code 500 (Internal Server Error)} if the checkListItem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CheckListItemResposeDTO> updateCheckListItem(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CheckListItemUpdateDTO checkListItemUpdateDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CheckListItem : {}, {}", id, checkListItemUpdateDTO);
        if (checkListItemUpdateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!checkListItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        checkListItemUpdateDTO.setId(id);
        return ResponseEntity.ok().body(checkListItemService.update(checkListItemUpdateDTO));
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



