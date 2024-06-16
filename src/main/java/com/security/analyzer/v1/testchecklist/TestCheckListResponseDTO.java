package com.security.analyzer.v1.testchecklist;

import com.security.analyzer.v1.testchecklistitem.TestCheckListItem;
import com.security.analyzer.v1.testchecklistitem.TestCheckListItemDTO;
import com.security.analyzer.v1.testchecklistitem.TestCheckListItemResoponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestCheckListResponseDTO {
    private Long id;
    private Set<TestCheckListItemResoponseDTO> testCheckListItemResoponseDTOS;
    private Long checkListId;
    private String checklistName;


    public TestCheckListResponseDTO(TestCheckList testCheckList) {
        this.id = testCheckList.getId();
        this.testCheckListItemResoponseDTOS = getTestCheckListItemResoponseDTOS(testCheckList.getTestCheckListItems());
        this.checkListId = testCheckList.getCheckList().getId();
        this.checklistName = testCheckList.getCheckList().getName();
    }

    private Set<TestCheckListItemResoponseDTO> getTestCheckListItemResoponseDTOS(Set<TestCheckListItem> testCheckListItems) {
        Set<TestCheckListItemResoponseDTO> testCheckListItemResoponseDTOS = new HashSet<>();
        testCheckListItems.forEach(data->{
            testCheckListItemResoponseDTOS.add(new TestCheckListItemResoponseDTO(data));
        });
        return testCheckListItemResoponseDTOS;
    }


}
