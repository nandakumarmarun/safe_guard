package com.security.analyzer.v1.checklist;

import com.security.analyzer.v1.checklistItem.CheckListItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CheckListMapper {

    private  final CheckListItemMapper checkListItemMapper;

    public CheckList checkListRequestDTOToCheckList(CheckListRequestDTO checkListRequestDTO) {
        CheckList checkList = new CheckList();
        checkList.setName(checkListRequestDTO.getChecklistName());
        return checkList;
    }

    public CheckList checkListUpdateDTOToCheckList(CheckListUpdateDTO checkListUpdateDTO) {
        CheckList checkList = new CheckList();
        checkList.setId(checkListUpdateDTO.getId());
        checkList.setName(checkListUpdateDTO.getChecklistName());
        return checkList;
    }

    public CheckListResponseDTO checkListToCheckListResponseDTO(CheckList checkList) {
        CheckListResponseDTO checkListResponseDTO = new CheckListResponseDTO();
        checkListResponseDTO.setId(checkList.getId());
        checkListResponseDTO.setChecklistName(checkList.getName());
        return checkListResponseDTO;
    }

    public List<CheckListResponseDTO> checkListToCheckListResponseDTO(List<CheckList> checkList) {
       List<CheckListResponseDTO> checkListResponseDTOs = new ArrayList<>();
        checkList.forEach(data->{
            CheckListResponseDTO checkListResponseDTO = new CheckListResponseDTO();
            checkListResponseDTO.setId(data.getId());
            checkListResponseDTO.setChecklistName(data.getName());
            checkListResponseDTO.setCheckListItemDTO(checkListItemMapper
                .checkListitemListToCheckListItemResponseDTOList(
                    data.getCheckListItems().stream().toList()));
            checkListResponseDTOs.add(checkListResponseDTO);
        });
        return checkListResponseDTOs;
    }


}
