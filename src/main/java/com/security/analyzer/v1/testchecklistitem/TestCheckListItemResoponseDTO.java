package com.security.analyzer.v1.testchecklistitem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestCheckListItemResoponseDTO {
    private Long id;
    private Boolean marked;
    private String ChecklistItemName;
    private Double value;
    private String priorityLevel;
    private Long checklistitemId;

    public TestCheckListItemResoponseDTO(TestCheckListItem testCheckListItem) {
        this.id = testCheckListItem.getId();
        this.marked = testCheckListItem.getMarked();
        this.ChecklistItemName = testCheckListItem.getChecklistitem().getName();
        this.value = testCheckListItem.getChecklistitem().getValue();
        this.priorityLevel = String.valueOf(testCheckListItem.getChecklistitem().getPriorityLevel());
        this.checklistitemId = testCheckListItem.getChecklistitem().getId();
    }

}
