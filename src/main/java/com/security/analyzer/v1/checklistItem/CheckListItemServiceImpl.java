package com.security.analyzer.v1.checklistItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link com.security.analyzer.v1.checklistItem.CheckListItem}.
 */
@Service
@Transactional
public class CheckListItemServiceImpl implements CheckListItemService {

    private final Logger log = LoggerFactory.getLogger(CheckListItemServiceImpl.class);

    private final CheckListItemRepository checkListItemRepository;

    public CheckListItemServiceImpl(CheckListItemRepository checkListItemRepository) {
        this.checkListItemRepository = checkListItemRepository;
    }

    @Override
    public CheckListItem save(CheckListItem checkListItem) {
        log.debug("Request to save CheckListItem : {}", checkListItem);
        return checkListItemRepository.save(checkListItem);
    }

    @Override
    public CheckListItem update(CheckListItem checkListItem) {
        log.debug("Request to update CheckListItem : {}", checkListItem);
        return checkListItemRepository.save(checkListItem);
    }

    @Override
    public Optional<CheckListItem> partialUpdate(CheckListItem checkListItem) {
        log.debug("Request to partially update CheckListItem : {}", checkListItem);

        return checkListItemRepository
            .findById(checkListItem.getId())
            .map(existingCheckListItem -> {
                if (checkListItem.getName() != null) {
                    existingCheckListItem.setName(checkListItem.getName());
                }
                if (checkListItem.getValue() != null) {
                    existingCheckListItem.setValue(checkListItem.getValue());
                }
                if (checkListItem.getPriorityLevel() != null) {
                    existingCheckListItem.setPriorityLevel(checkListItem.getPriorityLevel());
                }

                return existingCheckListItem;
            })
            .map(checkListItemRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CheckListItem> findAll() {
        log.debug("Request to get all CheckListItems");
        return checkListItemRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CheckListItem> findOne(Long id) {
        log.debug("Request to get CheckListItem : {}", id);
        return checkListItemRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CheckListItem : {}", id);
        checkListItemRepository.deleteById(id);
    }
}
