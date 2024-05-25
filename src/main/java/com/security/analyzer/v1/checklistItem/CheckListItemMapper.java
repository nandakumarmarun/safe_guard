package com.security.analyzer.v1.checklistItem;

import com.security.analyzer.v1.Enum.PriorityLevel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CheckListItemMapper {

    public CheckListItem checkListitemRequestDTOToCheckListItem(CheckListItemDTO checkListItemDTO) {
        CheckListItem checkListItem = new CheckListItem();
        checkListItem.setName(checkListItemDTO.getChecklistItemName());
        checkListItem.setValue(checkListItemDTO.getValue());
        checkListItem.setPriorityLevel(PriorityLevel.valueOf(checkListItemDTO.getPriorityLevel()));
        return checkListItem;
    }

    public  List<CheckListItem> checkListitemRequesDTOListToCheckListItemList(List<CheckListItemDTO> checkListItemDTO) {
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
}
