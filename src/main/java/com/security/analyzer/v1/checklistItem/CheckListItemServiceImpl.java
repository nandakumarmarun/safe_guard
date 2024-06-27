package com.security.analyzer.v1.checklistItem;

import com.security.analyzer.v1.checklist.CheckList;
import com.security.analyzer.v1.checklist.CheckListMapper;
import com.security.analyzer.v1.checklist.CheckListRepository;
import com.security.analyzer.v1.checklist.CheckListResponseDTO;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class CheckListItemServiceImpl implements CheckListItemService {

    private final Logger log = LoggerFactory.getLogger(CheckListItemServiceImpl.class);

    private final CheckListItemRepository checkListItemRepository;

    private final CheckListRepository checkListRepository;

    private final CheckListMapper checkListMapper;

    private final CheckListItemMapper checkListItemMapper;


    @Override
    public CheckListResponseDTO save(CheckListItemCreateDTO checkListItemCreateDTO) {
        log.debug("Request to create CheckListItem : {}",
            checkListItemCreateDTO.getChecklistItemName());

        CheckListItem checkListItem = checkListItemMapper
            .checkListitemCreateDTOToCheckListItem(checkListItemCreateDTO);

        Optional<CheckList> optionalCheckList = checkListRepository
            .findById(checkListItemCreateDTO.getCheckListId());

        if(optionalCheckList.isPresent()) {
            optionalCheckList
                .get()
                .getCheckListItems()
                .add(checkListItem);
            return checkListMapper
                .checkListToCheckListResponseDTO(checkListRepository
                    .save(optionalCheckList.get()));
        }
        return null;
    }

    @Override
    public CheckListResponseDTO save(MultiCheckLisItemCreatetDTO checkListItemCreateDTOs) {
        Optional<CheckList> optionalCheckList = checkListRepository
                .findById(checkListItemCreateDTOs.getCheckListId());

        for(CheckListItemCreateDTO checkListItemCreateDTO :
                checkListItemCreateDTOs.getCheckListItemCreateDTO()){

            CheckListItem checkListItem = checkListItemMapper
                    .checkListitemCreateDTOToCheckListItem(checkListItemCreateDTO);

            if(optionalCheckList.isPresent()) {
                optionalCheckList
                        .get()
                        .getCheckListItems()
                        .add(checkListItem);
            }
        }
        return checkListMapper
                .checkListToCheckListResponseDTO(checkListRepository
                        .save(optionalCheckList.get()));
    }

    @Override
    public CheckListItemResposeDTO update(CheckListItemUpdateDTO checkListItemUpdateDTO) {
        log.debug("Request to update CheckListItem : {}", checkListItemUpdateDTO);
        checkListItemMapper.checkListitemUpdateDTOToCheckListItem(checkListItemUpdateDTO);
        return checkListItemMapper.checkListitemToCheckListItemResponseDTO(checkListItemRepository
            .save(checkListItemMapper.checkListitemUpdateDTOToCheckListItem(checkListItemUpdateDTO)));
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
