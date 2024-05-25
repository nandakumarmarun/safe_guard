package com.security.analyzer.v1.checklist;

import org.springframework.stereotype.Service;

@Service
public class CheckListMapper {

    public CheckList checkListRequestDTOToCheckList(CheckListRequestDTO checkListRequestDTO) {
        CheckList checkList = new CheckList();
        checkList.setName(checkListRequestDTO.getChecklistName());
        return checkList;
    }
}
