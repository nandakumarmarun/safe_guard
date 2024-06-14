package com.security.analyzer.v1.checklistItem;

import com.security.analyzer.v1.Enum.PriorityLevel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CheckListItemMapper {



    public CheckListItem checkListitemDTOToCheckListItem(CheckListItemDTO checkListItemDTO) {
        CheckListItem checkListItem = new CheckListItem();
        checkListItem.setName(checkListItemDTO.getChecklistItemName());
        checkListItem.setValue(checkListItemDTO.getValue());
        checkListItem.setPriorityLevel(PriorityLevel.valueOf(checkListItemDTO.getPriorityLevel()));
        return checkListItem;
    }


    public CheckListItem checkListitemUpdateDTOToCheckListItem(CheckListItemUpdateDTO checkListItemUpdateDTO) {
        CheckListItem checkListItem = new CheckListItem();
        checkListItem.setId(checkListItemUpdateDTO.getId());
        checkListItem.setName(checkListItemUpdateDTO.getChecklistItemName());
        checkListItem.setValue(checkListItemUpdateDTO.getValue());
        checkListItem.setPriorityLevel(PriorityLevel.valueOf(checkListItemUpdateDTO.getPriorityLevel()));
        return checkListItem;
    }

    public CheckListItem checkListitemCreateDTOToCheckListItem(CheckListItemCreateDTO checkListItemCreateDTO) {
        CheckListItem checkListItem = new CheckListItem();
        checkListItem.setName(checkListItemCreateDTO.getChecklistItemName());
        checkListItem.setValue(checkListItemCreateDTO.getValue());
        checkListItem.setPriorityLevel(PriorityLevel.valueOf(checkListItemCreateDTO.getPriorityLevel()));
        return checkListItem;
    }


    public  CheckListItemResposeDTO checkListitemToCheckListItemResponseDTO(CheckListItem checkListItem) {
        CheckListItemResposeDTO checkListItemResposeDTO = new CheckListItemResposeDTO();
        checkListItemResposeDTO.setId(checkListItem.getId());
        checkListItemResposeDTO.setChecklistItemName(checkListItem.getName());
        checkListItemResposeDTO.setValue(checkListItem.getValue());
        checkListItemResposeDTO.setPriorityLevel(checkListItem.getPriorityLevel().toString());
        return checkListItemResposeDTO;
    }




    public  List<CheckListItem> checkListitemCreateDTOListToCheckListItemList(List<CheckListItemDTO> checkListItemDTO) {
        List<CheckListItem> checkListItems = new ArrayList<>();
        checkListItemDTO.forEach(data ->{
            CheckListItem checkListItem = new CheckListItem();
            checkListItem.setName(data.getChecklistItemName());
            checkListItem.setValue(data.getValue());
            checkListItem.setPriorityLevel(PriorityLevel.valueOf(data.getPriorityLevel()));
            checkListItems.add(checkListItem);
        });
        return checkListItems;
    }


    public  List<CheckListItemResposeDTO> checkListitemListToCheckListItemResponseDTOList(List<CheckListItem> checkListItem) {
        List<CheckListItemResposeDTO> checkListItemResposeDTOS = new ArrayList<>();
        checkListItem.forEach(data ->{
            CheckListItemResposeDTO checkListItemResposeDTO = new CheckListItemResposeDTO();
            checkListItemResposeDTO.setId(data.getId());
            checkListItemResposeDTO.setChecklistItemName(data.getName());
            checkListItemResposeDTO.setValue(data.getValue());
            checkListItemResposeDTO.setPriorityLevel(data.getPriorityLevel().toString());
            checkListItemResposeDTOS.add(checkListItemResposeDTO);
        });
        return checkListItemResposeDTOS;
    }
}
