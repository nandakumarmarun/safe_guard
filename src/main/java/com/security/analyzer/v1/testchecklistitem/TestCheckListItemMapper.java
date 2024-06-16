package com.security.analyzer.v1.testchecklistitem;

import org.springframework.stereotype.Service;

@Service
public class TestCheckListItemMapper {

    public TestCheckListItem  TestCheckListItemDTOToTestCheckListItem(TestCheckListItemDTO testCheckListItemDTO){
        TestCheckListItem testCheckListItem = new TestCheckListItem();
        testCheckListItem.setMarked(testCheckListItemDTO.getMarked());
        return null;
    }

    public TestCheckListItem  TestCheckListItemToTestCheckListResponseDTO(TestCheckListItem testCheckListItem){

        return null;
    }


}
