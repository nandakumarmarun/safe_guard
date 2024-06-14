package com.security.analyzer.v1.checklist;

import com.security.analyzer.v1.checklistItem.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link com.security.analyzer.v1.checklist.CheckList}.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class CheckListServiceImpl implements CheckListService {

    private final Logger log = LoggerFactory.getLogger(CheckListServiceImpl.class);

    private final CheckListRepository checkListRepository;

    private final CheckListItemRepository checkListItemRepository;

    private final CheckListMapper checkListMapper;

    private final CheckListItemMapper checkListItemMapper;

    @Override
    public CheckList save(CheckList checkList) {
        log.debug("Request to save CheckList : {}", checkList);
        return checkListRepository.save(checkList);
    }

    @Override
    public CheckListResponseDTO save(CheckListRequestDTO checkListRequestDTO ) {
        CheckList checkList = checkListMapper.checkListRequestDTOToCheckList(checkListRequestDTO);
        Set<CheckListItem> checkListItems = checkListItemMapper
            .checkListitemCreateDTOListToCheckListItemList(
                checkListRequestDTO.getCheckListItemDTO())
            .stream()// Set the CheckList for each CheckListItem
            .collect(Collectors.toSet());
        checkList.setCheckListItems(checkListItems);
        CheckList respone = checkListRepository.save(checkList);
        List<CheckListItemResposeDTO> checkListItemResposeDTOS = checkListItemMapper.checkListitemListToCheckListItemResponseDTOList(respone.getCheckListItems().stream().toList());
        CheckListResponseDTO checkListResponseDTO = checkListMapper.checkListToCheckListResponseDTO(respone);
        checkListResponseDTO.setCheckListItemDTO(checkListItemResposeDTOS);
        return checkListResponseDTO;
    }


    @Override
    public CheckListResponseDTO update(CheckListUpdateDTO CheckListUpdateDTO) {
        log.debug("Request to update CheckList : {}", CheckListUpdateDTO);
        CheckList checkList = checkListMapper.checkListUpdateDTOToCheckList(CheckListUpdateDTO);
        CheckList respone = checkListRepository.save(checkList);
        CheckListResponseDTO checkListResponseDTO = checkListMapper.checkListToCheckListResponseDTO(respone);
        return checkListResponseDTO;
    }

    @Override
    public Optional<CheckList> partialUpdate(CheckList checkList) {
        log.debug("Request to partially update CheckList : {}", checkList);

        return checkListRepository
            .findById(checkList.getId())
            .map(existingCheckList -> {
                if (checkList.getName() != null) {
                    existingCheckList.setName(checkList.getName());
                }

                return existingCheckList;
            })
            .map(checkListRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CheckListResponseDTO> findAll() {
        log.debug("Request to get all CheckLists");
        List<CheckList> checkListAll = checkListRepository.findAll();
        List<CheckListResponseDTO> checkListResponseDTO = checkListMapper
                .checkListToCheckListResponseDTO(checkListAll);
        return checkListResponseDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CheckList> findOne(Long id) {
        log.debug("Request to get CheckList : {}", id);
        return checkListRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CheckList : {}", id);
        checkListRepository.deleteById(id);
    }

}
